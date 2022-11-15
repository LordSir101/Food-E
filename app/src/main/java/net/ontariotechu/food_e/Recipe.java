package net.ontariotechu.food_e;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe  implements Serializable {

    private String title;
    private String imageUrl;
    private boolean isFavourite;
    private String uri;
    private String ingredients;

    public Recipe(String title) {
        this.title = title;
    }
    public Recipe() {
    }

    public static RecipeBuilder builder() {
        return new RecipeBuilder();
    }

    public static class RecipeBuilder {
        private String title;
        private String imageUrl;
        private boolean isFavourite;
        private String uri;
        private String ingredients;

        public RecipeBuilder title(String title) {
            this.title = title;
            return this;
        }

        public RecipeBuilder imageUrl(String url) {
            this.imageUrl = url;
            return this;
        }

        public RecipeBuilder isFavourite(boolean favourite) {
            this.isFavourite = favourite;
            return this;
        }

        public RecipeBuilder uri(String uri) {
            this.uri = uri;
            return this;
        }

        public RecipeBuilder ingredients(String url) {
            this.imageUrl = url;
            return this;
        }

        public Recipe build() {
            Recipe result = new Recipe();
            result.title = title;
            result.imageUrl = imageUrl;
            result.isFavourite = isFavourite;
            result.uri = uri;
            result.ingredients = ingredients;
            return result;
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String url) {
        imageUrl = url;
    }

    public boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public void setUri(String sourceUri) { uri = sourceUri; }

    public String getUri() { return uri; }

    public void setIngredients(String ingredients) { this.ingredients = ingredients; }

    public String getIngredients() { return ingredients; }

    public static List<Recipe> testRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Boiled Milk"));
        recipes.add(new Recipe("Steamed Hams"));
        recipes.add(new Recipe("Green Eggs and Ham"));
        recipes.add(new Recipe("Caesar Salad"));
        return recipes;
    }
}
