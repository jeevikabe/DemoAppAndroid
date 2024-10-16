package com.example.demoapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Multimedia extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_VIDEO_CAPTURE = 2;
    private static final int REQUEST_AUDIO_CAPTURE = 3;
    private static final int REQUEST_DOCUMENT_PICK = 4;
    private static final int REQUEST_PERMISSIONS = 5;

    private ImageView imgView;
    private VideoView videoView;
    private ImageButton btnPlayPauseVideo;
    private TextView documentTextView;

    private MediaPlayer mediaPlayer;
    private boolean isAudioPlaying = false;
    private boolean isVideoPlaying = false;
    private Uri videoUri;
    private Uri audioUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);

        imgView = findViewById(R.id.imgView);
        videoView = findViewById(R.id.videoView);
        btnPlayPauseVideo = findViewById(R.id.btnPlayPauseVideo);
        documentTextView = findViewById(R.id.documentTextView);

        Button btnCaptureImage = findViewById(R.id.btnCaptureImage);
        btnCaptureImage.setOnClickListener(v -> captureImage());
        btnCaptureImage.setText(getString(R.string.capture_image));

        Button btnCaptureVideo = findViewById(R.id.btnCaptureVideo);
        btnCaptureVideo.setOnClickListener(v -> captureVideo());
        btnCaptureVideo.setText(getString(R.string.capture_video));

        Button btnRecordAudio = findViewById(R.id.btnRecordAudio);
        btnRecordAudio.setOnClickListener(v -> recordAudio());
        btnRecordAudio.setText(getString(R.string.record_audio));

        Button btnPickDocument = findViewById(R.id.btnPickDocument);
        btnPickDocument.setOnClickListener(v -> pickDocument());
        btnPickDocument.setText(getString(R.string.pick_document));

        Button btnPlayPauseAudio = findViewById(R.id.btnPlayPauseAudio);
        btnPlayPauseAudio.setOnClickListener(v -> toggleAudioPlayback());
        btnPlayPauseAudio.setText(getString(R.string.play_audio));

        btnPlayPauseVideo.setOnClickListener(v -> toggleVideoPlayback());

        checkPermissions();
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
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

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void captureVideo() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void recordAudio() {
        Intent recordAudioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        if (recordAudioIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(recordAudioIntent, REQUEST_AUDIO_CAPTURE);
        }
    }

//    private void pickDocument() {
//        Intent pickDocumentIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        pickDocumentIntent.setType("*/*");
//        startActivityForResult(pickDocumentIntent, REQUEST_DOCUMENT_PICK);
//    }

    private void pickDocument() {
        Intent pickDocumentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickDocumentIntent.setType("application/pdf"); // Restrict to PDF files
        pickDocumentIntent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(pickDocumentIntent, "Select PDF"), REQUEST_DOCUMENT_PICK);
    }



    private void toggleAudioPlayback() {
        if (audioUri == null) {
            Toast.makeText(this, "No audio file selected", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(this, audioUri);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to play audio", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (isAudioPlaying) {
            mediaPlayer.pause();
            isAudioPlaying = false;
            ((Button) findViewById(R.id.btnPlayPauseAudio)).setText("Play Audio");
        } else {
            mediaPlayer.start();
            isAudioPlaying = true;
            ((Button) findViewById(R.id.btnPlayPauseAudio)).setText("Pause Audio");
        }
        mediaPlayer.setOnCompletionListener(mp -> {
            isAudioPlaying = false;
            ((Button) findViewById(R.id.btnPlayPauseAudio)).setText("Play Audio");
        });
    }

    private void toggleVideoPlayback() {
        if (videoUri == null) {
            Toast.makeText(this, "No video captured", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isVideoPlaying) {
            videoView.pause();
            btnPlayPauseVideo.setImageResource(android.R.drawable.ic_media_play);
            isVideoPlaying = false;
        } else {
            videoView.start();
            btnPlayPauseVideo.setImageResource(android.R.drawable.ic_media_pause);
            isVideoPlaying = true;
        }

        videoView.setOnCompletionListener(mp -> {
            btnPlayPauseVideo.setImageResource(android.R.drawable.ic_media_play);
            isVideoPlaying = false;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imgView.setImageBitmap(imageBitmap);
                    imgView.setVisibility(View.VISIBLE); // Make ImageView visible
                    break;
                case REQUEST_VIDEO_CAPTURE:
                    videoUri = data.getData();
                    videoView.setVideoURI(videoUri);
                    videoView.start();
                    isVideoPlaying = true;
                    btnPlayPauseVideo.setImageResource(android.R.drawable.ic_media_pause);
                    ((View) videoView.getParent()).setVisibility(View.VISIBLE);// Make VideoView visible
                    break;
                case REQUEST_AUDIO_CAPTURE:
                    audioUri = data.getData();
                    Toast.makeText(this, "Audio Recorded Successfully", Toast.LENGTH_SHORT).show();
                    break;
                case REQUEST_DOCUMENT_PICK:
//                    Uri documentUri = data.getData();
//                    String documentContent = readTextFromUri(documentUri);
//                    documentTextView.setText(documentContent);
//                    break;
                    Uri pdfUri = data.getData();
                    openPdfFile(pdfUri);
            }
        }
    }

    private String readTextFromUri(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return stringBuilder.toString();
    }


    private void openPdfFile(Uri pdfUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(pdfUri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent chooser = Intent.createChooser(intent, "Open PDF");
        try {
            //startActivity(chooser);
            Toast.makeText(this, "Document picked", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "No PDF viewer found", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (!allPermissionsGranted) {
               // Toast.makeText(this, "All permissions are required for this app to function.", Toast.LENGTH_SHORT).show();
                //finish();
            }
        }
    }
}
