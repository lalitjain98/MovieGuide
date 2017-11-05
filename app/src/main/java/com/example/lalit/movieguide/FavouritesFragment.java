package com.example.lalit.movieguide;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment {


    public FavouritesFragment() {
        // Required empty public constructor
    }

    RecyclerView favouritesMoviesRecyclerView, favouritesTVRecyclerView;
    SmallImageRecyclerViewAdapter moviesAdapter;
    TVAdapter tvAdapter;
    ArrayList<Movie> moviesArrayList;
    ArrayList<TV> tvArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        moviesArrayList = new ArrayList<>();
        tvArrayList = new ArrayList<>();

        FavouritesDatabase database = FavouritesDatabase.getInstance(getContext());

        moviesArrayList.addAll(database.getMovieDao().getMovies());
        tvArrayList.addAll(database.getTVDao().getTV());

        favouritesMoviesRecyclerView = view.findViewById(R.id.favouritesMoviesRecyclerView);
        favouritesTVRecyclerView = view.findViewById(R.id.favouritesTVRecyclerView);

        moviesAdapter = new SmallImageRecyclerViewAdapter(getContext(), moviesArrayList, Constants.KEY_POSTER_SMALL, new SmallImageRecyclerViewAdapter.SmallImageMovieClickListener() {
            @Override
            public void onMovieClicked(int position) {
                int id = moviesArrayList.get(position).id;
                Intent intent = new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.KEY_MOVIE_ID,id);
                intent.putExtra(Constants.INTENT_TYPE, Constants.INTENT_FAVOURITES);
                startActivity(intent);
            }

            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getContext());
                Movie movie = moviesArrayList.get(position);
                if(isClicked)
                    database.getMovieDao().deleteMovie(movie);
                else
                    database.getMovieDao().addMovie(movie);
                return true;
            }
        });
        favouritesMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //favouritesMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.HORIZONTAL,false));
        favouritesMoviesRecyclerView.setAdapter(moviesAdapter);
        tvAdapter = new TVAdapter(getContext(), tvArrayList, Constants.KEY_POSTER_SMALL, new TVAdapter.TVClickListener() {
            @Override
            public void onTVClicked(int position) {
                int tv_id = tvArrayList.get(position).id;
                Intent intent = new Intent(getActivity(),TVDetailActivity.class);
                intent.putExtra(Constants.KEY_TV_ID, tv_id);
                intent.putExtra(Constants.INTENT_TYPE, Constants.INTENT_FAVOURITES);
                startActivity(intent);
            }

            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getContext());
                TV tv = tvArrayList.get(position);
                if(isClicked)
                    database.getTVDao().deleteTV(tv);
                else
                    database.getTVDao().addTV(tv);
                return true;
            }
        });
        favouritesTVRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        //favouritesTVRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.HORIZONTAL,false));
        favouritesTVRecyclerView.setAdapter(tvAdapter);
        return view;
    }

}
