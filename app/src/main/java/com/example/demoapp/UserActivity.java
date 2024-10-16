//package com.example.demoapp;
//
//import android.os.Bundle;
//import android.widget.EditText;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.demoapp.roomdatabasead.User;
//import com.example.demoapp.roomdatabasead.UserListAdapter;
//import com.example.demoapp.roomdatabasead.UserViewModel;
//
//public class UserActivity extends AppCompatActivity {
//
//    private UserViewModel userViewModel;
//    private EditText userNameEditText, userAgeEditText;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user);
//
//        // Correct RecyclerView ID
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        final UserListAdapter adapter = new UserListAdapter(new UserListAdapter.UserDiff());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // ViewModel setup
//        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//        userViewModel.getAllUsers().observe(this, users -> {
//            // Update the cached copy of users in the adapter
//            adapter.submitList(users);
//        });
//
//        // Handle insert operation
//        userNameEditText = findViewById(R.id.editTextUserName);
//        userAgeEditText = findViewById(R.id.editTextUserAge);
//
//        findViewById(R.id.button_add).setOnClickListener(v -> {
//            String name = userNameEditText.getText().toString();
//            int age = Integer.parseInt(userAgeEditText.getText().toString());
//            User user = new User(name, age);
//            userViewModel.insert(user);
//        });
//    }
//}






package com.example.demoapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.roomdatabasead.User;
import com.example.demoapp.roomdatabasead.UserListAdapter;
import com.example.demoapp.roomdatabasead.UserViewModel;

public class UserActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private EditText userNameEditText, userAgeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Find and bind views
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        userNameEditText = findViewById(R.id.editTextUserName);
        userAgeEditText = findViewById(R.id.editTextUserAge);

        if (recyclerView == null || userNameEditText == null || userAgeEditText == null) {
            // If any view is null, display a Toast and return
            Toast.makeText(this, "View initialization error!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Setup RecyclerView
        final UserListAdapter adapter = new UserListAdapter(new UserListAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ViewModel setup
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().observe(this, users -> {
            // Update the cached copy of users in the adapter
            adapter.submitList(users);
        });

        // Handle insert operation
        findViewById(R.id.button_add).setOnClickListener(v -> {
            String name = userNameEditText.getText().toString();
            String ageStr = userAgeEditText.getText().toString();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ageStr)) {
                Toast.makeText(UserActivity.this, "Please enter both name and age", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int age = Integer.parseInt(ageStr);
                User user = new User(name, age);
                userViewModel.insert(user);

                // Clear the input fields
                userNameEditText.setText("");
                userAgeEditText.setText("");

            } catch (NumberFormatException e) {
                Toast.makeText(UserActivity.this, "Invalid age entered", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
