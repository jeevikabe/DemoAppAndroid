package com.example.demoapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.demoapp.domains.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RealTimeFirebaseActivity extends AppCompatActivity {
    private static final String TAG = "FirebaseDemo";
    private TextView textView;
    private TextView userTextView; // TextView for displaying user info

    // Firebase Database Reference
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_firebase);

        // Initialize TextViews
        textView = findViewById(R.id.textView);
        userTextView = findViewById(R.id.userTextView);

        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();

        // Read a User object from Firebase
        readUsersFromFirebase();
    }

    // Method to read Users from Firebase and display them in UI
    private void readUsersFromFirebase() {
        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder userData = new StringBuilder();

                // Iterate through all users
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        Log.d(TAG, "User Name: " + user.name + ", Email: " + user.email);
                        // Append user data
                        userData.append("Name: ").append(user.name).append("\n")
                                .append("Email: ").append(user.email).append("\n").append("-------------------------------------").append("\n\n");
                    }
                }

                // Update UI with all users data
                userTextView.setText(userData.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read users.", databaseError.toException());
            }
        });
    }
}
