package com.habitoff;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class RemediesActivity extends AppCompatActivity {
    private MaterialButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedies);

        // Initialize views
        btnBack = findViewById(R.id.btn_back);

        // Back button listener
        btnBack.setOnClickListener(v -> finish());
    }
}
