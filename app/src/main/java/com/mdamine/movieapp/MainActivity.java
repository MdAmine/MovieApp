package com.mdamine.movieapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.mdamine.movieapp.adapters.ScrollTabsAdapter;
import com.mdamine.movieapp.fragments.PopularMoviesFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private Toolbar toolbar;

    private ViewPager viewPager;
    private ScrollTabsAdapter adapter;
    private TabLayout tabLayout;

    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Main");

        setUpScrollTabs();
        setUpDrawer();

    }


    private void setUpScrollTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        fragmentList.add(new PopularMoviesFragment());
        titleList.add("Popular");

        for (int i = 0; i < 3; i++) {
            fragmentList.add(new Fragment());
            titleList.add("Fragment " + i);
        }

        adapter = new ScrollTabsAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        String itemName = (String) item.getTitle();

        drawerLayout.closeDrawer(GravityCompat.START);

        Toast.makeText(this, itemName, Toast.LENGTH_SHORT).show();

        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}
