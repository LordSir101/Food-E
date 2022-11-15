package net.ontariotechu.food_e.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
    private TextView txtNoFavourites;

    private FavouritesFragment() {
        recipeService = RecipeService.getInstance();
    }

    public static FavouritesFragment newInstance() {
        FavouritesFragment fragment = new FavouritesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = new DbHandler(getContext());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        // Initialize components
        lsRecipes = view.findViewById(R.id.lsRecipes);
        txtNoFavourites = view.findViewById(R.id.txtNoFavourites);

        // Set Listeners and adapters
        recipes = Arrays.asList(db.getAllRecipes());
        recipeAdapter = new RecipeAdapter(getContext(), recipes);
        lsRecipes.setAdapter(recipeAdapter);

        // Setup components
        fetchRecipeDetails(recipes);
        txtNoFavourites.setVisibility(recipes.size() == 0 ? View.VISIBLE : View.GONE);
        return view;
    }

    private void fetchRecipeDetails(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
             recipeService.getRecipeByIdBackground(recipe.getUri(), (r) -> {
                 getActivity().runOnUiThread(() -> {
                     recipe.setTitle(r.getTitle());
                     recipe.setImageUrl(r.getImageUrl());
                     recipe.setIngredients(r.getIngredients());
                     recipeAdapter.notifyDataSetChanged();
                 });
            });
        }
    }
}