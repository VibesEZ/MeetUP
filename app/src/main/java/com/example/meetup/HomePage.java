package com.example.meetup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                // Navigate to the Home page
                navigateToHome();
                return true;
            } else if (itemId == R.id.search) {
                // Navigate to the Search page
                navigateToSearch();
                return true;
            } else if (itemId == R.id.person) {
                // Navigate to the Person page
                navigateToPerson();
                return true;
            } else if (itemId == R.id.settings) {
                // Navigate to the Settings page
                navigateToSettings();
                return true;
            }
            return false;
        });

        // Initialize your BottomAppBar and other components if necessary
        BottomAppBar bottomAppBar = findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    private void navigateToSearch() {
        showToast("Search");
    }

    private void navigateToPerson() {
        showToast("Person");
    }

    private void navigateToSettings() {
        showToast("Settings");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
