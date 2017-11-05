package com.example.lalit.movieguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    Boolean moviesLoaded,tvLoaded;

    ArrayList<String> keywordsList;
    ArrayList<Movie> moviesList;
    ArrayList<TV> tvList;

    RecyclerView searchActivityMoviesRecyclerView, searchActivityTVRecyclerView;
    LinearLayout searchActivityLinearLayout;
    SmallImageRecyclerViewAdapter moviesAdapter;
    TVAdapter tvAdapter;
    ProgressBar searchActivityProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keywordsList = new ArrayList<>();
        moviesList = new ArrayList<>();
        tvList = new ArrayList<>();

        moviesLoaded = false;
        tvLoaded = false;
        searchActivityProgressBar = findViewById(R.id.searchActivityProgressBar);
        searchActivityProgressBar.setVisibility(View.VISIBLE);
        searchActivityMoviesRecyclerView = findViewById(R.id.searchActivityMoviesRecyclerView);
        searchActivityTVRecyclerView = findViewById(R.id.searchActivityTVRecyclerView);
        searchActivityLinearLayout = findViewById(R.id.searchActivityLinearLayout);
        searchActivityLinearLayout.setVisibility(View.GONE);
        moviesAdapter = new SmallImageRecyclerViewAdapter(this, moviesList, Constants.KEY_POSTER_SMALL, new SmallImageRecyclerViewAdapter.SmallImageMovieClickListener() {
            @Override
            public void onMovieClicked(int position) {
                int movie_id = moviesList.get(position).id;
                Intent intent = new Intent(SearchActivity.this,MovieDetailActivity.class);
                intent.putExtra(Constants.KEY_MOVIE_ID,movie_id);
                startActivity(intent);
            }
            @Override
            public Boolean onFavouriteClicked(int position,Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getApplicationContext());
                Movie movie = moviesList.get(position);
                if(isClicked)
                    database.getMovieDao().deleteMovie(movie);
                else
                    database.getMovieDao().addMovie(movie);
                return true;
            }

        });
        searchActivityMoviesRecyclerView.setAdapter(moviesAdapter);
        searchActivityMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //searchActivityMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false));

        tvAdapter = new TVAdapter(this, tvList, Constants.KEY_POSTER_SMALL, new TVAdapter.TVClickListener() {
            @Override
            public void onTVClicked(int position) {
                int tv_id = tvList.get(position).id;
                Intent intent = new Intent(SearchActivity.this,TVDetailActivity.class);
                intent.putExtra(Constants.KEY_TV_ID,tv_id);
                startActivity(intent);
            }

            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getApplicationContext());
                TV tv = tvList.get(position);
                if(isClicked)
                    database.getTVDao().deleteTV(tv);
                else
                    database.getTVDao().addTV(tv);
                return true;
            }
        });
        searchActivityTVRecyclerView.setAdapter(tvAdapter);
        //searchActivityTVRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false));
        searchActivityTVRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        String query =  getIntent().getStringExtra(Constants.KEY_SEARCH_KEYWORD);
        Collections.addAll(keywordsList,query.split(" "));
        Log.d("Keywords",keywordsList.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/search/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SearchService searchService = retrofit.create(SearchService.class);
        Call<MoviesResponse> moviesResponseCall = searchService.getMoviesQuery(query,Constants.API_KEY);
        moviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                moviesList.addAll(response.body().results);
                moviesAdapter.notifyDataSetChanged();
                moviesLoaded = true;
                checkDataLoaded();
            }
            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.d("Search", "Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        Call<TVResponse> tvResponseCall = searchService.getTVQuery(query, Constants.API_KEY);
        tvResponseCall.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                tvList.addAll(response.body().results);
                tvAdapter.notifyDataSetChanged();
                tvLoaded = true;
                checkDataLoaded();
            }
            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.d("Search", "Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

    }

    private void checkDataLoaded() {
        if(moviesLoaded & tvLoaded){
            searchActivityProgressBar.setVisibility(View.GONE);
            searchActivityLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}
