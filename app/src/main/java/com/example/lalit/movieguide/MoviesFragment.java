package com.example.lalit.movieguide;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements View.OnClickListener {
    FrameLayout nowShowingMoviesFrameLayout,popularMoviesFrameLayout,topRatedMoviesFrameLayout,upcomingMoviesFrameLayout;
    RecyclerView nowShowingMoviesRecyclerView, popularMoviesRecyclerView, topRatedMoviesRecyclerView, upcomingMoviesRecyclerView;
    ArrayList<Movie> nowShowingMoviesList, popularMoviesList, topRatedMoviesList, upcomingMoviesList;
    SmallImageRecyclerViewAdapter nowShowingMoviesAdapter, popularMoviesAdapter, topRatedMoviesAdapter, upcomingMoviesAdapter;
    TextView showAllNowShowingMoviesTextView, showAllPopularMoviesTextView, showAllUpcomingMoviesTextView, showAllTopRatedMoviesTextView;
    ProgressBar moviesFragmentProgressBar;
    ScrollView moviesFragmentScrollView;
    int allDataLoaded;
    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        allDataLoaded = 0;
        moviesFragmentScrollView = view.findViewById(R.id.moviesFragmentScrollView);
        moviesFragmentProgressBar = view.findViewById(R.id.moviesFragmentProgressBar);
        moviesFragmentScrollView.setVisibility(View.GONE);
        moviesFragmentProgressBar.setVisibility(View.VISIBLE);
        showAllNowShowingMoviesTextView = view.findViewById(R.id.showAllNowShowingMoviesTextView);
        showAllPopularMoviesTextView =  view.findViewById(R.id.showAllPopularMoviesTextView);
        showAllUpcomingMoviesTextView = view.findViewById(R.id.showAllUpcomingMoviesTextView);
        showAllTopRatedMoviesTextView = view.findViewById(R.id.showAllTopRatedMoviesTextView);

        showAllNowShowingMoviesTextView.setOnClickListener(this);
        showAllPopularMoviesTextView.setOnClickListener(this);
        showAllUpcomingMoviesTextView.setOnClickListener(this);
        showAllTopRatedMoviesTextView.setOnClickListener(this);

        nowShowingMoviesRecyclerView = view.findViewById(R.id.nowShowingMoviesRecyclerView);
        popularMoviesRecyclerView = view.findViewById(R.id.popularMoviesRecyclerView);
        topRatedMoviesRecyclerView = view.findViewById(R.id.topRatedMoviesRecyclerView);
        upcomingMoviesRecyclerView = view.findViewById(R.id.upcomingMoviesRecyclerView);

        nowShowingMoviesList = new ArrayList<>();
        popularMoviesList = new ArrayList<>();
        topRatedMoviesList = new ArrayList<>();
        upcomingMoviesList = new ArrayList<>();

        nowShowingMoviesAdapter = new SmallImageRecyclerViewAdapter(getContext(), nowShowingMoviesList,Constants.KEY_POSTER_LARGE, new SmallImageRecyclerViewAdapter.SmallImageMovieClickListener() {
            @Override
            public void onMovieClicked(int position) {
                showDetails(nowShowingMoviesList,position);
            }
            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getContext());
                Movie movie = nowShowingMoviesList.get(position);
                if(isClicked)
                    database.getMovieDao().deleteMovie(movie);
                else
                    database.getMovieDao().addMovie(movie);
                return true;
            }
        });

        popularMoviesAdapter = new SmallImageRecyclerViewAdapter(getContext(), popularMoviesList,Constants.KEY_POSTER_SMALL, new SmallImageRecyclerViewAdapter.SmallImageMovieClickListener() {
            @Override
            public void onMovieClicked(int position) {
                showDetails(popularMoviesList,position);

            }
            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getContext());
                Movie movie = popularMoviesList.get(position);
                if(isClicked)
                    database.getMovieDao().deleteMovie(movie);
                else
                    database.getMovieDao().addMovie(movie);
                return true;
            }

        }
        );

        topRatedMoviesAdapter = new SmallImageRecyclerViewAdapter(getContext(), topRatedMoviesList, Constants.KEY_POSTER_SMALL, new SmallImageRecyclerViewAdapter.SmallImageMovieClickListener() {
            @Override
            public void onMovieClicked(int position) {
                showDetails(topRatedMoviesList,position);
            }
            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getContext());
                Movie movie = popularMoviesList.get(position);
                if(isClicked)
                    database.getMovieDao().deleteMovie(movie);
                else
                    database.getMovieDao().addMovie(movie);

                return true;
            }

        });

        upcomingMoviesAdapter = new SmallImageRecyclerViewAdapter(getContext(), upcomingMoviesList, Constants.KEY_POSTER_LARGE, new SmallImageRecyclerViewAdapter.SmallImageMovieClickListener() {
            @Override
            public void onMovieClicked(int position) {
                showDetails(upcomingMoviesList,position);
            }
            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getContext());
                Movie movie = popularMoviesList.get(position);
                if(isClicked)
                    database.getMovieDao().deleteMovie(movie);
                else
                    database.getMovieDao().addMovie(movie);
                return true;
            }
        });

        nowShowingMoviesRecyclerView.setAdapter(nowShowingMoviesAdapter);
        popularMoviesRecyclerView.setAdapter(popularMoviesAdapter);
        topRatedMoviesRecyclerView.setAdapter(topRatedMoviesAdapter);
        upcomingMoviesRecyclerView.setAdapter(upcomingMoviesAdapter);

        RecyclerView.LayoutManager nowShowingMoviesLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager popularMoviesLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager topRatedMoviesLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager upcomingMoviesLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);

        nowShowingMoviesRecyclerView.setLayoutManager(nowShowingMoviesLayoutManager);
