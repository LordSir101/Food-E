package net.ontariotechu.food_e;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RecipeSearcher implements Runnable{

    private Hashtable<String, ArrayList<String>> filters;
    private String baseUrl;
    DataPasser dataPasser;
    //private ArrayList<Recipe> displayedRecipes;
    public RecipeSearcher(Context context, Hashtable<String, ArrayList<String>> data) {
        this.filters = data;
        this.dataPasser = (DataPasser) context;
        this.baseUrl = String.format("https://api.edamam.com/api/recipes/v2?type=public&app_id=%s&app_key=%s", BuildConfig.APP_ID, BuildConfig.API_KEY);
    }

    public void getRecipes() {

        URLConnection connection = null;

        String urlString = baseUrl;

        // Add query params to the url
        // key of filterData is the filter name
        // value of filterData is array of values for that filter
        for (Map.Entry<String, ArrayList<String>> filterData : filters.entrySet()) {
            for(String filter: filterData.getValue()){
                urlString += String.format("&%s=%s", filterData.getKey(), filter);
            }
        }

        try {
            connection = new URL(urlString).openConnection();
            connection.setRequestProperty("Accept", "application/json");
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            InputStream is = connection.getInputStream();
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONObject obj = new JSONObject(result);

            // TODO do something useful with API results
            System.out.println("#######################################################################################");
            System.out.println(urlString);

            ArrayList recipeResults = new ArrayList<>();
            // example of getting recipe names from result object
            JSONArray arr = obj.getJSONArray("hits");
            for (int i = 0; i < arr.length(); i++) {
                String label = arr.getJSONObject(i).getJSONObject("recipe").getString("label");
                recipeResults.add(new Recipe(label));
            }

            dataPasser.onRecipesFetched(recipeResults);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        getRecipes();
    }
}
