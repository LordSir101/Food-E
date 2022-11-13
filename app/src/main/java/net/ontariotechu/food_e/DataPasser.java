package net.ontariotechu.food_e;

import java.util.ArrayList;
import java.util.Hashtable;

public interface DataPasser {
    void onFilterChanged(Hashtable<String, ArrayList<String>> data);
    void onRecipesFetched(ArrayList<Recipe> recipes);
    ArrayList<Recipe> getDisplayedRecipes();
}
