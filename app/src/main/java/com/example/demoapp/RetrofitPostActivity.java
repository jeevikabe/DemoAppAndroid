package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.domains.UserModel;
import com.example.demoapp.interfaces.APIResponseListener;
import com.example.demoapp.network.APIServer;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitPostActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText bodyEditText;
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_post);

        titleEditText = findViewById(R.id.titleEditText);
        bodyEditText = findViewById(R.id.bodyEditText);
        responseTextView = findViewById(R.id.responseTextView);

        Button sendPostButton = findViewById(R.id.sendPostButton);
        sendPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostRequest();
                //sendDataToServerUsingHttp();
            }
        });
    }

    private void sendPostRequest() {
        String title = titleEditText.getText().toString();
        String body = bodyEditText.getText().toString();

        // Check if title and body are not empty
        if (title.isEmpty() || body.isEmpty()) {
            Toast.makeText(RetrofitPostActivity.this, "Title and Body must be provided", Toast.LENGTH_LONG).show();
            return;
        }

        // Create a new UserModel object
        UserModel postRequest = new UserModel(0, 0, title, body);

        // Create Retrofit API interface
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<UserModel> call = apiInterface.createPost(postRequest);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel postResponse = response.body();
                    Log.d("POST_RESPONSE", "Response: " + postResponse.getTitle() + ", " + postResponse.getBody());
                    showSuccessDialog(postResponse);
                } else {
                    Log.e("POST_ERROR", "Failed to create post: " + response.message());
                    showFailureDialog(response.message());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("POST_FAILURE", t.getMessage());
                showFailureDialog(t.getMessage());
            }
        });
    }

    private void showSuccessDialog(UserModel postResponse) {
        new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Post created: " + "\nTitle: " + postResponse.getTitle() + "\nBody: " + postResponse.getBody())
                .setPositiveButton("Update", (dialog, which) -> showUpdateDialog(postResponse))
                .setNegativeButton(android.R.string.ok, null)
                .show();
    }

    private void showUpdateDialog(UserModel postResponse) {
        // Inflate the update dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_update_post, null);
        final EditText titleInput = dialogView.findViewById(R.id.titleInput);
        final EditText bodyInput = dialogView.findViewById(R.id.bodyInput);

        // Set current values in the input fields
        titleInput.setText(postResponse.getTitle());
        bodyInput.setText(postResponse.getBody());

        new AlertDialog.Builder(this)
                .setTitle("Update Post")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String updatedTitle = titleInput.getText().toString();
                    String updatedBody = bodyInput.getText().toString();
                    updatePost(postResponse.getId(), updatedTitle, updatedBody);
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void updatePost(int postId, String title, String body) {
        UserModel updatedPost = new UserModel(0, postId, title, body); // Pass `0` for `userId` if not needed

        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Call<UserModel> call = apiInterface.patchPost(postId, updatedPost);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RetrofitPostActivity.this, "Post updated successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, refresh the data or update the UI
                } else {
                    Toast.makeText(RetrofitPostActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(RetrofitPostActivity.this, "Failed to update post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showFailureDialog(String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle("Failure")
                .setMessage("Failed to create post: " + errorMessage)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void sendDataToServerUsingHttp() {
        String title = titleEditText.getText().toString();
        String body = bodyEditText.getText().toString();
        String url = "https://dummy.restapiexample.com/api/v1/create";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", title);
            jsonObject.put("salary", body);

            new APIServer(this).postJSON(url, jsonObject, new APIResponseListener() {
                @Override
                public void onResponse(int statusCode, String response) {
                    if (statusCode == 200) {
                        Toast.makeText(RetrofitPostActivity.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RetrofitPostActivity.this, "Unable to add data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
