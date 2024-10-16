package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParserJsonActivity extends AppCompatActivity {

    private TextView parsedDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parser_json);

        parsedDataTextView = findViewById(R.id.text_parsed_data);

        String jsonData = "{ \"users\": [ { \"id\": 1, \"name\": \"John Doe\", \"email\": \"john@example.com\" }, { \"id\": 2, \"name\": \"Jane Smith\", \"email\": \"jane@example.com\" } ] }";

        parseJson(jsonData);
        Button btngson = (Button) findViewById(R.id.btngs);
        btngson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(JsonParserJsonActivity.this,GsonParserActivity.class);
                startActivity(in);
                finish();
            }
        });

    }

    private void parseJson(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray usersArray = jsonObject.getJSONArray("users");

            // Create a StringBuilder to store all the parsed data
            StringBuilder parsedData = new StringBuilder();

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                int id = user.getInt("id");
                String name = user.getString("name");
                String email = user.getString("email");

                // Append the user data to the StringBuilder
                parsedData.append("ID: ").append(id)
                        .append("\nName: ").append(name)
                        .append("\nEmail: ").append(email)
                        .append("\n\n");  // Add new lines for better readability
            }

            // Set the parsed data in the TextView
            parsedDataTextView.setText(parsedData.toString());

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