//        popularMoviesRecyclerView.setLayoutManager(gridLayoutManager);
        popularMoviesRecyclerView.setLayoutManager(popularMoviesLayoutManager);
        topRatedMoviesRecyclerView.setLayoutManager(topRatedMoviesLayoutManager);
        upcomingMoviesRecyclerView.setLayoutManager(upcomingMoviesLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .build();

        MoviesService moviesService = retrofit.create(MoviesService.class);
        Call<MoviesResponse> nowShowingMoviesResponseCall = moviesService.getNowShowingMovies(Constants.API_KEY);
        Call<MoviesResponse> popularMoviesResponseCall = moviesService.getPopularMovies(Constants.API_KEY);
        Call<MoviesResponse> topRatedMoviesResponseCall = moviesService.getTopRatedMovies(Constants.API_KEY);
        Call<MoviesResponse> upcomingMoviesResponseCall = moviesService.getUpcomingMovies(Constants.API_KEY);



        nowShowingMoviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                nowShowingMoviesList.addAll(response.body().results);
                nowShowingMoviesAdapter.notifyDataSetChanged();
                Log.d("Now Showing Movies", String.valueOf(nowShowingMoviesList.size()));
                allDataLoaded++;
                checkDataLoaded();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
        popularMoviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                popularMoviesList.addAll(response.body().results);
                popularMoviesAdapter.notifyDataSetChanged();
                Log.d("Popular Movies", String.valueOf(popularMoviesList.size()));
                allDataLoaded++;
                checkDataLoaded();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
        topRatedMoviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                topRatedMoviesList.addAll(response.body().results);
                topRatedMoviesAdapter.notifyDataSetChanged();
                Log.d("Top Rated Movies", String.valueOf(topRatedMoviesList.size()));
                allDataLoaded++;
                checkDataLoaded();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
        upcomingMoviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                upcomingMoviesList.addAll(response.body().results);
                upcomingMoviesAdapter.notifyDataSetChanged();
                Log.d("Upcoming Movies", String.valueOf(upcomingMoviesList.size()));
                allDataLoaded++;
                checkDataLoaded();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
        return view;
    }

    private void checkDataLoaded() {
        if(allDataLoaded == 4){
            moviesFragmentProgressBar.setVisibility(View.GONE);
            moviesFragmentScrollView.setVisibility(View.VISIBLE);
        }
    }


    private void showDetails(ArrayList<Movie> moviesList, int position) {
        int id = moviesList.get(position).id;
        Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
        intent.putExtra(Constants.KEY_MOVIE_ID,id);
        startActivity(intent);

    }

//showAllPopularMoviesTextView, showAllUpcomingMoviesTextView, showAllTopRatedMoviesTextView;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String MOVIES_CATEGORY = "";
        switch (id){
            case R.id.showAllNowShowingMoviesTextView: MOVIES_CATEGORY = Constants.MOVIES_NOW_SHOWING;
                break;

            case R.id.showAllPopularMoviesTextView: MOVIES_CATEGORY = Constants.MOVIES_POPULAR;
                break;

            case R.id.showAllUpcomingMoviesTextView: MOVIES_CATEGORY = Constants.MOVIES_UPCOMING;
                break;

            case R.id.showAllTopRatedMoviesTextView: MOVIES_CATEGORY = Constants.MOVIES_TOP_RATED;
                break;
        }
        Intent intent = new Intent(getActivity(),ShowAllActivity.class);
        intent.putExtra(Constants.KEY_MOVIES_CATEGORY,MOVIES_CATEGORY);
        startActivity(intent);
    }
}
