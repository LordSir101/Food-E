package net.ontariotechu.food_e.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.checkbox.MaterialCheckBox;

import net.ontariotechu.food_e.DbHandler;
import net.ontariotechu.food_e.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private Button btnSave;

    private LinearLayout llAllergies;
    private LinearLayout llAllergiesContent;
    private ImageView ivAllergies;

    private LinearLayout llDiet;
    private LinearLayout llDietContent;
    private ImageView ivDiet;

    private DbHandler db;
    private List<String> allergies;
    private List<String> diets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get persistent data
        db = new DbHandler(this);
        allergies = Arrays.asList(db.getAllergies());
        diets = Arrays.asList(db.getDiets());

        // Initialize components
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        llAllergies = findViewById(R.id.llAllergies);
        llAllergiesContent = findViewById(R.id.llAllergiesContent);
        ivAllergies = findViewById(R.id.ivAllergies);

        llDiet = findViewById(R.id.llDiet);
        llDietContent = findViewById(R.id.llDietContent);
        ivDiet = findViewById(R.id.ivDiet);

        // Add listeners and adapters
        btnBack.setOnClickListener(this::onBackClicked);
        btnSave.setOnClickListener(this::onSaveClicked);
        llAllergies.setOnClickListener(this::onAllergiesHeaderClicked);
        llDiet.setOnClickListener(this::onDietHeaderClicked);

        // Setup components
        loadState();
    }

    private void loadState() {
        for (int i = 0; i < llDietContent.getChildCount(); i++) {
            MaterialCheckBox checkBox = (MaterialCheckBox) llDietContent.getChildAt(i);
            checkBox.setChecked(diets.contains(checkBox.getText().toString().toLowerCase()));
        }

        for (int i = 0; i < llAllergiesContent.getChildCount(); i++) {
            MaterialCheckBox checkBox = (MaterialCheckBox) llAllergiesContent.getChildAt(i);
            checkBox.setChecked(allergies.contains(checkBox.getText().toString().toLowerCase()));
        }
    }

    private void onBackClicked(View v) {
        finish();
    }

    private void onSaveClicked(View v) {
        // Save and delete diet changes
        List<String> dietsToInsert = new ArrayList<>();
        List<String> dietsToDelete = new ArrayList<>();

        for (int i = 0; i < llDietContent.getChildCount(); i++) {
            MaterialCheckBox checkBox = (MaterialCheckBox) llDietContent.getChildAt(i);
            if (checkBox.isChecked() && !diets.contains(checkBox.getText().toString().toLowerCase()))
                dietsToInsert.add(checkBox.getText().toString().toLowerCase());
            else if (!checkBox.isChecked() && diets.contains(checkBox.getText().toString().toLowerCase()))
                dietsToDelete.add(checkBox.getText().toString().toLowerCase());
        }

        db.removeDiets(dietsToDelete.toArray(new String[0]));
        db.addDiets(dietsToInsert.toArray(new String[0]));

        // Save and delete allergy changes
        List<String> allergiesToInsert = new ArrayList<>();
        List<String> allergiesToDelete = new ArrayList<>();

        for (int i = 0; i < llAllergiesContent.getChildCount(); i++) {
            MaterialCheckBox checkBox = (MaterialCheckBox) llAllergiesContent.getChildAt(i);
            if (checkBox.isChecked() && !allergies.contains(checkBox.getText().toString().toLowerCase()))
                allergiesToInsert.add(checkBox.getText().toString().toLowerCase());
            else if (!checkBox.isChecked() && allergies.contains(checkBox.getText().toString().toLowerCase()))
                allergiesToDelete.add(checkBox.getText().toString().toLowerCase());
        }

        db.removeAllergies(allergiesToDelete.toArray(new String[0]));
        db.addAllergies(allergiesToInsert.toArray(new String[0]));

        Toast.makeText(getApplicationContext(), "Settings saved successfully", Toast.LENGTH_SHORT).show();
        finish();
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
}