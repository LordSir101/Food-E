package net.ontariotechu.food_e;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import net.ontariotechu.food_e.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RecipeService extends Application {

    private static RecipeService instance;
    //private static Context context;
    private final String baseUrl;

    private DbHandler db;
    private List<String> allergies;
    private List<String> diets;

    public RecipeService() {
        this.baseUrl = String.format("https://api.edamam.com/api/recipes/v2?type=public&app_id=%s&app_key=%s", BuildConfig.APP_ID, BuildConfig.API_KEY);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //RecipeService.context = getApplicationContext();
    }

    // Thread safe instantiation
    public static RecipeService getInstance() {
        RecipeService result = instance;
        if (result == null) {
            synchronized (RecipeService.class) {
                result = instance;
                if (result == null) {
                    instance = result = new RecipeService();
                }
            }
        }
        return result;
    }

    public void getRecipesBackground(Context context, @Nullable String search, @Nullable Hashtable<String, ArrayList<String>> filters, RecipeResultCallback callback) {
        Thread thread = new Thread(() -> {
           callback.onComplete(getRecipes(context, search, filters));
        });
        thread.start();
    }

    private List<Recipe> getRecipes(Context context, @Nullable String search, @Nullable Hashtable<String, ArrayList<String>> filters) {

        StringBuilder url = new StringBuilder(baseUrl);

        db = new DbHandler(context);
        allergies = Arrays.asList(db.getAllergies());
        diets = Arrays.asList(db.getDiets());
        db.close();

        if(!allergies.isEmpty()){
            for(String preference:allergies){
                url.append("&health=").append(preference);
            }
        }
        if(!diets.isEmpty()){
            for(String preference:diets){
                url.append("&diet=").append(preference);
            }
        }

        if (TextUtils.isEmpty(search) && (filters == null || filters.size() == 0))
            url.append("&mealType=breakfast&mealType=Lunch&mealType=Dinner");

        if (!TextUtils.isEmpty(search))
            url.append("&q=").append(search);

        if (filters != null && filters.size() > 0) {
            for (Map.Entry<String, ArrayList<String>> filterData : filters.entrySet()) {
                for(String filter: filterData.getValue()){
                    url.append(String.format("&%s=%s", filterData.getKey(), filter));
                }
            }
        }

        try {
            URLConnection connection = new URL(url.toString()).openConnection();
            connection.setRequestProperty("Accept", "application/json");
            InputStream is = connection.getInputStream();
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONObject obj = new JSONObject(result);


            ArrayList<Recipe> recipeResults = new ArrayList<>();
            // example of getting recipe names from result object
            JSONArray arr = obj.getJSONArray("hits");
            for (int i = 0; i < arr.length(); i++) {
                String label = arr.getJSONObject(i).getJSONObject("recipe").getString("label");
                String imageUrl = arr.getJSONObject(i).getJSONObject("recipe").getString("image");
                String uri = arr.getJSONObject(i).getJSONObject("recipe").getString("uri");
                String ingredients = arr.getJSONObject(i).getJSONObject("recipe").getJSONArray("ingredients").toString();

                Recipe newRecipe = new Recipe(label);
                newRecipe.setImageUrl(imageUrl);
                newRecipe.setUri(uri);
                newRecipe.setIngredients(ingredients);
                recipeResults.add(newRecipe);
            }

            return recipeResults;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface RecipeResultCallback {
        void onComplete(List<Recipe> recipes);
    }

}
