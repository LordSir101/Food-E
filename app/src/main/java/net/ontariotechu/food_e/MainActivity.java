package net.ontariotechu.food_e;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                URLConnection connection = null;
                try {
                    //TODO hide app key and app id
                    connection = new URL("https://api.edamam.com/api/recipes/v2?type=public&app_id=appId&app_key=appKey&mealType=Dinner").openConnection();
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
                    String next = obj.getJSONObject("_links").getString("next");
                    System.out.println("#######################################################################################");
                    System.out.println(next);

                    JSONArray arr = obj.getJSONArray("hits"); // notice that `"posts": [...]`
                    for (int i = 0; i < arr.length(); i++)
                    {
                        String label = arr.getJSONObject(i).getJSONObject("recipe").getString("label");
                        System.out.println(label);
                    }
                    //System.out.println(connection.getContent().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
//        connection.setRequestProperty("header1", header1);
//        connection.setRequestProperty("header2", header2);
        //Get Response


    }
}