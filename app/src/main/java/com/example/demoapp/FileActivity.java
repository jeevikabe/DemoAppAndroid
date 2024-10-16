package com.example.demoapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FileActivity extends AppCompatActivity {
    private static final int PICK_FILE_REQUEST = 1; // Request code for file picker
    //private String downloadUrl = "https://example.com/file.png"; // Replace with your file URL
    private String downloadUrl = "https://via.placeholder.com/150"; // Placeholder image URL

    //private String uploadUrl = "https://example.com/upload"; // Replace with your server upload URL
    private String uploadUrl = "https://postb.in"; // Use PostBin to receive the POST request

    //private String uploadUrl = "https://httpbin.org/post"; // Mock URL for testing uploads

    private String filePath; // This will store the path of the selected file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        Button selectFileButton = findViewById(R.id.selectFileButton);
        Button uploadButton = findViewById(R.id.uploadButton);
        Button downloadButton = findViewById(R.id.downloadButton);

        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filePath != null) {
                    uploadFile();
                } else {
                    // Show an error message or prompt the user to select a file first
                }
            }
        });

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Allows selecting any file type
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select a file"), PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                filePath = getRealPathFromURI(uri);
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

    public void downloadFile() {
        Intent intent = new Intent(this, FileService.class);
        intent.setAction(FileService.ACTION_DOWNLOAD);
        intent.putExtra(FileService.EXTRA_URL, downloadUrl);
        intent.putExtra(FileService.EXTRA_FILE_PATH, "/storage/emulated/0/Download/file.zip"); // Adjust the download path as needed
        startService(intent);
    }

    public void uploadFile() {
        Intent intent = new Intent(this, FileService.class);
        intent.setAction(FileService.ACTION_UPLOAD);
        intent.putExtra(FileService.EXTRA_URL, uploadUrl);
        intent.putExtra(FileService.EXTRA_FILE_PATH, filePath);
        startService(intent);
    }
}
