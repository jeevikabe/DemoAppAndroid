package com.example.demoapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class FileUploadActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1;
    private static final int REQUEST_PERMISSIONS = 2;

    private Button uploadFileButton;
    private TextView progressText;
    private Uri fileUri;
    private String currentFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload);

        uploadFileButton = findViewById(R.id.uploadFileButton);
        uploadFileButton.setText(getString(R.string.upload));
        progressText = findViewById(R.id.progressText);
        //progressText.setText(getString(R.string.progress));
        // Check and request permissions if necessary
        checkPermissions();

        uploadFileButton.setOnClickListener(v -> openFilePickerForPdf());
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
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

    private String getPathFromUri(Uri uri) {
        if (uri == null) return null;
        String path = null;

        // Check if URI is a content URI
        if ("content".equals(uri.getScheme())) {
            String[] projection = { MediaStore.Files.FileColumns.DATA };
            try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                    path = cursor.getString(columnIndex);
                }
            }
            if (path == null) {
                // Handle non-file content URIs here
                path = handleNonFileContentUri(uri);
            }
        } else if ("file".equals(uri.getScheme())) {
            // Handle file URI
            path = uri.getPath();
        }
        return path;
    }

    private String handleNonFileContentUri(Uri uri) {
        // Implement logic to handle non-file URIs
        // Example: Use InputStream to read file content
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            if (inputStream != null) {
                File tempFile = File.createTempFile("tempFile", ".pdf", getCacheDir()); // Ensure .pdf extension
                try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    return tempFile.getAbsolutePath();
                }
            }
        } catch (IOException e) {
            Log.e("FileUploadActivity", "Error handling non-file content URI", e);
        }
        return null;
    }

    private void openFilePickerForPdf() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, PICK_FILE_REQUEST);
    }
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Upload Successful")
                .setMessage("The PDF file has been uploaded successfully.")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private void showFailureDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Upload Failed")
                .setMessage("There was an error uploading the PDF file.")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void uploadImage() {
        if (currentFilePath != null && !currentFilePath.isEmpty() && currentFilePath.endsWith(".pdf")) {
            Log.d("FileUploadActivity", "Uploading file from path: " + currentFilePath); // Log the file path
            new APIServer(this).uploadImage(Links.UPLOAD_PDF, currentFilePath, "document",
                    new APIResponseListener() {
                        @Override
                        public void onResponse(int statusCode, String response) {
                            Log.d("FileUploadActivity", "Upload response: " + response); // Log the response
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
            Toast.makeText(this, "No valid PDF file selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == PICK_FILE_REQUEST) {
                fileUri = data.getData();
                if (fileUri != null) {
                    Log.d("FileUploadActivity", "File URI: " + fileUri.toString());
                    currentFilePath = getPathFromUri(fileUri); // Update the path
                    Log.d("FileUploadActivity", "File Path: " + currentFilePath);
                    if (currentFilePath != null && currentFilePath.endsWith(".pdf")) {
                        uploadImage(); // Trigger upload after selecting the file
                    } else {
                        Toast.makeText(this, "Selected file is not a PDF", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No file URI found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with file picking
            }
        }
    }
}
