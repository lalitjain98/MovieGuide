package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 02-11-2017.
 */

public class TVResponse {
    int page;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;
    ArrayList<TV> results;
}
