package com.example.demoapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;

public class FileService extends Service {
    public static final String ACTION_DOWNLOAD = "action.DOWNLOAD";
    public static final String ACTION_UPLOAD = "action.UPLOAD";
    public static final String EXTRA_URL = "extra.URL";
    public static final String EXTRA_FILE_PATH = "extra.FILE_PATH";

    private OkHttpClient httpClient = new OkHttpClient();
    private NotificationManager notificationManager;
    private static final String CHANNEL_ID = "download_upload_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            String url = intent.getStringExtra(EXTRA_URL);
            String filePath = intent.getStringExtra(EXTRA_FILE_PATH);

            if (ACTION_DOWNLOAD.equals(action)) {
                downloadFile(url, filePath);
            } else if (ACTION_UPLOAD.equals(action)) {
                uploadFile(url, filePath);
            }
        }
        return START_NOT_STICKY;
    }

    private void downloadFile(String fileUrl, final String destFilePath) {
        Request request = new Request.Builder().url(fileUrl).build();

        showNotification("Downloading file...", 0);

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Service", "Download failed: " + e.getMessage());
                updateNotification("Download Failed", -1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e("Service", "Download failed: " + response.message());
                    updateNotification("Download Failed", -1);
                    return;
                }

                File file = new File(destFilePath);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    long fileSize = response.body().contentLength();
                    byte[] buffer = new byte[2048];
                    int read;
                    long downloaded = 0;

                    while ((read = response.body().byteStream().read(buffer)) != -1) {
                        fos.write(buffer, 0, read);
                        downloaded += read;
                        int progress = (int) (100 * downloaded / fileSize);
                        showNotification("Downloading file...", progress);
                    }
                } catch (IOException e) {
                    Log.e("Service", "File write error: " + e.getMessage());
                    updateNotification("Download Failed", -1);
                    return;
                }

                updateNotification("Download Complete", 100);
            }
        });
    }

    private void uploadFile(String serverUrl, String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();

        Request request = new Request.Builder().url(serverUrl).post(new ProgressRequestBody(requestBody)).build();

        showNotification("Uploading file...", 0);

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Service", "Upload failed: " + e.getMessage());
                updateNotification("Upload Failed", -1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    updateNotification("Upload Complete", 100);
                } else {
                    Log.e("Service", "Upload failed: " + response.message());
                    updateNotification("Upload Failed", -1);
                }
            }
        });
    }

    private void showNotification(String content, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Background Service")
                .setContentText(content)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setOngoing(progress >= 0 && progress < 100);

        if (progress >= 0) {
            builder.setProgress(100, progress, false);
        }

        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private void updateNotification(String content, int progress) {
        showNotification(content, progress);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Background Service Channel", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(serviceChannel);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class ProgressRequestBody extends RequestBody {

        private final RequestBody requestBody;
        private static final int DEFAULT_BUFFER_SIZE = 2048;

        public ProgressRequestBody(RequestBody requestBody) {
            this.requestBody = requestBody;
        }

        @Override
        public MediaType contentType() {
            return requestBody.contentType();
        }

        @Override
        public long contentLength() throws IOException {
            return requestBody.contentLength();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            long contentLength = contentLength();
            long uploaded = 0;
            BufferedSink bufferedSink = Okio.buffer(sink);

            try {
                // Writing the request body and tracking progress
                while (uploaded < contentLength) {
                    long read = 0;
                    requestBody.writeTo(bufferedSink);
                    if (read == -1) break; // End of stream
                    uploaded += read;
                    int progress = (int) (100 * uploaded / contentLength);
                    showNotification("Uploading file...", progress);
                }
            } finally {
                bufferedSink.flush();
            }
        }
    }
}
