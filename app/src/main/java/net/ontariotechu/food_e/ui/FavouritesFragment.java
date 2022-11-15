package net.ontariotechu.food_e.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import net.ontariotechu.food_e.DbHandler;
import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;
import net.ontariotechu.food_e.RecipeService;

import java.util.Arrays;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private ListView lsRecipes;

    private DbHandler db;
    private List<Recipe> recipes;
    private RecipeAdapter recipeAdapter;
    private RecipeService recipeService;

    private FavouritesFragment() {
        db = new DbHandler(getContext());
        recipeService = RecipeService.getInstance();
    }

    public static FavouritesFragment newInstance() {
        FavouritesFragment fragment = new FavouritesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        // Initialize components
        lsRecipes = view.findViewById(R.id.lsRecipes);

        // Set Listeners and adapters
        recipes = Arrays.asList(db.getAllRecipes());
        recipeAdapter = new RecipeAdapter(getContext(), recipes);
        lsRecipes.setAdapter(recipeAdapter);
        fetchRecipeDetails(recipes);
        return view;
    }

    private void fetchRecipeDetails(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
             recipeService.getRecipeByIdBackground(recipe.getUri(), (r) -> {
                recipe.setTitle(r.getTitle());
                recipe.setImageUrl(r.getImageUrl());
                recipe.setIngredients(r.getIngredients());
                recipeAdapter.notifyDataSetChanged();
            });
        }
    }
}