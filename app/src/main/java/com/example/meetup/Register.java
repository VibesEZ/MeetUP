package com.example.meetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText username, email, password, re_password;
    Button register;
    TextView login;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String userID;
    FirebaseAuth fAuth;
    ImageButton showPasswordButton, showRePasswordButton;
    boolean passwordVisible = false;
    boolean rePasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Hooks
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.repassword);
        register = findViewById(R.id.signupbtn);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressbar);
        showPasswordButton = findViewById(R.id.showPasswordButton);
        showRePasswordButton = findViewById(R.id.showRePasswordButton);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), SwipePages.class));
            finish();
        }

        // Toggle Password Visibility
        showPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(password, showPasswordButton);
            }
        });

        // Toggle Re-Password Visibility
        showRePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(re_password, showRePasswordButton);
            }
        });

        // Register button click
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_username = username.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();
                String str_re_password = re_password.getText().toString();

                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)
                        ||
                        TextUtils.isEmpty(str_re_password)) {
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (str_password.length() < 6) {
                    Toast.makeText(Register.this, "Password must have at least 6 characters", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (!str_password.equals(str_re_password)) {
                    Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform the registration logic here

                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(str_email, str_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser fUser = fAuth.getCurrentUser();
                                    fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getApplicationContext(), "Registered Successfully",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                        }
                                    });
                                    Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("users").document(userID);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("username", username);
                                    user.put("email", email);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: user profile is created for " + userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d(TAG, "onFailure: " + e.toString());
                                        }
                                    });

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {

                                    Toast.makeText(Register.this, "Error: " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

                Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });

        // Login button click
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        // Toggle Password Visibility
        showPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(password, showPasswordButton);
            }
        });

        // Toggle Re-Password Visibility
        showRePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(re_password, showRePasswordButton);
            }
        });
    }

    // Method to toggle password visibility
    private void togglePasswordVisibility(EditText editText, ImageButton button) {
        if (passwordVisible) {
            editText.setInputType(129); // Password mode
            button.setImageResource(R.drawable.ic_visibility_off_24);
        } else {
            editText.setInputType(1); // Text mode
            button.setImageResource(R.drawable.ic_visibility);
        }
        editText.setSelection(editText.getText().length()); // Set cursor to the end of the text
        passwordVisible = !passwordVisible;
    }
}
