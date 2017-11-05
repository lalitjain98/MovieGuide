package com.example.lalit.movieguide;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jainl on 30-10-2017.
 */

public interface SearchService {
    @GET("movie")
    Call<MoviesResponse> getMoviesQuery(@Query("query") String query, @Query("api_key") String api_key);

    @GET("tv")
    Call<TVResponse> getTVQuery(@Query("query") String query, @Query("api_key") String api_key);
}
