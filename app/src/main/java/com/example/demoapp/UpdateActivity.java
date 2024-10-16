package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.domains.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private EditText titleEditText, bodyEditText;
    private Button updateButton;
    private ApiInterface apiInterface;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        titleEditText = findViewById(R.id.titleEditText);
        bodyEditText = findViewById(R.id.bodyEditText);
        updateButton = findViewById(R.id.updateButton);

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        postId = getIntent().getIntExtra("postId", -1);
        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");

        titleEditText.setText(title);
        bodyEditText.setText(body);

        updateButton.setOnClickListener(v -> {
            String updatedTitle = titleEditText.getText().toString();
            String updatedBody = bodyEditText.getText().toString();

            UserModel updatedUser = new UserModel(0, postId, updatedTitle, updatedBody);

            Call<UserModel> call = apiInterface.patchPost(postId, updatedUser);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    if (response.isSuccessful()) {
                        Log.d("UpdateActivity", "Update successful: " + response.body());
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        try {
                            Log.e("UpdateActivity", "Update failed: " + response.errorBody().string());
                        } catch (Exception e) {
                            Log.e("UpdateActivity", "Error reading error body", e);
                        }
                    }
                }
               @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.e("UpdateActivity", "Update failed: ", t);
                }
            });
        });
    }
}
