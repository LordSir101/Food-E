package net.ontariotechu.food_e.ui;

import android.content.Context;
import android.content.Intent;
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

import net.ontariotechu.food_e.DbHandler;
import net.ontariotechu.food_e.ImageService;
import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    List<Recipe> recipes;
    ImageService imageService;
    DbHandler dbHandler;

    public RecipeAdapter(@NonNull Context context, int resource, List<Recipe> recipes) {
        super(context, resource, recipes);
        this.recipes = recipes;
        this.imageService = ImageService.getInstance();
    }

    @Override
    public View getView(int pos, View view, ViewGroup parent) {
        View recipeView = view;
        if (recipeView == null) {
            recipeView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recipe, parent, false);
        }

        Recipe currentRecipe = recipes.get(pos);

        // Initialize components
        TextView txtTitle = recipeView.findViewById(R.id.txtTitle);
        ImageButton btnFavourite = recipeView.findViewById(R.id.btnFavourite);
        setFavouriteButton(btnFavourite, currentRecipe.getFavourite());
        ImageView ivRecipe = recipeView.findViewById(R.id.ivRecipe);

        // Add listeners and adapters
        btnFavourite.setOnClickListener(v -> {
            currentRecipe.setFavourite(!currentRecipe.getFavourite());
            dbHandler = new DbHandler(getContext());
            if (currentRecipe.getFavourite()) {
                dbHandler.addRecipe(currentRecipe);
            } else {
                dbHandler.removeRecipe(currentRecipe.getUri());
            }
            setFavouriteButton(btnFavourite, currentRecipe.getFavourite());
        });
        recipeView.setOnClickListener((View v) -> {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("recipe", currentRecipe);
            getContext().startActivity(intent);
        });

        // Setup components
        txtTitle.setText(currentRecipe.getTitle());
        imageService.getImageBackground(currentRecipe.getImageUrl(), (bm) -> {
            if (bm != null) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    ivRecipe.setImageBitmap(bm);
                });
            }
        });

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

    @Override
    public int getCount() {
        return recipes != null ? recipes.size() : 0;
    }
}
