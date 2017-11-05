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
public class TVFragment extends Fragment implements View.OnClickListener {
    ArrayList<TV> onTheAirTVArrayList, airingTodayTVArrayList, popularTVArrayList, topRatedTVArrayList;
    RecyclerView onTheAirTvRecyclerView, airingTodayTVRecyclerView, popularTVRecyclerView, topRatedTVRecyclerView;
    TVAdapter onTheAirTVRecyclerViewAdapter, airingTodayTVRecyclerViewAdapter, popularTVRecyclerViewAdapter, topRatedTVRecyclerViewAdapter;
    TextView showAllOnTheAirTVTextView,showAllAiringTodayTVTextView,showAllPopularTVTextView,showAllTopRatedTVTextView;
    int itemsLoaded;
    ScrollView tvFragmentScrollView;
    ProgressBar tvFragmentProgressBar;
    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        itemsLoaded = 0;
        showAllOnTheAirTVTextView = view.findViewById(R.id.showAllOnTheAirTVTextView);
        showAllAiringTodayTVTextView = view.findViewById(R.id.showAllAiringTodayTVTextView);
        showAllPopularTVTextView = view.findViewById(R.id.showAllPopularTVTextView);
        showAllTopRatedTVTextView = view.findViewById(R.id.showAllTopRatedTVTextView);

        tvFragmentProgressBar = view.findViewById(R.id.tvFragmentProgressBar);
        tvFragmentProgressBar.setVisibility(View.VISIBLE);

        tvFragmentScrollView = view.findViewById(R.id.TVFragmentScrollView);
        tvFragmentScrollView.setVisibility(View.GONE);

        showAllOnTheAirTVTextView.setOnClickListener(this);
        showAllAiringTodayTVTextView.setOnClickListener(this);
        showAllPopularTVTextView.setOnClickListener(this);
        showAllTopRatedTVTextView.setOnClickListener(this);

        onTheAirTVArrayList = new ArrayList<>();
        airingTodayTVArrayList = new ArrayList<>();
        popularTVArrayList = new ArrayList<>();
        topRatedTVArrayList = new ArrayList<>();

        onTheAirTvRecyclerView = view.findViewById(R.id.onTheAirTVRecyclerView);
        airingTodayTVRecyclerView = view.findViewById(R.id.airingTodayTVRecyclerView);
        popularTVRecyclerView = view.findViewById(R.id.popularTVRecyclerView);
        topRatedTVRecyclerView = view.findViewById(R.id.topRatedTVRecyclerView);


        onTheAirTVRecyclerViewAdapter = getRecyclerView(onTheAirTvRecyclerView, onTheAirTVArrayList, Constants.KEY_POSTER_SMALL);
        airingTodayTVRecyclerViewAdapter = getRecyclerView(airingTodayTVRecyclerView, airingTodayTVArrayList, Constants.KEY_POSTER_LARGE);
        popularTVRecyclerViewAdapter = getRecyclerView(popularTVRecyclerView, popularTVArrayList, Constants.KEY_POSTER_LARGE);
        topRatedTVRecyclerViewAdapter = getRecyclerView(topRatedTVRecyclerView, topRatedTVArrayList, Constants.KEY_POSTER_SMALL);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.baseUrl("http://api.themoviedb.org/3/tv/");
        Retrofit retrofit = builder.build();
        TVService tvService = retrofit.create(TVService.class);

        Call<TVResponse> airingTodayTVResponseCall = tvService.getAiringTodayTV(Constants.API_KEY);
        LoadTVShows(airingTodayTVArrayList,airingTodayTVResponseCall, airingTodayTVRecyclerViewAdapter);

        Call<TVResponse> onTheAirTVResponseCall = tvService.getOnTheAirTV(Constants.API_KEY);
        LoadTVShows(onTheAirTVArrayList,onTheAirTVResponseCall, onTheAirTVRecyclerViewAdapter);

        Call<TVResponse> popularTVResponseCall = tvService.getPopularTV(Constants.API_KEY);
        LoadTVShows(popularTVArrayList,popularTVResponseCall, popularTVRecyclerViewAdapter);

        Call<TVResponse> topRatedTVResponseCall = tvService.getTopRatedTV(Constants.API_KEY);
        LoadTVShows(topRatedTVArrayList,topRatedTVResponseCall, topRatedTVRecyclerViewAdapter);

        return view;
    }

    private TVAdapter getRecyclerView(RecyclerView TVRecyclerView, final ArrayList<TV> TVArrayList, int keyPosterLarge) {
        TVAdapter adapter = new TVAdapter(getContext(), TVArrayList,keyPosterLarge, new TVAdapter.TVClickListener() {
            @Override
            public void onTVClicked(int position) {
                int tv_id = TVArrayList.get(position).id;
                Intent intent = new Intent(getActivity(),TVDetailActivity.class);
                intent.putExtra(Constants.KEY_TV_ID, tv_id);
                startActivity(intent);
            }

            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getContext());
                TV tv = TVArrayList.get(position);
                if(isClicked)
                    database.getTVDao().deleteTV(tv);
                else
                    database.getTVDao().addTV(tv);

                return true;
            }
        });
        TVRecyclerView.setAdapter(adapter);
        TVRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        return adapter;
    }

    private void LoadTVShows(final ArrayList<TV> TVArrayList, Call<TVResponse> tvResponseCall, final TVAdapter adapter) {

        tvResponseCall.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                TVArrayList.addAll(response.body().results);
                adapter.notifyDataSetChanged();
                itemsLoaded++;
                checkItemsLoaded();
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.d("TV adapter", "Failed");
            }
        });
    }

    private void checkItemsLoaded() {
        if(itemsLoaded==4){
            tvFragmentScrollView.setVisibility(View.VISIBLE);
            tvFragmentProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        String TV_CATEGORY = "";
        switch (id){
            case R.id.showAllOnTheAirTVTextView: TV_CATEGORY = Constants.TV_ON_THE_AIR;
                break;

            case R.id.showAllAiringTodayTVTextView: TV_CATEGORY = Constants.TV_AIRING_TODY;
                break;

            case R.id.showAllPopularTVTextView: TV_CATEGORY = Constants.TV_POPULAR;
                break;

            case R.id.showAllTopRatedMoviesTextView: TV_CATEGORY = Constants.TV_TOP_RATED;
                break;
        }
        Intent intent = new Intent(getActivity(),ShowAllTV.class);
        intent.putExtra(Constants.KEY_TV_CATEGORY,TV_CATEGORY);
        startActivity(intent);
    }
}
