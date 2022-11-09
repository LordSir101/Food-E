package net.ontariotechu.food_e.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;

import java.util.List;

public class BrowseFragment extends Fragment {

    private ListView lsRecipes;
    private LinearLayout llFilters;
    private ImageButton btnFilters;

    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        // Initialize components
        lsRecipes = view.findViewById(R.id.lsRecipes);
        llFilters = view.findViewById(R.id.llFilters);
        btnFilters = view.findViewById(R.id.btnFilters);

        // Set listeners and adapters
        List<Recipe> recipes = Recipe.testRecipes();
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), recipes);
        btnFilters.setOnClickListener(this::onFilterButtonClicked);
        lsRecipes.setAdapter(recipeAdapter);

        return view;
    }

    private void onFilterButtonClicked(View v) {
        llFilters.setVisibility(llFilters.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }
}