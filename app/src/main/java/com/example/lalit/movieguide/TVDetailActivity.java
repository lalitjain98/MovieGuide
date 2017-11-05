package com.example.lalit.movieguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TVDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    int TV_id;
    String genreListString, firstAirDate, releaseYear;
    ImageView TVDetailBackdropImageView,
            TVDetailPosterImageView;
    TextView TVDetailGenresView,
            TVDetailReleaseYearView,
            TVDetailOverviewView,
            TVDetailFirstAirDateView,
            TVDetailRuntimeView;
//    TextView TVDetailTitleView;

    RecyclerView TVDetailVideosListRecyclerView,
            TVDetailCastListRecyclerView,
            TVDetailSimilarTVShowsListRecyclerView;

    TVVideoRecyclerViewAdapter videosAdapter;
    TVCastRecyclerViewAdapter castAdapter;
    TVAdapter similarTVShowsAdapter ;

    ArrayList<TVDetailVideosResponse.TVVideo> videoList;
    ArrayList<TVDetailCastResponse.TVCast> castList;
    ArrayList<TV> similarTVsList;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);
        toolbar = (Toolbar) findViewById(R.id.TVDetailToolbar);
        setSupportActionBar(toolbar);
        TV_id = getIntent().getIntExtra(Constants.KEY_TV_ID,-1);

        if(TV_id == -1){
            return;
        }

        TVDetailBackdropImageView = findViewById(R.id.TVDetailBackdropImageView);
        //TVDetailPosterImageView = findViewById(R.id.TVDetailPosterImageView);

        //TVDetailTitleView = findViewById(R.id.TVDetailTitleView);
        TVDetailGenresView = findViewById(R.id.TVDetailGenresView);
        TVDetailReleaseYearView = findViewById(R.id.TVDetailReleaseYearView);
        TVDetailOverviewView = findViewById(R.id.TVDetailOverviewView);
        TVDetailFirstAirDateView = findViewById(R.id.TVDetailFirstAirDateView);
        TVDetailRuntimeView = findViewById(R.id.TVDetailRuntimeView);

        videoList = new ArrayList<>();
        TVDetailVideosListRecyclerView = findViewById(R.id.TVDetailVideosListRecyclerView);
        videosAdapter = new TVVideoRecyclerViewAdapter(getApplicationContext(), videoList, new TVVideoRecyclerViewAdapter.VideoClickedListener() {
            @Override
            public void onVideoClicked(int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=" + videoList.get(position).key));
                startActivity(intent);
            }
        });
        TVDetailVideosListRecyclerView.setAdapter(videosAdapter);
        TVDetailVideosListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        castList = new ArrayList<>();
        TVDetailCastListRecyclerView = findViewById(R.id.TVDetailCastListRecyclerView);
        castAdapter = new TVCastRecyclerViewAdapter(this, castList, new TVCastRecyclerViewAdapter.CastClickedListener() {
            @Override
            public void onCastClicked(int position) {
                Toast.makeText(TVDetailActivity.this,"Cast Clicked",Toast.LENGTH_SHORT).show();
            }
        });
        TVDetailCastListRecyclerView.setAdapter(castAdapter);
        TVDetailCastListRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        similarTVsList = new ArrayList<>();
        TVDetailSimilarTVShowsListRecyclerView = findViewById(R.id.TVDetailSimilarTVShowsListRecyclerView);
        similarTVShowsAdapter = new TVAdapter(this, similarTVsList, Constants.KEY_POSTER_LARGE, new TVAdapter.TVClickListener() {
            @Override
            public void onTVClicked(int position) {
                Toast.makeText(TVDetailActivity.this,"Similar TV Clicked",Toast.LENGTH_SHORT).show();
                TV_id = similarTVsList.get(position).id;
                Intent intent = new Intent(TVDetailActivity.this,TVDetailActivity.class);
                intent.putExtra(Constants.KEY_TV_ID,TV_id);
                startActivity(intent);
            }

            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {

                FavouritesDatabase database = FavouritesDatabase.getInstance(getApplicationContext());
                TV tv = similarTVsList.get(position);
                if(isClicked)
                    database.getTVDao().deleteTV(tv);
                else
                    database.getTVDao().addTV(tv);
                return true;
            }
        });
        TVDetailSimilarTVShowsListRecyclerView.setAdapter(similarTVShowsAdapter);
        TVDetailSimilarTVShowsListRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/tv/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TVService tvService = retrofit.create(TVService.class);

        Call<TVDetailResponse> TVDetailResponseCall = tvService.getTVDetail(TV_id,Constants.API_KEY);
        TVDetailResponseCall.enqueue(new Callback<TVDetailResponse>() {
            @Override
            public void onResponse(Call<TVDetailResponse> call, Response<TVDetailResponse> response) {
                LoadData(response);
                Log.d("TV ID ", String.valueOf(response.body().id));
            }

            @Override
            public void onFailure(Call<TVDetailResponse> call, Throwable t) {
                Log.d("Cast List ", "Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        Call<TVDetailVideosResponse> TVDetailVideosResponseCall = tvService.getTVVideos(TV_id,Constants.API_KEY);

        TVDetailVideosResponseCall.enqueue(new Callback<TVDetailVideosResponse>() {
            @Override
            public void onResponse(Call<TVDetailVideosResponse> call, Response<TVDetailVideosResponse> response) {
                videoList.addAll(response.body().results);
                Log.d("Video", response.body().toString());
                Log.d("Video Count", String.valueOf(videoList.size()));
                videosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TVDetailVideosResponse> call, Throwable t) {
                Log.d("Videos List ", "Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        Call<TVDetailCastResponse> TVDetailCastResponseCall = tvService.getTVCast(TV_id, Constants.API_KEY);
        TVDetailCastResponseCall.enqueue(new Callback<TVDetailCastResponse>() {
            @Override
            public void onResponse(Call<TVDetailCastResponse> call, Response<TVDetailCastResponse> response) {
                String list = "";
                for(int i = 0;i< response.body().castList.size();i++){
                    list += response.body().castList.get(i).profilePath;
                }
                Log.d("Video",list);
                if(response.body()==null){
                    Log.d("TV Cast", "Failed");
                    return;
                }
                castList.addAll(response.body().castList);
                castAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TVDetailCastResponse> call, Throwable t) {
                Log.d("Cast List ", "Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        Call<TVResponse> similarTVsResponseCall = tvService.getSimilarTVShows(TV_id, Constants.API_KEY);
        similarTVsResponseCall.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                if(response.body()==null){
                    Log.d("TV Similar Shows", "Failed");
                    return;
                }
                similarTVsList.addAll(response.body().results);
                similarTVShowsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {

            }
        });

    }

    private void LoadData(Response<TVDetailResponse> response) {
        TVDetailResponse tvDetailResponse = response.body();

        //ArrayList<TVDetailResponse.Genre> genreArrayList = new ArrayList<>();

        genreListString = "";
        if(tvDetailResponse == null){
            Log.d("TV Detail", "Failed");
            return;
        }
            for(int i=0;i<tvDetailResponse.genresList.size();i++){
            genreListString += tvDetailResponse.genresList.get(i).genreName;
            if(i<tvDetailResponse.genresList.size()-1){
                genreListString += ", ";
            }
        }
        Log.d("GenreList",genreListString);
        //
        //final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        //AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        //appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
        //boolean isShow = false;
        //int scrollRange = -1;
        //
        //@Override
        //public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //if (scrollRange == -1) {
        //scrollRange = appBarLayout.getTotalScrollRange();
        //}
        //if (scrollRange + verticalOffset == 0) {
        //collapsingToolbarLayout.setTitle("Title");
        //isShow = true;
        //} else if(isShow) {
        //collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
        //isShow = false;
        //}
        //}
        //});
        //
        toolbar.setTitle(" ");
        toolbar.setTitle(tvDetailResponse.title);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        toolbar.setCollapsible(true);
        firstAirDate = "";
        firstAirDate += tvDetailResponse.firstAirDate;
        if(firstAirDate.length()!=0)
            releaseYear = (firstAirDate.split("-"))[0];
        else
            releaseYear = "";
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w1280/" + tvDetailResponse.backdropPath).error(R.drawable.placeholder_backdrop).placeholder(R.drawable.placeholder_backdrop).into(TVDetailBackdropImageView);
        //Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w342/" + tvDetailResponse.posterPath).into(TVDetailPosterImageView);
        TVDetailGenresView.setText(genreListString);
        //TVDetailTitleView.setText(tvDetailResponse.title);
        TVDetailReleaseYearView.setText(releaseYear);
        TVDetailFirstAirDateView.setText("Release Date: "+ firstAirDate);
        TVDetailRuntimeView.setText("Runtime: " + tvDetailResponse.runtime + " minutes");
        TVDetailOverviewView.setText(tvDetailResponse.overview);
        //VideosAdapter.notifyDataSetChanged();
        Log.d("Video Count", String.valueOf(videoList.size()));
    }


}
