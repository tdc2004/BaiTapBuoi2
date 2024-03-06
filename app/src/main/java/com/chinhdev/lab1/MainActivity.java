package com.chinhdev.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btn_email, btn_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn_email = findViewById(R.id.btn_email);
        btn_phone = findViewById(R.id.btn_phone);

        btn_email.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DangNhapEmail.class);
            startActivity(intent);
        });
        btn_phone.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DangNhapPhone.class);
            startActivity(intent);
        });
    }
}