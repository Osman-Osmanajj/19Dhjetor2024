package com.example.a19dhjetor2024;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpVerificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        EditText otpEditText = findViewById(R.id.otpEditText);
        Button verifyOtpButton = findViewById(R.id.verifyOtpButton);

        String generatedOtp = getIntent().getStringExtra("OTP");

        verifyOtpButton.setOnClickListener(v -> {
            String enteredOtp = otpEditText.getText().toString();

            if (enteredOtp.equals(generatedOtp)) {
                Intent intent = new Intent(OtpVerificationActivity.this, HomePageActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(OtpVerificationActivity.this, "Kodi OTP është i pasaktë!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}