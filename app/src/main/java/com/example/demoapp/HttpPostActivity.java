package com.example.demoapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.network.APIServer;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpPostActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText bodyEditText;
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_post);
        titleEditText = findViewById(R.id.titleEditText);
        bodyEditText = findViewById(R.id.bodyEditText);
        responseTextView = findViewById(R.id.responseTextView);

        Button sendPostButton = findViewById(R.id.sendPostButton);
        sendPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToServerUsingHttp();
            }
        });
    }

    // Success dialog to show the response after the post is created
    private void showSuccessDialog(String response) {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Data added successfully! ")
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }

    // Failure dialog to handle the failed post creation
    private void showFailureDialog(String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle("Failure")
                .setMessage("Failed to create post: " + errorMessage)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    // Using APIServer to send HTTP POST request
    private void sendDataToServerUsingHttp() {
        String title = titleEditText.getText().toString();
        String body = bodyEditText.getText().toString();
        String url = "https://jsonplaceholder.typicode.com/posts";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("body", body);

            new APIServer(this).postJSON(url, jsonObject, new APIResponseListener() {
                @Override
                public void onResponse(int statusCode, String response) {
                    if (statusCode == 201) {
                        // Display success dialog with the response
                        showSuccessDialog(response);
                    } else {
                        // Display failure dialog if status code is not 201
                        showFailureDialog("Unable to add data. Status code: " + statusCode);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            showFailureDialog("JSON error: " + e.getMessage());
        }
    }
}
