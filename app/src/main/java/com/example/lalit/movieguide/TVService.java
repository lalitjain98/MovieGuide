package com.example.lalit.movieguide;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jainl on 02-11-2017.
 */

public interface TVService {
    @GET("airing_today")
    Call<TVResponse> getAiringTodayTV(@Query("api_key") String api_key);

    @GET("airing_today")
    Call<TVResponse> getAiringTodayTV(@Query("api_key") String api_key, @Query("page") String page);

    @GET("on_the_air")
    Call<TVResponse> getOnTheAirTV(@Query("api_key") String api_key);

    @GET("on_the_air")
    Call<TVResponse> getOnTheAirTV(@Query("api_key") String api_key, @Query("page") String page);

    @GET("popular")
    Call<TVResponse> getPopularTV(@Query("api_key") String api_key);

    @GET("popular")
    Call<TVResponse> getPopularTV(@Query("api_key") String api_key, @Query("page") String page);

    @GET("top_rated")
    Call<TVResponse> getTopRatedTV(@Query("api_key") String api_key);

    @GET("top_rated")
    Call<TVResponse> getTopRatedTV(@Query("api_key") String api_key, @Query("page") String page);

    @GET("{tv_id}")
    Call<TVDetailResponse> getTVDetail(@Path("tv_id") int tv_id, @Query("api_key") String api_key);

    @GET("{tv_id}/videos")
    Call<TVDetailVideosResponse> getTVVideos(@Path("tv_id") int tv_id, @Query("api_key") String api_key);

    @GET("{tv_id}/credits")
    Call<TVDetailCastResponse> getTVCast(@Path("tv_id") int tv_id, @Query("api_key") String api_key);

    @GET("{tv_id}/similar")
    Call<TVResponse> getSimilarTVShows(@Path("tv_id") int tv_id, @Query("api_key") String api_key);
}
