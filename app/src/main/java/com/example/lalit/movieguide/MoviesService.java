package com.example.lalit.movieguide;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jainl on 23-10-2017.
 */

public interface MoviesService {
    @GET("popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String api_key);

    @GET("popular")
    Call<MoviesResponse> getPopularMovies(@Query("page") String page, @Query("api_key") String api_key);

    @GET("{movie_id}")
    Call<MovieDetailResponse> getMovieDetail(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("now_playing?page=1")
    Call<MoviesResponse> getNowShowingMovies(@Query("api_key") String api_key);

    @GET("now_playing")
    Call<MoviesResponse> getNowShowingMovies(@Query("page") String page, @Query("api_key") String api_key);

    @GET("top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("page") String page, @Query("api_key") String api_key);

    @GET("upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String api_key);

    @GET("upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("page") String page, @Query("api_key") String api_key);

    @GET("{movie_id}/videos")
    Call<MovieDetailTrailersResponse> getMovieTrailers(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("{movie_id}/credits")
    Call<MovieDetailCastResponse> getMovieCast(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("{movie_id}/similar")
    Call<MoviesResponse> getSimilarMovies(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("{movie_id}/reviews")
    Call<ReviewResponse> getMovieReviews(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
}
