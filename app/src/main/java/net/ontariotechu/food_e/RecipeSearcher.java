package net.ontariotechu.food_e;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class RecipeSearcher implements Runnable{

    private ArrayList<String> filters;
    private String baseUrl;
    public RecipeSearcher(ArrayList data) {
        this.filters = data;
        this.baseUrl = String.format("https://api.edamam.com/api/recipes/v2?type=public&app_id=%s&app_key=%s", BuildConfig.APP_ID, BuildConfig.API_KEY);
    }

    public void getRecipes() {

        URLConnection connection = null;

        String urlString = baseUrl;
        for(String filter:this.filters){
            urlString += String.format("&mealType=%s", filter);
        }

        try {
            //TODO hide app key and app id
            connection = new URL(urlString).openConnection();
            connection.setRequestProperty("Accept", "application/json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = connection.getInputStream();
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            System.out.println(result);
            JSONObject obj = new JSONObject(result);
            //String next = obj.getJSONObject("_links").getString("next");
            System.out.println("#######################################################################################");
            System.out.println(urlString);
            //System.out.println(next);

            JSONArray arr = obj.getJSONArray("hits"); // notice that `"posts": [...]`
            for (int i = 0; i < arr.length(); i++) {
                String label = arr.getJSONObject(i).getJSONObject("recipe").getString("label");
                System.out.println(label);
            }
            //System.out.println(connection.getContent().toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        getRecipes();
    }
}
