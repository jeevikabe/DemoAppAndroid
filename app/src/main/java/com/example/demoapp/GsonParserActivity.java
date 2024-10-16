package com.example.demoapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.demoapp.domains.Jso;
import com.example.demoapp.domains.UserList;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonParserActivity extends AppCompatActivity {

    private TextView parsedDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_parser);

        parsedDataTextView = findViewById(R.id.text_parsed_data_gson);

        String jsonData = "{ \"users\": [ { \"id\": 1, \"name\": \"John Doe\", \"email\": \"john@example.com\" }, { \"id\": 2, \"name\": \"Jane Smith\", \"email\": \"jane@example.com\" } ] }";

        parseJsonWithGson(jsonData);
    }

    private void parseJsonWithGson(String jsonData) {
        Gson gson = new Gson();
        try {
            UserList userList = gson.fromJson(jsonData, UserList.class);
            StringBuilder parsedData = new StringBuilder();

            for (Jso user : userList.getUsers()) {
                // Append the user data to StringBuilder
                parsedData.append("ID: ").append(user.getId())
                        .append("\nName: ").append(user.getName())
                        .append("\nEmail: ").append(user.getEmail())
                        .append("\n\n");  // Add new lines for better readability
            }

            // Set the parsed data in the TextView
            parsedDataTextView.setText(parsedData.toString());

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
