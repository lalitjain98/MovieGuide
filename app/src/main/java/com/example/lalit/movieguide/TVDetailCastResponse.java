package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 02-11-2017.
 */

public class TVDetailCastResponse {

    @SerializedName("id")
    String tv_id;
    @SerializedName("cast")
    ArrayList<TVCast> castList;
    class TVCast {
        @SerializedName("cast_id")
        String id;
        String character, name;
        @SerializedName("profile_path")
        String profilePath;
    }
}
