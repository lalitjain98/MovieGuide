package com.example.lalit.movieguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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

public class MovieDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    int movie_id;
    String genreListString, releaseDate, releaseYear;
    ImageView movieDetailBackdropImageView,
            movieDetailPosterImageView;
    TextView movieDetailGenresView,
            movieDetailReleaseYearView,
            movieDetailOverviewView,
            movieDetailReleaseDateView,
            movieDetailRuntimeView;
//    TextView movieDetailTitleView;
    RecyclerView movieDetailTrailersListRecyclerView,
            movieDetailCastListRecyclerView,
            movieDetailSimilarMoviesListRecyclerView,
            movieDetailReviewsListRecyclerView;


    MovieTrailerRecyclerViewAdapter trailersAdapter;
    MovieCastRecyclerViewAdapter castAdapter;
    SmallImageRecyclerViewAdapter similarMoviesAdapter;
    MoviesDetailReviewsAdapter reviewsAdapter;

    ArrayList<MovieDetailTrailersResponse.MovieTrailer> trailerList;
    ArrayList<MovieDetailCastResponse.MovieCast> castList;
    ArrayList<Movie> similarMoviesList;
    MovieDetailResponse movieDetailResponse;
    ArrayList<Review> reviews;
    ReviewResponse reviewResponse;
    AppBarLayout movieDetailAppBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        toolbar = (Toolbar) findViewById(R.id.movieDetailToolbar);

        movieDetailAppBar = findViewById(R.id.movieDetailAppBar);

        setSupportActionBar(toolbar);
        movie_id = getIntent().getIntExtra(Constants.KEY_MOVIE_ID,-1);

        if(movie_id == -1){
            return;
        }


        movieDetailBackdropImageView = findViewById(R.id.movieDetailBackdropImageView);
        //movieDetailPosterImageView = findViewById(R.id.movieDetailPosterImageView);

        //movieDetailTitleView = findViewById(R.id.movieDetailTitleView);
        movieDetailGenresView = findViewById(R.id.movieDetailGenresView);
        movieDetailReleaseYearView = findViewById(R.id.movieDetailReleaseYearView);
        movieDetailOverviewView = findViewById(R.id.movieDetailOverviewView);
        movieDetailReleaseDateView = findViewById(R.id.movieDetailReleaseDateView);
        movieDetailRuntimeView = findViewById(R.id.movieDetailRuntimeView);

        trailerList = new ArrayList<>();
        movieDetailTrailersListRecyclerView = findViewById(R.id.movieDetailTrailersListRecyclerView);
        trailersAdapter = new MovieTrailerRecyclerViewAdapter(getApplicationContext(), trailerList, new MovieTrailerRecyclerViewAdapter.TrailerClickedListener() {
            @Override
            public void onTrailerClicked(int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=" + trailerList.get(position).key));
                startActivity(intent);
            }
        });
        movieDetailTrailersListRecyclerView.setAdapter(trailersAdapter);
        movieDetailTrailersListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        castList = new ArrayList<>();
        movieDetailCastListRecyclerView = findViewById(R.id.movieDetailCastListRecyclerView);
        castAdapter = new MovieCastRecyclerViewAdapter(this, castList, new MovieCastRecyclerViewAdapter.CastClickedListener() {
            @Override
            public void onCastClicked(int position) {
                Toast.makeText(MovieDetailActivity.this,"Cast Clicked",Toast.LENGTH_SHORT).show();
            }
        });
        movieDetailCastListRecyclerView.setAdapter(castAdapter);
        movieDetailCastListRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        similarMoviesList = new ArrayList<>();
        movieDetailSimilarMoviesListRecyclerView = findViewById(R.id.movieDetailSimilarMoviesListRecyclerView);
        similarMoviesAdapter = new SmallImageRecyclerViewAdapter(this, similarMoviesList, Constants.KEY_POSTER_LARGE, new SmallImageRecyclerViewAdapter.SmallImageMovieClickListener() {
            @Override
            public void onMovieClicked(int position) {
//                Toast.makeText(MovieDetailActivity.this,"Similar Movie Clicked",Toast.LENGTH_SHORT).show();
                movie_id = similarMoviesList.get(position).id;
                Intent intent = new Intent(MovieDetailActivity.this,MovieDetailActivity.class);
                intent.putExtra(Constants.KEY_MOVIE_ID,movie_id);
                startActivity(intent);
            }
            @Override
            public Boolean onFavouriteClicked(int position, Boolean isClicked) {

                FavouritesDatabase database = FavouritesDatabase.getInstance(getApplicationContext());
                Movie movie = similarMoviesList.get(position);
                if(isClicked)
                    database.getMovieDao().deleteMovie(movie);
                else
                    database.getMovieDao().addMovie(movie);
                return true;
            }
        });
        movieDetailSimilarMoviesListRecyclerView.setAdapter(similarMoviesAdapter);
        movieDetailSimilarMoviesListRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        reviews = new ArrayList<>();
        movieDetailReviewsListRecyclerView = findViewById(R.id.movieDetailReviewsListRecyclerView);
        reviewsAdapter = new MoviesDetailReviewsAdapter(this, reviews, new MoviesDetailReviewsAdapter.ReviewClickListener() {
            @Override
            public void onReviewClicked(int position) {
                Intent review = new Intent();
                review.setData(Uri.parse(reviews.get(position).url));
                startActivity(review);
            }
        });
        movieDetailReviewsListRecyclerView.setAdapter(reviewsAdapter);
        movieDetailReviewsListRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MoviesService moviesService = retrofit.create(MoviesService.class);

        Call<MovieDetailResponse> movieDetailResponseCall = moviesService.getMovieDetail(movie_id,Constants.API_KEY);
        movieDetailResponseCall.enqueue(new Callback<MovieDetailResponse>() {
            @Override
            public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                LoadData(response);
                Log.d("Movie ID ", String.valueOf(response.body().id));
            }

            @Override
            public void onFailure(Call<MovieDetailResponse> call, Throwable t) {
                Log.d("Cast List ", "Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        Call<MovieDetailTrailersResponse> movieDetailTrailersResponseCall = moviesService.getMovieTrailers(movie_id, Constants.API_KEY);
        movieDetailTrailersResponseCall.enqueue(new Callback<MovieDetailTrailersResponse>() {
            @Override
            public void onResponse(Call<MovieDetailTrailersResponse> call, Response<MovieDetailTrailersResponse> response) {
                trailerList.addAll(response.body().results);
                Log.d("Trailer", response.body().toString());
                Log.d("Trailer Count", String.valueOf(trailerList.size()));
                trailersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieDetailTrailersResponse> call, Throwable t) {
                Log.d("Trailers List ", "Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        Call<MovieDetailCastResponse> movieDetailCastResponseCall = moviesService.getMovieCast(movie_id, Constants.API_KEY);
        movieDetailCastResponseCall.enqueue(new Callback<MovieDetailCastResponse>() {
            @Override
            public void onResponse(Call<MovieDetailCastResponse> call, Response<MovieDetailCastResponse> response) {
//                String list = "";
//                for(int i = 0;i< response.body().castList.size();i++){
//                    list += response.body().castList.get(i).profilePath;
//                }
//                Log.d("Trailer",list);
                castList.addAll(response.body().castList);
                castAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieDetailCastResponse> call, Throwable t) {
                Log.d("Cast List ", "Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        Call<MoviesResponse> similarMoviesResponseCall = moviesService.getSimilarMovies(movie_id, Constants.API_KEY);
        similarMoviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                similarMoviesList.addAll(response.body().results);
                similarMoviesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });

        Call<ReviewResponse> reviewResponseCall = moviesService.getMovieReviews(movie_id,Constants.API_KEY);
        reviewResponseCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                reviewResponse = response.body();
                reviews.addAll(reviewResponse.results);
                reviewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("Reviews","Failed");
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

    }

    private void LoadData(Response<MovieDetailResponse> response) {
        movieDetailResponse = response.body();
        //ArrayList<MovieDetailResponse.Genre> genreArrayList = new ArrayList<>();
        genreListString = "";
        for(int i=0;i<movieDetailResponse.genresList.size();i++){
            genreListString += movieDetailResponse.genresList.get(i).genreName.toString();
            if(i<movieDetailResponse.genresList.size()-1){
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
        toolbar.setTitle(movieDetailResponse.title);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        toolbar.setCollapsible(false);
        releaseDate = movieDetailResponse.releaseDate;
        releaseYear= (releaseDate.split("-"))[0];
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w1280/" + movieDetailResponse.backdropPath).error(R.drawable.placeholder_backdrop).placeholder(R.drawable.placeholder_backdrop).into(movieDetailBackdropImageView);
        //Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w342/" + movieDetailResponse.posterPath).into(movieDetailPosterImageView);
        movieDetailGenresView.setText(genreListString);
        //movieDetailTitleView.setText(movieDetailResponse.title);
        movieDetailReleaseYearView.setText(releaseYear);
        movieDetailReleaseDateView.setText("Release Date: "+ releaseDate);
        movieDetailRuntimeView.setText("Runtime: " + movieDetailResponse.runtime + " minutes");
        movieDetailOverviewView.setText(movieDetailResponse.overview);
        //trailersAdapter.notifyDataSetChanged();
        Log.d("Trailer Count", String.valueOf(trailerList.size()));
    }
}
