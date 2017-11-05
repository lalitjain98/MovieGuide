package com.example.lalit.movieguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);

        final SearchView searchView = findViewById(R.id.mainSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals("")){
                    Log.d("Search String",query);
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra(Constants.KEY_SEARCH_KEYWORD,query);
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                searchView.setLayoutParams(new SearchView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                toolbar.setLayoutParams(new Toolbar.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));

            }
        });
        if(searchView.toString().equals("")){
            String searchString = searchView.getQuery().toString();
            Log.d("Search String",searchString);
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra(Constants.KEY_SEARCH_KEYWORD,searchString);
            startActivity(intent);
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                //        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FrameLayout moviesFrameLayout = findViewById(R.id.mainActivityFragment);
        FragmentManager fragmentManager  = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainActivityFragment,new MoviesFragment()).commit();
        //fragmentManager.beginTransaction().replace(R.id.mainActivityFragment,new TVFragment()).commit();
        toolbar.setTitle("Movies");
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();
        FragmentManager fragmentManager  = getSupportFragmentManager();
        if (id == R.id.nav_movies) {
            toolbar.setTitle("Movies");
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            fragmentManager.beginTransaction().replace(R.id.mainActivityFragment,new MoviesFragment()).commit();

        } else if (id == R.id.nav_tv_shows) {
            toolbar.setTitle("TV Shows");
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            fragmentManager.beginTransaction().replace(R.id.mainActivityFragment,new TVFragment()).commit();

        } else if (id == R.id.nav_favourites) {
            toolbar.setTitle("Favourites");
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            fragmentManager.beginTransaction().replace(R.id.mainActivityFragment,new FavouritesFragment()).commit();

        }
        else if (id == R.id.nav_about) {
            toolbar.setTitle("About");
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            fragmentManager.beginTransaction().replace(R.id.mainActivityFragment,new AboutFragment()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
