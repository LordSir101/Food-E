package net.ontariotechu.food_e;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String title;
    private String imageUrl;
    private boolean isFavourite;
    private String uri;

    public Recipe(String title) {
        this.title = title;
        this.imageUrl = "https://edamam-product-images.s3.amazonaws.com/web-img/e42/e42f9119813e890af34c259785ae1cfb.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEJn%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaCXVzLWVhc3QtMSJIMEYCIQDzbhbjasl%2BdmcPtwT7npppLMQ3MjDLWYkYKZast%2BoxAgIhANg751sfSIckhVC5kqghrVGskbV5DCsyRCJD6nqqYpQxKtUECJL%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEQABoMMTg3MDE3MTUwOTg2IgzpcdYvHb9tLsm8FMsqqQTRNwAhDpd4tqPiGKryIoHX7tIT6c2fivlS1kTLme%2FfWqHUsmZ74zsUOiuV4UnY%2BYv%2BHqCezz8ySxp6P3MUPbXzGUSekWn5BTDA6Ia6VPRl8bbma2zrnxY5Wl48vPVQLbkpJP4UDuWCh4uS8Pr6DPeKM6ftUHOqpiPjnye0PPH0N5mxSf4ZIHS2KgfxbI5wegKjjbLHvXlRm0REFignUqyvmMjF16D%2Bu5F3cuOdPSWHXaJu25zM25uaDiq9%2BuErGN%2BvrdOGtqc1JZ9cctWT7Y3%2F0UNqfKO3b4eukR8TVCuVQtXeXDnMr3FLrBfoso4xMXtlqJChJ73frKwImB%2BV4TtXWjCahsmngL5ycxvCWIxIZL7hiKnizcpoIpR9n14M7mE%2BK3xwTc3y8mN9%2FKXrRAFFjL9II8Hm1%2FjhyMMCmGK5NBFPWNWkS%2FzE8gqyoQAJAYna7%2Fd1ybrh8saK3r9vSZF4JhB93QPuXcyP3ISVRCAV%2BUS5oSJD1tN6SnXV5Es6qB%2FVv7jWwNYFrxYR1gNS%2FHhwtaR%2FWWz4DqLbc0BD9I0W1LFkWNUAIBPkgJIMfHzpS5NRQk2odfWbbT1fzzPu6rC10qQZXaCton5dWhjQ0WRDaH0hXJdqNkiUAcai60j%2FKpZUr8Ny8gZJ3MuJAuTefNfxo1vBLmZuuCICTC7EnclFc7slBUZ5BhV0%2Fvof1LfMBc5ekwE9eLls9cHCpxJ2lFFG73WJUdOa5tD2MOCAupsGOqgBUPyBOOeGPhKtGcbZ3BJrTH9WX9T%2BzUil08hvA%2B%2BnMQHdzyKa%2B%2BFoYWDx0CriaOIkjcLOdnR8DdazEor2HQC6QzeRm9HIY1opgD71j0OFY0PRl6%2BCGSCd9J0HcZuQE1NgS5%2B0MoX564xiAUVMySTW4R4%2BiRIh5VCnUJgp3B5mWtv0mhCvOtkksgmEtBx7VeNThQVPYHdT8DkYog%2BtbgEKJaPD0zHPN6YF&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221111T175329Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFCZVMD6FI%2F20221111%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=66221e070e5dfcc4fc9ae56ac0d95858901d8fcaf66a6849d026d45a42a4f2ce";
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

    public void setUri(String sourceUri) { uri = sourceUri; }

    public String getUri() { return uri; }

    public static List<Recipe> testRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Boiled Milk"));
        recipes.add(new Recipe("Steamed Hams"));
        recipes.add(new Recipe("Green Eggs and Ham"));
        recipes.add(new Recipe("Caesar Salad"));
        return recipes;
    }
}
