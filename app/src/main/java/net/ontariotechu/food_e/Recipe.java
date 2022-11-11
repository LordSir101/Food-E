package net.ontariotechu.food_e;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String title;
    private String imageUrl;
    private boolean isFavourite;

    public Recipe(String title) {
        this.title = title;
        this.imageUrl = "https://edamam-product-images.s3.amazonaws.com/web-img/e42/e42f9119813e890af34c259785ae1cfb.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEJX%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJHMEUCIQCKJOmGwg96%2FEV9XGPq3IJbOi7yyn%2Fo8v3bNu5U45Dq%2FgIgIcvMhO3tNdheNvmXub2iE2JU%2FUVhW6ajU96Kn2jIMPoq1QQIjf%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwxODcwMTcxNTA5ODYiDOB%2BO1QYpdErAkJ40yqpBAYeX5t1g%2B22os24bRQ6nOdlHbNzp5G9Z2FDYmzqEaekg4Dj77hZ0nX8fsWJNW%2BGQ9d%2BnFM3wKH7U9fhVGdPvSZqkgW26ZaANEIHIScS69K0GIhU%2FzNSCjZJksxFg52oaS9gHK0k02wosNfBI3RAMumKdNKsD4StssM1GtrxEH7MJmbg8TqL2fspJ1eLHxtFfp8lmbEKhfkXMbnLDvzt%2B5DoGACOfdyYQzy4w4XqVH%2F1x5GaAHY8nxKz6ZYXWQ%2FXse6kwFS%2BabMD34UdtLGb8N0zQaFVKInFb8L3JZrixxKwLIjTWQw%2BQlyCOJOoQJCa5wig4ngTZet%2Fb%2F%2FQ%2Bz%2FGoS4I6lrcQoe%2FrH07ThVOBrJrH1eDBNl3rBNU0yc826e6iHCnHms6qvBCMmwyII4LIEHqmYLJhDejqVUXnnPBiFLm8umN2VW0LGEurKnrpdhBLgajiznvo3x14P%2B4PPuWq7Ddi0IxwrKBIYLkIY9VEypoUOEEiMtoVkJBwRkjkskLfaDMaNGM5vnp9i65nFgU7%2B5izf6EqiIGrSL7t9eAYWDEGPhD3yOvNF6s9CIljQCQtubnEXgx8XhD5mxO90QntRnRmHugsOCxhnvEYvYENIcLDs%2FCM4NaI%2F4dLDU%2FStQQ7Rxy8bJ9qetvUt6uSb1k0AUlgpECpVzgk1zrRUggbgwneu5kJb4HZ63lSd6giS%2BAOldxXu56mHwCq%2BqEwkI4OJfHDd2yzeuMW0Aw3fq4mwY6qQEKDYPP4Yc2x31EbE%2FMTpqyLHSzUBHByx%2BMvxFshcNEzVXxmIYarpxyW5oUW9TwU78rn8sNwurexi1Qt3TN5jyBDoOUqUyiOxxYjhVhKpdxhT3stzRDEnESEf9aqAJ8SIKKYJcdZpDoH3t%2FNH0P23LMagIy4OU605YT5awglyA7XHXdQnt%2BBRWlo00ZrO3qroW3OdaN6T0y6xxgbqise2pgJTp8SCjzn45b&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221111T132510Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFKISAST63%2F20221111%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=c68f6dbd17f09049cbf9c3847726654d07bb4917a943305138c997b3b75dc70b";
    }
    public Recipe() {
    }

    public RecipeBuilder builder() {
        return new RecipeBuilder();
    }

    public static class RecipeBuilder {
        private String title;
        private String imageUrl;
        private boolean isFavourite;

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

        public Recipe build() {
            Recipe result = new Recipe();
            result.title = title;
            result.imageUrl = imageUrl;
            result.isFavourite = isFavourite;
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

    public static List<Recipe> testRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Boiled Milk"));
        recipes.add(new Recipe("Steamed Hams"));
        recipes.add(new Recipe("Green Eggs and Ham"));
        recipes.add(new Recipe("Ceasar Salad"));
        return recipes;
    }
}
