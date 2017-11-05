package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 29-10-2017.
 */
/*// 20171028223151
// https://api.themoviedb.org/3/movie/346364/videos?api_key=e41d9ad2341d47013d80820d17700acf&language=en-US

{
  "id": 346364,
  "results": [
    {
      "id": "58f2bf699251413d95006445",
      "iso_639_1": "en",
      "iso_3166_1": "US",
      "key": "FnCdOQsX5kc",
      "name": "Official Teaser Trailer",
      "site": "YouTube",
      "size": 1080,
      "type": "Teaser"
    },
    {
      "id": "597a18aac3a368688e01950b",
      "iso_639_1": "en",
      "iso_3166_1": "US",
      "key": "xKJmEC5ieOk",
      "name": "Official Trailer 1",
      "site": "YouTube",
      "size": 1080,
      "type": "Trailer"
    }
  ]
}*/
public class MovieDetailTrailersResponse {
    @SerializedName("id")
    String movie_id;
    @SerializedName("results")
    ArrayList<MovieTrailer> results;
    class MovieTrailer {
        @SerializedName("id")
        String trailer_id;
        String key, name;
    }
}
