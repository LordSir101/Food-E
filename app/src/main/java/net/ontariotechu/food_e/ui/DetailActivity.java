package net.ontariotechu.food_e.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import net.ontariotechu.food_e.DbHandler;
import net.ontariotechu.food_e.ImageService;
import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView txtTitle;
    private ImageButton btnFavourite;
    private ImageButton btnBack;
    private ImageView ivRecipe;
    private Recipe recipe;
    private LinearLayout llIngredients;

    private ImageService imageService;

    private DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageService = ImageService.getInstance();
        db = new DbHandler(getApplicationContext());

        Intent intent = getIntent();
        recipe = (Recipe) intent.getSerializableExtra("recipe");

        // Initialize components
        txtTitle = findViewById(R.id.txtTitle);
        btnFavourite = findViewById(R.id.btnFavourite);
        btnBack = findViewById(R.id.btnBack);
        ivRecipe = findViewById(R.id.ivRecipe);
        llIngredients = findViewById(R.id.llIngredients);

        // Add listeners and adapters
        btnFavourite.setOnClickListener(this::onFavouriteClicked);
        btnBack.setOnClickListener(this::onBackClicked);

        // Setup view
        txtTitle.setText(recipe.getTitle());
        loadIngredients(recipe.getIngredients());
        setFavouriteButton(btnFavourite, recipe.getFavourite());

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
        setFavouriteButton((ImageButton) v, recipe.getFavourite());
        if (!recipe.getFavourite()) {
            db.removeRecipe(recipe.getUri());
        } else {
            db.addRecipe(recipe);
        }
    }

    // Changes the star from outline to solid or solid to outline
    private void setFavouriteButton(ImageButton button, boolean isFavourite) {
        if (!isFavourite) {
            Drawable starOutline = ResourcesCompat.getDrawable(getResources(), R.drawable.star_outline, null);
            button.setBackground(starOutline);
        } else {
            Drawable starSolid = ResourcesCompat.getDrawable(getResources(), R.drawable.star_solid, null);
            button.setBackground(starSolid);
        }
    }

    private void onBackClicked(View v) {
        finish();
    }

    private void loadIngredients(String ingredients) {

        try{
            JSONArray ingredientsJSON = new JSONArray(ingredients);

            for (int i = 0; i < ingredientsJSON.length(); i++) {
                String text = ingredientsJSON.getJSONObject(i).getString("text");
                View view = LayoutInflater.from(this).inflate(R.layout.fragment_bullet_item, findViewById(androidx.appcompat.R.id.content), false);
                TextView txtItem = view.findViewById(R.id.txtItem);
                txtItem.setText(text);
                llIngredients.addView(view);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
