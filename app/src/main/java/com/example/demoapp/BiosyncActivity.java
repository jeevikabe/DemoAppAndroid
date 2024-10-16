package com.example.demoapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.demoapp.interfaces.APIResponseListener;
import org.json.JSONException;
import org.json.JSONObject;
import networkapi.APIServer;
import networkapi.Links;

public class BiosyncActivity extends AppCompatActivity implements View.OnClickListener {

    EditText Username, Password;
    Button Lbutton;
    ImageView eyeIcon;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biosync);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        Lbutton = findViewById(R.id.button);
        eyeIcon = findViewById(R.id.eye_icon);

        Lbutton.setOnClickListener(this);
        eyeIcon.setOnClickListener(this);

        // Initialize Links
        Links.setLinks(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            String user = Username.getText().toString();
            String pass = Password.getText().toString();

            if (user.isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else {
                loginToServer(user, pass);
            }
        } else if (view.getId() == R.id.eye_icon) {
            togglePasswordVisibility();
        }
    }
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Successful")
                //.setMessage("The PDF file has been uploaded successfully.")
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
    private void loginToServer(String user, String pass) {
        // Create JSON payload
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", user);
            jsonObject.put("password", pass);
            jsonObject.put("imei", "123");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        APIServer apiServer = new APIServer(this);
        // Post login request
        apiServer.postJSON(Links.USER_PASS, jsonObject, new APIResponseListener() {
            @Override
            public void onResponse(int statusCode, String response) {
                if (statusCode == 200) {
                    // Successful login, move to OTP screen
                   // Intent intent = new Intent(BiosyncActivity.this, AuthenticationOtpActivity.class);
//                    intent.putExtra("user", user);
//                    startActivity(intent);
//                    finish();
                    showSuccessDialog();
                } else {
                    // Show error
                    Toast.makeText(BiosyncActivity.this, "Login failed: " + response, Toast.LENGTH_LONG).show();
                }
            }

//            @Override
//            public void onError(String error) {
//                Toast.makeText(BiosyncActivity.this, "Network Error: " + error, Toast.LENGTH_LONG).show();
//            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            Password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eyeIcon.setImageResource(R.drawable.eye);
        } else {
            Password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eyeIcon.setImageResource(R.drawable.eye);
        }
        Password.setSelection(Password.getText().length());
        isPasswordVisible = !isPasswordVisible;
    }
}
