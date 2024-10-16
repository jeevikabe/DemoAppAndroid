package com.example.demoapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.interfaces.UploadFileAndResponseListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import networkapi.APIServer;
import networkapi.Links;

public class ZipFileActivity extends AppCompatActivity {
    private static final int REQUEST_FILE_PICK = 1;
    private static final int REQUEST_PERMISSIONS = 2;

    private TextView progressText;
    private Button uploadButton;
    private Uri fileUri;
    private String currentFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_file);
        progressText = findViewById(R.id.progressText);
        uploadButton = findViewById(R.id.uploadButton);

        // Check and request permissions
        checkPermissions();

        // Set upload button click listener
        uploadButton.setOnClickListener(v -> showFilePicker());
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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

    private void showFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Allow all file types
        String[] mimeTypes = {"application/zip"}; // Filter for ZIP files
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select ZIP file"), REQUEST_FILE_PICK);
    }

    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private String copyUriToFile(Uri uri) {
        String fileName = getFileName(uri);
        File file = new File(getExternalFilesDir(null), fileName);

        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return file.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error copying file", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private void uploadFile() {
        if (currentFilePath != null && !currentFilePath.isEmpty()) {
            Log.d("ZipFileActivity", "Uploading file from path: " + currentFilePath); // Log the file path
            new APIServer(this).uploadImage(Links.UPLOAD_ZIP, currentFilePath, "zipfile",
                    new APIResponseListener() {
                        @Override
                        public void onResponse(int statusCode, String response) {
                            Log.d("ZipFileActivity", "Upload response: " + response); // Log the response
                            if (statusCode == 200) {
                                progressText.setText("Upload complete!");
                                showSuccessDialog(); // Show success dialog
                            } else {
                                progressText.setText("Upload failed!");
                                showFailureDialog(); // Show failure dialog
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
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_FILE_PICK && data != null) {
                fileUri = data.getData();
                currentFilePath = copyUriToFile(fileUri); // Copy the file to local storage
                if (currentFilePath != null) {
                    uploadFile(); // Trigger upload after copying the file
                } else {
                    Toast.makeText(this, "Error retrieving file path", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Upload Successful")
                .setMessage("The ZIP file has been uploaded successfully.")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private void showFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Upload Failed")
                .setMessage("There was an error uploading the ZIP file.")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
