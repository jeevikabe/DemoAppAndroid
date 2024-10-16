package com.example.demoapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.core.content.FileProvider;

import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.interfaces.UploadFileAndResponseListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import networkapi.APIServer;
import networkapi.Links;

public class ApiActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_PERMISSIONS = 3;

    private ImageView imageView;
    private TextView progressText;
    private Button uploadButton;
    private Uri imageUri;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        imageView = findViewById(R.id.imageView);
        progressText = findViewById(R.id.progressText);
        uploadButton = findViewById(R.id.uploadButton);
        uploadButton.setText(getString(R.string.upload));
        // Set up initial visibility
        imageView.setVisibility(View.INVISIBLE);

        // Check and request permissions
        checkPermissions();

        // Set upload button click listener
        uploadButton.setOnClickListener(v -> showImageOptions());
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.CAMERA,
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

    private void showImageOptions() {
        CharSequence[] options = {"Take Photo", "Choose from Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            } else if (which == 1) {
                openGallery();
            }
        });
        builder.show();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                imageUri = FileProvider.getUriForFile(this,
                        "com.example.demoapp.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void openGallery() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    private void uploadImage() {
        if (currentPhotoPath != null && !currentPhotoPath.isEmpty()) {
            Log.d("ApiActivity", "Uploading file from path: " + currentPhotoPath); // Log the file path
            new APIServer(this).uploadImage(Links.UPLOAD_IMAGE, currentPhotoPath,"photo",
                    new APIResponseListener() {
                        @Override
                        public void onResponse(int statusCode, String response) {
                            Log.d("ApiActivity", "Upload response: " + response); // Log the response
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
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                imageView.setImageURI(imageUri);
                imageView.setVisibility(View.VISIBLE);
                uploadImage(); // Trigger upload after capturing the image
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
                imageView.setVisibility(View.VISIBLE);
                currentPhotoPath = getPathFromUri(imageUri); // Update the path
                uploadImage(); // Trigger upload after selecting the image
            }
        }
    }
}
