package net.ontariotechu.food_e.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;

import java.util.List;

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
        TextView ttl = recipeView.findViewById(R.id.txtTitle);
        ImageButton btnFavourite = recipeView.findViewById(R.id.btnFavourite);
        btnFavourite.setOnClickListener(v -> {
            if (currentRecipe.getFavourite()) {
                currentRecipe.setFavourite(false);
                Drawable starOutline = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.star_outline, null);
                btnFavourite.setBackground(starOutline);
            } else {
                currentRecipe.setFavourite(true);
                Drawable starSolid = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.star_solid, null);
                btnFavourite.setBackground(starSolid);
            }
        });
        ttl.setText(currentRecipe.getTitle());
        return recipeView;
    }
}
