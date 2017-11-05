package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 04-11-2017.
 */

public class ReviewResponse {
    int page;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;
    ArrayList<Review> results;
}
