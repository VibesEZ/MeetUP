package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {

    private EditText email;
    private MaterialButton resetbtn;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        // Initialize UI components
        email = findViewById(R.id.email);
        resetbtn = findViewById(R.id.resetbtn);
        progressBar = findViewById(R.id.progressbar);
        fAuth = FirebaseAuth.getInstance();

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String mail = email.getText().toString().trim();
        if (TextUtils.isEmpty(mail)) {
            email.setError("Email is required.");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        fAuth.sendPasswordResetEmail(mail)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(Forgot.this, "Password reset email sent. Check your email.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    } else {
                        Toast.makeText(Forgot.this, "Failed to reset password. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
