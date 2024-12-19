package com.example.a19dhjetor2024;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}


