package com.habitoff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ProfileActivity - Display and edit user profile
 */
public class ProfileActivity extends AppCompatActivity {

    private TextView tvUsername, tvEmail, tvQuitDate;
    private EditText etCigarettesPerDay, etPricePerCigarette;
    private Button btnUpdateProfile;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        databaseHelper = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("HabitOffPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", "");
        currentUser = databaseHelper.getUserByEmail(userEmail);

        tvUsername = findViewById(R.id.tv_username);
        tvEmail = findViewById(R.id.tv_email);
        tvQuitDate = findViewById(R.id.tv_quit_date);
        etCigarettesPerDay = findViewById(R.id.et_cigarettes_per_day);
        etPricePerCigarette = findViewById(R.id.et_price_per_cigarette);
        btnUpdateProfile = findViewById(R.id.btn_update_profile);

        if (currentUser != null) {
            tvUsername.setText(currentUser.getUsername());
            tvEmail.setText(currentUser.getEmail());
            tvQuitDate.setText(currentUser.getQuitDate());
            etCigarettesPerDay.setText(String.valueOf(currentUser.getCigarettesPerDay()));
            etPricePerCigarette.setText(String.valueOf(currentUser.getPricePerPack())); // price per cigarette
        }

        btnUpdateProfile.setOnClickListener(v -> updateProfile());
    }

    private void updateProfile() {
        String cigarettesPerDayStr = etCigarettesPerDay.getText().toString().trim();
        String pricePerCigaretteStr = etPricePerCigarette.getText().toString().trim();

        if (cigarettesPerDayStr.isEmpty() || pricePerCigaretteStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int cigarettesPerDay = Integer.parseInt(cigarettesPerDayStr);
            double pricePerCigarette = Double.parseDouble(pricePerCigaretteStr);

            if (cigarettesPerDay < 0 || pricePerCigarette < 0) {
                Toast.makeText(this, "Values must be positive", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = databaseHelper.updateUserProfile(currentUser.getId(), cigarettesPerDay, pricePerCigarette);

            if (success) {
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                SharedPreferences prefs = getSharedPreferences("HabitOffPrefs", MODE_PRIVATE);
                String userEmail = prefs.getString("userEmail", "");
                currentUser = databaseHelper.getUserByEmail(userEmail);
            } else {
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_logout) {
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        SharedPreferences prefs = getSharedPreferences("HabitOffPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
