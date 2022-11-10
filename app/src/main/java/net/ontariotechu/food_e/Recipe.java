package net.ontariotechu.food_e;

import java.util.ArrayList;
import java.util.List;

// Temporary model for testing purposes
public class Recipe {

    private String title;
    private boolean isFavourite;

    public Recipe(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public static List<Recipe> testRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Boiled Milk"));
        recipes.add(new Recipe("Steamed Hams"));
        recipes.add(new Recipe("Green Eggs and Ham"));
        recipes.add(new Recipe("Ceasar Salad"));
        return recipes;
    }
}
