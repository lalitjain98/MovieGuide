package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 23-10-2017.
 */

public class MoviesResponse {
    int page;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;
    ArrayList<Movie> results;
}
