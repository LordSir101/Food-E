package net.ontariotechu.food_e.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import net.ontariotechu.food_e.R;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSave;

    private LinearLayout llAllergies;
    private LinearLayout llAllergiesContent;
    private ImageView ivAllergies;

    private LinearLayout llDiet;
    private LinearLayout llDietContent;
    private ImageView ivDiet;

    private LinearLayout llOther;
    private LinearLayout llOtherContent;
    private ImageView ivOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize components
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        llAllergies = findViewById(R.id.llAllergies);
        llAllergiesContent = findViewById(R.id.llAllergiesContent);
        ivAllergies = findViewById(R.id.ivAllergies);

        llDiet = findViewById(R.id.llDiet);
        llDietContent = findViewById(R.id.llDietContent);
        ivDiet = findViewById(R.id.ivDiet);

        llOther = findViewById(R.id.llOther);
        llOtherContent = findViewById(R.id.llOtherContent);
        ivOther = findViewById(R.id.ivOther);

        // Add listeners and adapters
        btnBack.setOnClickListener(this::onBackClicked);
        llAllergies.setOnClickListener(this::onAllergiesHeaderClicked);
        llDiet.setOnClickListener(this::onDietHeaderClicked);
        llOther.setOnClickListener(this::onOtherHeaderClicked);

    }

    private void onBackClicked(View v) {
        finish();
    }

    private void onSaveClicked(View v) {
        // TODO: Persist changes
    }

    // TODO: Refactor into reusable view
    private void onAllergiesHeaderClicked(View v) {
        if (llAllergiesContent.getVisibility() == View.GONE) {
            Drawable caretUp = ResourcesCompat.getDrawable(getResources(), R.drawable.caret_up, null);
            ivAllergies.setBackground(caretUp);
            llAllergiesContent.setVisibility(View.VISIBLE);
        } else {
            Drawable caretDown = ResourcesCompat.getDrawable(getResources(), R.drawable.caret_down, null);
            ivAllergies.setBackground(caretDown);
            llAllergiesContent.setVisibility(View.GONE);
        }
    }

    private void onDietHeaderClicked(View v) {
        if (llDietContent.getVisibility() == View.GONE) {
            Drawable caretUp = ResourcesCompat.getDrawable(getResources(), R.drawable.caret_up, null);
            ivDiet.setBackground(caretUp);
            llDietContent.setVisibility(View.VISIBLE);
        } else {
            Drawable caretDown = ResourcesCompat.getDrawable(getResources(), R.drawable.caret_down, null);
            ivDiet.setBackground(caretDown);
            llDietContent.setVisibility(View.GONE);
        }
    }

    private void onOtherHeaderClicked(View v) {
        if (llOtherContent.getVisibility() == View.GONE) {
            Drawable caretUp = ResourcesCompat.getDrawable(getResources(), R.drawable.caret_up, null);
            ivOther.setBackground(caretUp);
            llOtherContent.setVisibility(View.VISIBLE);
        } else {
            Drawable caretDown = ResourcesCompat.getDrawable(getResources(), R.drawable.caret_down, null);
            ivOther.setBackground(caretDown);
            llOtherContent.setVisibility(View.GONE);
        }
    }

}