package net.ontariotechu.food_e.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.function.Function;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    List<Recipe> recipes;

    public RecipeAdapter(@NonNull Context context, List<Recipe> recipes) {
        super(context, 0, recipes);
        this.recipes = recipes;
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        View recipeView = view;
        if (recipeView == null)
            recipeView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recipe, parent, false);

        Recipe currentRecipe = recipes.get(pos);

        // Initialize components
        TextView txtTitle = recipeView.findViewById(R.id.txtTitle);
        ImageButton btnFavourite = recipeView.findViewById(R.id.btnFavourite);
        ImageView ivRecipe = recipeView.findViewById(R.id.ivRecipe);

        // Add listeners and adapters
        btnFavourite.setOnClickListener(v -> {
            currentRecipe.setFavourite(!currentRecipe.getFavourite());
            setFavouriteButton(btnFavourite, currentRecipe.getFavourite());
        });
        recipeView.setOnClickListener(this::onItemClicked);

        txtTitle.setText(currentRecipe.getTitle());
        loadImage(currentRecipe.getImageUrl(), ivRecipe);
        return recipeView;
    }

    // Changes the star from outline to solid or solid to outline
    private void setFavouriteButton(ImageButton button, boolean isFavourite) {
        if (!isFavourite) {
            Drawable starOutline = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.star_outline, null);
            button.setBackground(starOutline);
        } else {
            Drawable starSolid = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.star_solid, null);
            button.setBackground(starSolid);
        }
    }

    private void onItemClicked(View v) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        getContext().startActivity(intent);
    }

    private void loadImage(String url, ImageView imageView) {
        Thread thread = new Thread(() -> {
            try {
                InputStream inputStream = new URL(url).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                // Update on ui thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    imageView.setImageBitmap(bitmap);
                });
            } catch (IOException ex) {

            }
        });
        thread.start();
    }

}
