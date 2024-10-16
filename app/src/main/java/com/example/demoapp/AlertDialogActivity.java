package com.example.demoapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class AlertDialogActivity extends AppCompatActivity {


    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        Button btnNormalAlertDialog = findViewById(R.id.btnNormalAlertDialog);
        btnNormalAlertDialog.setText(getString(R.string.normal_alert_dialog));

        Button btnProgressDialog = findViewById(R.id.btnProgressDialog);
        btnProgressDialog.setText(getString(R.string.progress_dialog));

        Button btnCustomAlertDialog = findViewById(R.id.btnCustomAlertDialog);
        btnCustomAlertDialog.setText(getString(R.string.custom_alert_dialog));

        Button btnFullScreenAlertDialog = findViewById(R.id.btnFullScreenAlertDialog);
        btnFullScreenAlertDialog.setText(getString(R.string.full_screen_alert_dialog));

        Button btnFullScreenLoadingBar = findViewById(R.id.btnFullScreenLoadingBar);
        btnFullScreenLoadingBar.setText(getString(R.string.full_screen_loading_bar));

        // Normal Alert Dialog
        btnNormalAlertDialog.setOnClickListener(v -> showNormalAlertDialog());

        // Progress Dialog
        btnProgressDialog.setOnClickListener(v -> showProgressDialog());

        // Custom Alert Dialog
        btnCustomAlertDialog.setOnClickListener(v -> showCustomAlertDialog());

        // Full-Screen Alert Dialog
        btnFullScreenAlertDialog.setOnClickListener(v -> showFullScreenDialog());

        // Full-Screen Loading Bar
        btnFullScreenLoadingBar.setOnClickListener(v -> showFullScreenLoadingBar());
    }

    private void showNormalAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Normal Alert Dialog")
                .setMessage("This is a normal alert dialog.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

   /* private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Simulate a delay to dismiss the dialog
        new Handler().postDelayed(() -> progressDialog.dismiss(), 3000);
    }*/

    private void showProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.progressalert, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Simulate a delay to dismiss the dialog
        new Handler().postDelayed(dialog::dismiss, 3000);
    }

    private void showCustomAlertDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.customalert, null);
        alert.setView(mView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);

        mView.findViewById(R.id.cancel).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        mView.findViewById(R.id.ok).setOnClickListener(v -> {
            Toast.makeText(this, "Clicked OK ", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

   /* private void showFullScreenAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        builder.setMessage("This is a full-screen alert dialog.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }*/

  /*  private void showFullScreenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fullscreen, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button btnCloseDialog = dialogView.findViewById(R.id.btnCloseFullScreenDialog);
        btnCloseDialog.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

*/
  private void showFullScreenDialog() {
      AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
      LayoutInflater inflater = LayoutInflater.from(this);  // or just use 'this.getLayoutInflater();'
      View dialogView = inflater.inflate(R.layout.fullscreen, null);
      builder.setView(dialogView);

      AlertDialog dialog = builder.create();

      // Close button functionality
      Button btnCloseDialog = dialogView.findViewById(R.id.btnCloseFullScreenDialog);
      btnCloseDialog.setOnClickListener(v -> dialog.dismiss());

      dialog.show();
  }

    private void showFullScreenLoadingBar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fullscreenloadingbar, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.show();

        // Simulate a delay to dismiss the dialog
        new Handler().postDelayed(dialog::dismiss, 3000);
    }
}

