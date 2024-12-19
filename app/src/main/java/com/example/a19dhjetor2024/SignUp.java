package com.example.a19dhjetor2024;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    DataBaseConnection DataBaseConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        EditText phoneEditText = findViewById(R.id.phoneEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginbtn = findViewById(R.id.loginbtn);
        Button signupButton = findViewById(R.id.signupButton);

        DataBaseConnection = new DataBaseConnection(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        loginbtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LoginActivity.class);
            startActivity(intent);
        });

        signupButton.setOnClickListener(v -> {
            String firstName = firstNameEditText.getText().toString().trim();
            String lastName = lastNameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (validateInputs(firstName, lastName, phone, email, password)) {
                if (DataBaseConnection.checkEmail(email)) {
                    Toast.makeText(SignUp.this, "Email-i ekziston tashmë!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = DataBaseConnection.insertUser(firstName, lastName, phone, email, password);

                    if (isInserted) {
                        Toast.makeText(SignUp.this, "Regjistrimi u krye me sukses!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, "Dështoi regjistrimi, provoni përsëri!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateInputs(String firstName, String lastName, String phone, String email, String password) {
        if (!firstName.matches("^[a-zA-ZëËçÇ ]+$")) {
            Toast.makeText(this, "Emri nuk duhet të përmbajë numra ose karaktere speciale!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!lastName.matches("^[a-zA-ZëËçÇ ]+$")) {
            Toast.makeText(this, "Mbiemri nuk duhet të përmbajë numra ose karaktere speciale!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!phone.matches("^[0-9]{7,}$")) {
            Toast.makeText(this, "Numri i telefonit duhet të përmbajë vetëm numra dhe të jetë të paktën 7 shifra!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ju lutem vendosni një email të vlefshëm!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$")) {
            Toast.makeText(this, "Fjalëkalimi duhet të përmbajë të paktën një shkronjë të madhe, një numër dhe një karakter special!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
