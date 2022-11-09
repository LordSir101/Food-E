package net.ontariotechu.food_e.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import net.ontariotechu.food_e.R;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize components
        btnBack = findViewById(R.id.btnBack);

        // Add listeners and adapters
        btnBack.setOnClickListener(this::onBackClicked);

    }

    private void onBackClicked(View v) {
        finish();
    }
}