package com.habitoff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton; // for MaterialButton


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * MainActivity - Dashboard screen showing user progress and daily motivation
 */
public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome, tvDaysQuit, tvMoneySaved, tvCigarettesAvoided, tvDailyMotivation;
    private CardView cardProfile;
    private DatabaseHelper databaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getWritableDatabase();


        // Get current user
        SharedPreferences prefs = getSharedPreferences("HabitOffPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", "");
        currentUser = databaseHelper.getUserByEmail(userEmail);

        // Initialize views
        tvWelcome = findViewById(R.id.tv_welcome);
        tvDaysQuit = findViewById(R.id.tv_days_quit);
        tvMoneySaved = findViewById(R.id.tv_money_saved);
        tvCigarettesAvoided = findViewById(R.id.tv_cigarettes_avoided);
        tvDailyMotivation = findViewById(R.id.tv_daily_motivation);
        cardProfile = findViewById(R.id.card_profile);

        MaterialButton btnRemedies = findViewById(R.id.btn_remedies);
        btnRemedies.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RemediesActivity.class))
        );

        // Set welcome message
        if (currentUser != null) {
            tvWelcome.setText("Welcome back, " + currentUser.getUsername() + "!");
            updateDashboard();
            displayDailyMotivation();
        }

        // Profile card click listener
        cardProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh user data when returning to dashboard
        SharedPreferences prefs = getSharedPreferences("HabitOffPrefs", MODE_PRIVATE);
        String userEmail = prefs.getString("userEmail", "");
        currentUser = databaseHelper.getUserByEmail(userEmail);
        if (currentUser != null) {
            updateDashboard();
            displayDailyMotivation();
        }
    }

    /**
     * Update dashboard with calculated statistics
     */
    private void updateDashboard() {
        int daysQuit = calculateDaysQuit(currentUser.getQuitDate());
        double moneySaved = calculateMoneySaved(daysQuit,
                currentUser.getCigarettesPerDay(), currentUser.getPricePerPack());
        int cigarettesAvoided = daysQuit * currentUser.getCigarettesPerDay();

        tvDaysQuit.setText(String.valueOf(daysQuit));
        tvMoneySaved.setText("₹" + String.format(Locale.getDefault(), "%.2f", moneySaved));
        tvCigarettesAvoided.setText(String.valueOf(cigarettesAvoided));
    }

    /**
     * Display daily motivational quote
     */
    private void displayDailyMotivation() {
        // Get random quote from database
        String quote = databaseHelper.getRandomQuoteEnglish();

        if (quote != null && !quote.isEmpty()) {
            tvDailyMotivation.setText("\"" + quote + "\"");
        } else {
            tvDailyMotivation.setText("\"You can do this! Believe in yourself.\"");
        }
    }

    /**
     * Calculate days since quit date
     */
    private int calculateDaysQuit(String quitDate) {
        if (quitDate == null || quitDate.isEmpty()) {
            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date quit = sdf.parse(quitDate);
            Date today = new Date();
            long diffInMillis = today.getTime() - quit.getTime();
            return (int) TimeUnit.MILLISECONDS.toDays(diffInMillis);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Calculate money saved
     * Uses price per cigarette instead of price per pack
     */
    private double calculateMoneySaved(int daysQuit, int cigarettesPerDay, double pricePerCigarette) {
        if (cigarettesPerDay == 0 || pricePerCigarette == 0) {
            return 0;
        }

        return daysQuit * cigarettesPerDay * pricePerCigarette;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_logout) {
            logoutUser();
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(this, "HabitOff v1.0 - Quit Smoking App", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Logout user and return to login screen
     */
    private void logoutUser() {
        SharedPreferences prefs = getSharedPreferences("HabitOffPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}