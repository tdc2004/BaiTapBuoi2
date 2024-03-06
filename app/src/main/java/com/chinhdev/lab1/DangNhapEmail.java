package com.chinhdev.lab1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class DangNhapEmail extends AppCompatActivity {
    TextInputEditText edt_emai, edt_pas;
    FirebaseAuth auth;
    Button btn_login;
    TextView tv_dangk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        auth = FirebaseAuth.getInstance();
        edt_emai = findViewById(R.id.edt_email);
        edt_pas = findViewById(R.id.edt_pass);
        btn_login = findViewById(R.id.btn_login);
        tv_dangk = findViewById(R.id.tv_dangky);
        tv_dangk.setOnClickListener(v -> {
            startActivity(new Intent(DangNhapEmail.this, DangKyEmail.class));
        });
        Intent intent = getIntent();
        edt_emai.setText(intent.getStringExtra("user_email"));
        edt_pas.setText(intent.getStringExtra("password"));

        btn_login.setOnClickListener(v -> {
            String email = edt_emai.getText().toString();
            String pass = edt_pas.getText().toString();
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(DangNhapEmail.this, "Dang Nhap thanh cong", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DangNhapEmail.this, Signout.class));
                    } else {
                        Toast.makeText(DangNhapEmail.this, "Dang nhap that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}