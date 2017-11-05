package com.example.lalit.movieguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowAllActivity extends AppCompatActivity {
    ProgressBar showAllActivityProgressBar, showAllActivityBottomProgressBar;
    RecyclerView showAllActivityRecyclerView;
    ArrayList<Movie> moviesArrayList;
    SmallImageRecyclerViewAdapter moviesAdapter;
    GridLayoutManager gridLayoutManager;
    public static String MOVIES_CATEGORY;
    int currentPage,lastLoadedPage, totalPages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);

        MOVIES_CATEGORY = getIntent().getStringExtra(Constants.KEY_MOVIES_CATEGORY);
        currentPage = 1;
        lastLoadedPage = 0;
        totalPages = -1;
        showAllActivityProgressBar = findViewById(R.id.showAllActivityProgressBar);
        showAllActivityBottomProgressBar = findViewById(R.id.showAllActivityBottomProgressBar);
        showAllActivityBottomProgressBar.setVisibility(View.GONE);
        showAllActivityRecyclerView = findViewById(R.id.showAllActivityRecyclerView);
        moviesArrayList = new ArrayList<>();
        moviesAdapter = new SmallImageRecyclerViewAdapter(this, moviesArrayList, Constants.KEY_POSTER_SMALL, new SmallImageRecyclerViewAdapter.SmallImageMovieClickListener() {
            @Override
            public void onMovieClicked(int position) {
                showDetails(moviesArrayList,position);
            }
            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getApplicationContext());
                Movie movie = moviesArrayList.get(position);
                if(isClicked)
                    database.getMovieDao().deleteMovie(movie);
                else
                    database.getMovieDao().addMovie(movie);
                return true;
            }

        });
        showAllActivityRecyclerView.setAdapter(moviesAdapter);
        gridLayoutManager = new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        showAllActivityRecyclerView.setLayoutManager(gridLayoutManager);
        //showAllActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        showAllActivityRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.computeVerticalScrollOffset()>0 ){
                    int lastItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                    if(lastItemPosition == moviesArrayList.size()-1){
                        showAllActivityBottomProgressBar.setVisibility(View.VISIBLE);
                        LoadMovies();
                    }
                }
                else {
                    //Log.d("Show All Movies", String.valueOf(newState));

                    Log.d("OFFSET", String.valueOf(recyclerView.computeVerticalScrollOffset()));
                    Log.d("RANGE", String.valueOf(recyclerView.computeVerticalScrollRange()));

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.d("Show All Movies", String.valueOf(dy));

            }
        });
        if(totalPages == -1 || lastLoadedPage < totalPages)
            LoadMovies();
        else{
            showAllActivityBottomProgressBar.setVisibility(View.GONE);

        }
    }

    private void LoadMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesService moviesService = retrofit.create(MoviesService.class);
        Call<MoviesResponse> moviesResponseCall = null;
        switch(MOVIES_CATEGORY) {
            case Constants.MOVIES_NOW_SHOWING : moviesResponseCall = moviesService.getNowShowingMovies(String.valueOf(currentPage), Constants.API_KEY);
                break;
            case Constants.MOVIES_POPULAR : moviesResponseCall = moviesService.getPopularMovies(String.valueOf(currentPage), Constants.API_KEY);
                break;
            case Constants.MOVIES_TOP_RATED : moviesResponseCall = moviesService.getTopRatedMovies(String.valueOf(currentPage), Constants.API_KEY);
                break;
            case Constants.MOVIES_UPCOMING : moviesResponseCall = moviesService.getUpcomingMovies(String.valueOf(currentPage), Constants.API_KEY);
                break;
        }

        if(moviesResponseCall != null){
            moviesResponseCall.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    if(response.body() == null){
                        return;
                    }
                    if(totalPages == -1){
                        totalPages = response.body().totalPages;
                    }
                    Log.d("Page Number", String.valueOf(lastLoadedPage));
                    lastLoadedPage = currentPage;

                    if(lastLoadedPage >= totalPages){
                        //Log.d("Page Number Check", "End Of List");
                        Snackbar.make(showAllActivityRecyclerView,"End of List",Snackbar.LENGTH_SHORT).show();
                        showAllActivityBottomProgressBar.setVisibility(View.GONE);
                        showAllActivityRecyclerView.setVisibility(View.VISIBLE);
                        return;
                    }

                    currentPage++;
                    moviesArrayList.addAll(response.body().results);
                    showAllActivityBottomProgressBar.setVisibility(View.GONE);
                    showAllActivityProgressBar.setVisibility(View.GONE);
                    moviesAdapter.notifyDataSetChanged();
                    showAllActivityRecyclerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Show All Movies", "Failed");
                    try {
                        throw t;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        }
    }

    private void showDetails(ArrayList<Movie> moviesList, int position) {
        int id = moviesList.get(position).id;
        Intent intent = new Intent(this,MovieDetailActivity.class);
        intent.putExtra(Constants.KEY_MOVIE_ID,id);
        startActivity(intent);
    }

}
