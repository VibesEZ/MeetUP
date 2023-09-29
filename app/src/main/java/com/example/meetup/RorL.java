package com.example.meetup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.meetup.ImagePagerAdapter;
import com.google.android.material.button.MaterialButton;

public class RorL extends AppCompatActivity {

    private ViewPager viewPager;
    private ImagePagerAdapter imagePagerAdapter;
    private int[] imageArray = { R.drawable.image1, R.drawable.image2, R.drawable.image3 };

    TextView login;
    MaterialButton register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ror_l);
        viewPager = findViewById(R.id.image_viewpager);
        imagePagerAdapter = new ImagePagerAdapter(this, imageArray);
        viewPager.setAdapter(imagePagerAdapter);

        // Add any additional code or functionality you need here.
        // Create account button
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        }); // Create account button

        // Login button

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        }); // Login button

    }
}
