package net.ontariotechu.food_e.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.Recipe;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class BrowseFragment extends Fragment {

    private ListView lsRecipes;
    private LinearLayout llFilters;
    private ImageButton btnFilters;
    private ChipGroup cgMeal;
    private ChipGroup cgCuisine;
    private TextInputEditText etSearch;
    private DataPasser dataPasser;

    private Hashtable<String, ArrayList<String>> selectedFilters;

    // Main Activity implements this interface
    // We can call this function to pass data to Main Activity
    public interface DataPasser {
        void onDataPass(Hashtable<String, ArrayList<String>> data);
    }

    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (DataPasser) context;
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
        List<Recipe> recipes = Recipe.testRecipes();
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), recipes);
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
        dataPasser.onDataPass(selectedFilters);
    }

    private void onCuisineChipChanged(ChipGroup group, List<Integer> checkedIds) {
        selectedFilters.put("cuisineType", new ArrayList<>());
        for (Integer id:checkedIds){
            Chip chip = group.findViewById(id);
            selectedFilters.get("cuisineType").add(chip.getText().toString());
        }
        dataPasser.onDataPass(selectedFilters);
    }

    private boolean onSearch(View v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // TODO: Handle search
            return true;
        }
        return false;
    }
}
