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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrowseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrowseFragment extends Fragment {

    public BrowseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BrowseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        List<Recipe> recipes = Recipe.testRecipes();
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(), recipes);
        ListView lsRecipes = view.findViewById(R.id.lsRecipes);
        LinearLayout llFilters = view.findViewById(R.id.llFilters);
        ImageButton btnFilters = view.findViewById(R.id.btnFilters);
        btnFilters.setOnClickListener(v -> {
            llFilters.setVisibility(llFilters.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });
        lsRecipes.setAdapter(recipeAdapter);
        // Inflate the layout for this fragment
        return view;
    }
}