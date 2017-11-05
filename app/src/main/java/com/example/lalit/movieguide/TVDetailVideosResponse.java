package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 02-11-2017.
 */

public class TVDetailVideosResponse {
    @SerializedName("id")
    String tv_id;
    @SerializedName("results")
    ArrayList<TVVideo> results;
    class TVVideo {
        @SerializedName("id")
        String trailer_id;
        String key, name;
    }
}
