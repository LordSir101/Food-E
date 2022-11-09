package net.ontariotechu.food_e.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;

public class MainPageAdapter extends FragmentStateAdapter {

    public MainPageAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return BrowseFragment.newInstance();
        else
            return FavouritesFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void getPageName(TabLayout.Tab tab, int position) {
        if (position == 0)
            tab.setText("Browse");
        else
            tab.setText("Favourites");
    }
}
