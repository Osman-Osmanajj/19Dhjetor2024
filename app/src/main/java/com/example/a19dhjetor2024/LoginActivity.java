package com.example.a19dhjetor2024;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private DataBaseConnection dataBaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        dataBaseConnection = new DataBaseConnection(this);

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUp.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                if (dataBaseConnection.validateUser(email, password)) {
                    String otp = generateOtp();

                    new Thread(() -> {
                        try {
                            SenderEmail.sendEmail(
                                    email,
                                    "Kodi OTP",
                                    "Kodi juaj OTP është: " + otp
                            );
                            dataBaseConnection.insert_otp(otp);

                            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Kodi OTP është dërguar!", Toast.LENGTH_SHORT).show());
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Ka ndodhur një gabim gjatë dërgimit të email-it.", Toast.LENGTH_SHORT).show());
                        }
                    }).start();

                    Intent intent = new Intent(LoginActivity.this, OtpVerificationActivity.class);
                    intent.putExtra("email", email);
                    intent.putExtra("OTP", otp);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Një email i tillë nuk ekziston ne databaz!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Ju lutem plotësoni të gjitha fushat.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String generateOtp() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }
}
