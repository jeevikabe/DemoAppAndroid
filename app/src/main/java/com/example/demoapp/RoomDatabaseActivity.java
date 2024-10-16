package com.example.demoapp;

import android.content.Intent;
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

import java.util.List;

import Roomdatabase.AppDatabase;
import Roomdatabase.User;

public class RoomDatabaseActivity extends AppCompatActivity {
    private AppDatabase db;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextAge;
    private EditText editTextNickname;
    private EditText editTextMobile;
    private TextView textViewUserList;

    private Button buttonInsert;
    private Button buttonFetch;
    private Button buttonUpdate;
    private Button buttonFetchAndUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_database);

        // Initialize Room database
        db = AppDatabase.getDatabase(this);

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAge = findViewById(R.id.editTextAge);
        editTextNickname = findViewById(R.id.editTextNickname);

        editTextMobile = findViewById(R.id.editTextMobile);

        textViewUserList = findViewById(R.id.textViewUserList);
        buttonInsert = findViewById(R.id.buttonInsert);
        buttonFetch = findViewById(R.id.buttonFetch);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonFetchAndUpdate = findViewById(R.id.button);

        buttonInsert.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String email = editTextEmail.getText().toString();
            int age = Integer.parseInt(editTextAge.getText().toString());
            String nickname = editTextNickname.getText().toString();

            String mobile = editTextMobile.getText().toString();

            new InsertUserTask().execute(new User(name, email, age, nickname, mobile));
        });

        buttonFetch.setOnClickListener(v -> new FetchUsersTask().execute());

        buttonUpdate.setOnClickListener(v -> showUpdateDialog());

        buttonFetchAndUpdate.setOnClickListener(v -> {
            // Navigate to RoomApiActivity
            Intent intent = new Intent(RoomDatabaseActivity.this, RoomApiActivity.class);
            startActivity(intent);
        });
    }

    private void showUpdateDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_user_update, null);

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
                Toast.makeText(RoomDatabaseActivity.this, "Invalid User ID", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }

    private class InsertUserTask extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            db.userDao().insert(users[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(RoomDatabaseActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private class FetchUsersTask extends AsyncTask<Void, Void, List<User>> {
        @Override
        protected List<User> doInBackground(Void... voids) {
            return db.userDao().getAllUsers();
        }

        @Override
        protected void onPostExecute(List<User> users) {
            StringBuilder sb = new StringBuilder();
            for (User user : users) {
                sb.append("ID: ").append(user.getId()).append("\n")
                        .append("Name: ").append(user.getName()).append("\n")
                        .append("Email: ").append(user.getEmail()).append("\n")
                        .append("Age: ").append(user.getAge()).append("\n")
                        .append("Nickname: ").append(user.getNickname()).append("\n")
                        .append("PhoneNumber: ").append(user.getPhonenumber()).append("\n\n");
            }
            textViewUserList.setText(sb.toString());
        }
    }

    private class UpdateUserTask extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... params) {
            int userId = (int) params[0];
            String newName = (String) params[1];
            User user = db.userDao().getUserById(userId);
            if (user != null) {
                user.setName(newName);
                db.userDao().update(user);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(RoomDatabaseActivity.this, "User updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
