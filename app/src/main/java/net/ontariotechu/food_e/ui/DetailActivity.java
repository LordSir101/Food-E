package net.ontariotechu.food_e.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import net.ontariotechu.food_e.ImageService;
import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;

public class DetailActivity extends AppCompatActivity {

    private TextView txtTitle;
    private ImageButton btnFavourite;
    private ImageButton btnBack;
    private ImageView ivRecipe;
    private Recipe recipe;

    private ImageService imageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageService = ImageService.getInstance();

        // TODO: Get this recipe from service using id passed by intent possibly
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        recipe = new Recipe("Steamed Hams");

        // Initialize components
        txtTitle = findViewById(R.id.txtTitle);
        btnFavourite = findViewById(R.id.btnFavourite);
        btnBack = findViewById(R.id.btnBack);
        ivRecipe = findViewById(R.id.ivRecipe);

        // Add listeners and adapters
        btnFavourite.setOnClickListener(this::onFavouriteClicked);
        btnBack.setOnClickListener(this::onBackClicked);

        // Setup view
        txtTitle.setText(recipe.getTitle());

        imageService.getImageBackground(recipe.getImageUrl(), (bm) -> {
            if (bm != null) {
                runOnUiThread(() -> {
                    ivRecipe.setImageBitmap(bm);
                });
            }
        });

    }

    private void onFavouriteClicked(View v) {
        recipe.setFavourite(!recipe.getFavourite());
        if (!recipe.getFavourite()) {
            Drawable starOutline = ResourcesCompat.getDrawable(getResources(), R.drawable.star_outline, null);
            btnFavourite.setBackground(starOutline);
        } else {
            Drawable starSolid = ResourcesCompat.getDrawable(getResources(), R.drawable.star_solid, null);
            btnFavourite.setBackground(starSolid);
        }
    }

    private void onBackClicked(View v) {
        finish();
    }

}
