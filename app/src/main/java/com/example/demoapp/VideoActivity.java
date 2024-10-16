package com.example.demoapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.interfaces.UploadFileAndResponseListener;

import networkapi.APIServer;
import networkapi.Links;

public class VideoActivity extends AppCompatActivity {
    private static final int REQUEST_VIDEO_PICK = 2;
    private static final int REQUEST_PERMISSIONS = 3;

    private ImageView imageView;
    private TextView progressText;
    private Button uploadButton;
    private Uri videoUri;
    private String currentVideoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        imageView = findViewById(R.id.imageView);
        progressText = findViewById(R.id.progressText);
        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setText(getString(R.string.upload));
        // Set up initial visibility
        imageView.setVisibility(View.INVISIBLE);

        // Check and request permissions
        checkPermissions();

        // Set upload button click listener
        uploadButton.setOnClickListener(v -> openGallery());
    }

    private void checkPermissions() {
        String[] permissions = {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (!hasPermissions(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        }
    }

    private boolean hasPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void openGallery() {
        Intent pickVideoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickVideoIntent, REQUEST_VIDEO_PICK);
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    private void uploadImage() {
        if (currentVideoPath != null && !currentVideoPath.isEmpty()) {
            Log.d("VideoActivity", "Uploading video from path: " + currentVideoPath); // Log the file path
            new APIServer(this).uploadImage(Links.UPLOAD_VIDEO, currentVideoPath, "video",
                    new APIResponseListener() {
                        @Override
                        public void onResponse(int statusCode, String response) {
                            Log.d("VideoActivity", "Upload response: " + response); // Log the response
                            if (statusCode == 200) {
                                progressText.setText("Upload complete!");
                            } else {
                                progressText.setText("Upload failed!");
                            }
                        }
                    }, new UploadFileAndResponseListener() {
                        @Override
                        public void onResponse(float bytesUploaded, float totalBytes) {
                            if (totalBytes > 0) {
                                int progress = (int) ((bytesUploaded / totalBytes) * 100);
                                progressText.setText("Progress: " + progress + "%");
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_VIDEO_PICK && data != null) {
                videoUri = data.getData();
                imageView.setImageURI(videoUri);
                imageView.setVisibility(View.VISIBLE);
                currentVideoPath = getPathFromUri(videoUri); // Update the path
                uploadImage(); // Trigger upload after selecting the video
            }
        }
    }
}
