package com.example.demoapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demoapp.domains.UserModel;

import java.util.ArrayList;
import java.util.List;
//import ApiStoreRoomdb.RetrofitInstance;
import ApiStoreRoomdb.ApiService;
import RoomApi.ApiDao;
import RoomApi.ApiDatabase;
import RoomApi.UserEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

public class RoomApiActivity extends AppCompatActivity {
    private ApiDatabase apiDatabase;
    private TextView textViewUserList;
    private Button buttonFetch;
    private Button buttonUpdate;
    private Button buttonFetchAndUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_api);

        // Initialize Room database
        apiDatabase = ApiDatabase.getDatabase(this);
        textViewUserList = findViewById(R.id.textViewUserList);
        buttonFetch = findViewById(R.id.buttonFetch);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonFetchAndUpdate = findViewById(R.id.button); // Fixed button ID

        // Fetch data from API and update database
        buttonFetch.setOnClickListener(v -> new FetchUsersTask().execute());

        buttonUpdate.setOnClickListener(v -> showUpdateDialog());

        buttonFetchAndUpdate.setOnClickListener(v -> fetchDataFromApiAndUpdateDatabase());
    }

    private void fetchDataFromApiAndUpdateDatabase() {
        ApiService apiService = RetrofitInstance.getRetrofit().create(ApiService.class);
        Call<List<UserModel>> call = apiService.getPosts();

        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<UserModel> userModels = response.body();
                    new SaveUserModelsToDatabaseTask(apiDatabase).execute(userModels);
                } else {
                    Toast.makeText(RoomApiActivity.this, "Failed to fetch data from API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                Toast.makeText(RoomApiActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUpdateDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_api, null);

        // Find views in the dialog layout
        final EditText editTextUserId = dialogView.findViewById(R.id.editTextUserId);
        final EditText editTextUpdateUsername = dialogView.findViewById(R.id.editTextUpdateUsername);

        // Create and configure the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update User");
        builder.setView(dialogView);
        builder.setPositiveButton("Update", (dialog, which) -> {
            try {
                int userId = Integer.parseInt(editTextUserId.getText().toString());
                String newName = editTextUpdateUsername.getText().toString();
                new UpdateUserTask().execute(userId, newName);
            } catch (NumberFormatException e) {
                Toast.makeText(RoomApiActivity.this, "Invalid User ID", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }

    private class FetchUsersTask extends AsyncTask<Void, Void, List<UserEntity>> {
        @Override
        protected List<UserEntity> doInBackground(Void... voids) {
            return apiDatabase.apiDao().getAllUsers();
        }

        @Override
        protected void onPostExecute(List<UserEntity> userEntities) {
            // Update UI with the fetched data
            StringBuilder userList = new StringBuilder();
            for (UserEntity user : userEntities) {
                userList.append("ID: ").append(user.getId()).append("\n")
                        .append("Title: ").append(user.getTitle()).append("\n")
                        .append("Body: ").append(user.getBody()).append("\n\n");
            }
            textViewUserList.setText(userList.toString());
        }
    }

    private class SaveUserModelsToDatabaseTask extends AsyncTask<List<UserModel>, Void, Void> {
        private ApiDatabase db;

        public SaveUserModelsToDatabaseTask(ApiDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(List<UserModel>... lists) {
            List<UserModel> userModels = lists[0];
            ApiDao apiDao = db.apiDao();

            // Convert UserModels to UserEntities
            List<UserEntity> userEntities = new ArrayList<>();
            for (UserModel userModel : userModels) {
                UserEntity userEntity = new UserEntity(userModel.getId(), userModel.getTitle(), userModel.getBody());
                userEntities.add(userEntity);
            }

            // Insert UserEntities into the database
            apiDao.insert(userEntities); // Using batch insert
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(RoomApiActivity.this, "Data fetched and saved to database", Toast.LENGTH_SHORT).show();
        }
    }

    private class UpdateUserTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {
            int userId = (int) params[0];
            String newName = (String) params[1];
            UserEntity user = apiDatabase.apiDao().getUserById(userId);
            if (user != null) {
                user.setTitle(newName); // Assuming `setTitle` is used for updating
                apiDatabase.apiDao().update(user);
            }
            return null;
        }
    }
}
