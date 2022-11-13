package net.ontariotechu.food_e.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import net.ontariotechu.food_e.R;
import net.ontariotechu.food_e.RecipeSearcher;

import java.util.ArrayList;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity implements BrowseFragment.DataPasser {

    private ViewPager2 viewPager;
    private TabLayout tabs;
    private ImageButton btnSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        btnSettings = findViewById(R.id.btnSettings);

        // Set up tab view
        MainPageAdapter pageAdapter = new MainPageAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(pageAdapter);
        new TabLayoutMediator(tabs, viewPager, pageAdapter::getPageName).attach();
        // Add listeners and adapters
        btnSettings.setOnClickListener(this::onSettingsClicked);

    }

    public void onSettingsClicked(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDataPass(Hashtable<String, ArrayList<String>> data) {
        // Make the API call from RecipeSearcher
        Runnable recipeSearcher = new RecipeSearcher(data);
        Thread thread = new Thread(recipeSearcher);
        thread.start();
    }
}
