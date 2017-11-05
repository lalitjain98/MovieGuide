package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 29-10-2017.
 */
/*"id": 346364,
  "cast": [
    {
      "cast_id": 5,
      "character": "Bill Denbrough",
      "credit_id": "57739eda925141251d00058d",
      "gender": 2,
      "id": 1274508,
      "name": "Jaeden Lieberher",
      "order": 0,
      "profile_path": "/5iGyfDlrYsQwEeGz8rZibBcxbus.jpg"
    },*/
public class MovieDetailCastResponse {
    @SerializedName("id")
    String movie_id;
    @SerializedName("cast")
    ArrayList<MovieCast> castList;
    class MovieCast {
        @SerializedName("cast_id")
        String trailer_id;
        String character, name;
        @SerializedName("profile_path")
        String profilePath;
    }
}
