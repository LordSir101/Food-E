package net.ontariotechu.food_e.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;
import net.ontariotechu.food_e.RecipeService;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class BrowseFragment extends Fragment {

    private ListView lsRecipes;
    private LinearLayout llFilters;
    private ImageButton btnFilters;
    private ChipGroup cgMeal;
    private ChipGroup cgCuisine;
    private TextInputEditText etSearch;

    private List<Recipe> recipes;
    private RecipeAdapter recipeAdapter;
    private RecipeService recipeService;

    private Hashtable<String, ArrayList<String>> selectedFilters;

    private Context context;

    public BrowseFragment() {
        recipeService = RecipeService.getInstance();
    }

    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //keep track of current filters
        selectedFilters = new Hashtable<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        // Initialize components
        lsRecipes = view.findViewById(R.id.lsRecipes);
        llFilters = view.findViewById(R.id.llFilters);
        btnFilters = view.findViewById(R.id.btnFilters);
        cgMeal = view.findViewById(R.id.cgMeal);
        cgCuisine = view.findViewById(R.id.cgCuisine);
        etSearch = view.findViewById(R.id.etSearch);

        // Set listeners and adapters
        recipes = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getContext(), R.layout.fragment_browse, recipes);
        recipeService.getRecipesBackground(context, null, selectedFilters, this::onRecipeResult);
        lsRecipes.setAdapter(recipeAdapter);

        btnFilters.setOnClickListener(this::onFilterButtonClicked);
        cgMeal.setOnCheckedStateChangeListener(this::onMealChipChanged);
        cgCuisine.setOnCheckedStateChangeListener(this::onCuisineChipChanged);
        etSearch.setOnEditorActionListener(this::onSearch);

        return view;
    }

    private void onFilterButtonClicked(View v) {
        llFilters.setVisibility(llFilters.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
    }

    private void onMealChipChanged(ChipGroup group, List<Integer> checkedIds) {
        selectedFilters.put("mealType", new ArrayList<>());
        for (Integer id:checkedIds){
            Chip chip = group.findViewById(id);
            selectedFilters.get("mealType").add(chip.getText().toString());
        }
        recipeService.getRecipesBackground(context, etSearch.getText().toString(), selectedFilters, this::onRecipeResult);
    }

    private void onCuisineChipChanged(ChipGroup group, List<Integer> checkedIds) {
        selectedFilters.put("cuisineType", new ArrayList<>());
        for (Integer id:checkedIds){
            Chip chip = group.findViewById(id);
            selectedFilters.get("cuisineType").add(chip.getText().toString());
        }
        recipeService.getRecipesBackground(context, etSearch.getText().toString(), selectedFilters, this::onRecipeResult);
    }

    private boolean onSearch(View v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            recipeService.getRecipesBackground(context, etSearch.getText().toString(), selectedFilters, this::onRecipeResult);
            return true;
        }
        return false;
    }

    private void onRecipeResult(List<Recipe> result) {
        getActivity().runOnUiThread(() -> {
            recipes.clear();
            recipeAdapter.clear();
            recipes.addAll(result);
            recipeAdapter.notifyDataSetChanged();
        });
    }
}
