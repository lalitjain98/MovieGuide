package com.example.lalit.movieguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ShowAllTV extends AppCompatActivity {
    ProgressBar showAllTVActivityProgressBar, showAllTVActivityBottomProgressBar;
    RecyclerView showAllTVActivityRecyclerView;
    ArrayList<TV> TVArrayList;
    TVAdapter TVAdapter;
    GridLayoutManager gridLayoutManager;
    public static String TV_CATEGORY;
    int currentPage,lastLoadedPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_tv);

        TV_CATEGORY = getIntent().getStringExtra(Constants.KEY_TV_CATEGORY);

        currentPage = 1;
        lastLoadedPage = 0;

        showAllTVActivityProgressBar = findViewById(R.id.showAllTVActivityProgressBar);
        showAllTVActivityBottomProgressBar = findViewById(R.id.showAllTVActivityBottomProgressBar);
        showAllTVActivityBottomProgressBar.setVisibility(View.GONE);
        showAllTVActivityRecyclerView = findViewById(R.id.showAllTVActivityRecyclerView);
        TVArrayList = new ArrayList<>();
        TVAdapter = new TVAdapter(this, TVArrayList, Constants.KEY_POSTER_SMALL, new TVAdapter.TVClickListener() {
            @Override
            public void onTVClicked(int position) {
                showDetails(TVArrayList,position);
            }

            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {
                FavouritesDatabase database = FavouritesDatabase.getInstance(getApplicationContext());
                TV tv = TVArrayList.get(position);
                if(isClicked)
                    database.getTVDao().deleteTV(tv);
                else
                    database.getTVDao().addTV(tv);                return true;
            }
        });
        showAllTVActivityRecyclerView.setAdapter(TVAdapter);
        gridLayoutManager = new GridLayoutManager(this,3, LinearLayoutManager.VERTICAL,false);
        showAllTVActivityRecyclerView.setLayoutManager(gridLayoutManager);
        //showAllActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        showAllTVActivityRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.computeVerticalScrollOffset()>0 ){
                    int lastItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                    if(lastItemPosition == TVArrayList.size()-1){
                        showAllTVActivityBottomProgressBar.setVisibility(View.VISIBLE);
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
        LoadMovies();
    }

    private void LoadMovies() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/tv/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TVService tvService = retrofit.create(TVService.class);
        Call<TVResponse> tvResponseCall = null;
        switch(TV_CATEGORY) {
            case Constants.TV_AIRING_TODY : tvResponseCall = tvService.getAiringTodayTV(Constants.API_KEY,String.valueOf(currentPage));
                break;
            case Constants.TV_ON_THE_AIR : tvResponseCall = tvService.getOnTheAirTV(Constants.API_KEY,String.valueOf(currentPage));
                break;
            case Constants.TV_POPULAR : tvResponseCall = tvService.getPopularTV(Constants.API_KEY,String.valueOf(currentPage));
                break;
            case Constants.TV_TOP_RATED : tvResponseCall = tvService.getTopRatedTV(Constants.API_KEY,String.valueOf(currentPage));
                break;
        }

        if(tvResponseCall != null){
            tvResponseCall.enqueue(new Callback<TVResponse>() {
                @Override
                public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                    lastLoadedPage = currentPage;
                    currentPage++;
                    TVArrayList.addAll(response.body().results);
                    showAllTVActivityBottomProgressBar.setVisibility(View.GONE);
                    showAllTVActivityProgressBar.setVisibility(View.GONE);
                    TVAdapter.notifyDataSetChanged();
                    showAllTVActivityRecyclerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<TVResponse> call, Throwable t) {
                    Log.d("Show All TV", "Failed");
                    try {
                        throw t;
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            });
        }
    }

    private void showDetails(ArrayList<TV> tvList, int position) {
        int id = tvList.get(position).id;
        Intent intent = new Intent(this,TVDetailActivity.class);
        intent.putExtra(Constants.KEY_TV_ID,id);
        startActivity(intent);
    }

}
