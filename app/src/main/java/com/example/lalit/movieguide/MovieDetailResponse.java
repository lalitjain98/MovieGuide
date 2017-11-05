package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 28-10-2017.
 */

public class MovieDetailResponse {
    /*
      {
  "adult": false,
  "backdrop_path": "/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg",
  "belongs_to_collection": null,
  "budget": 35000000,
  "genres": [
    {
      "id": 53,
      "name": "Thriller"
    },
    {
      "id": 27,
      "name": "Horror"
    },
    {
      "id": 18,
      "name": "Drama"
    }
  ],
  "homepage": "http://itthemovie.com/",
  "id": 346364,
  "imdb_id": "tt1396484",
  "original_language": "en",
  "original_title": "It",
  "overview": "In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.",
  "popularity": 782.749289,
  "poster_path": "/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg",
  "production_companies": [
    {
      "name": "New Line Cinema",
      "id": 12
    },
    {
      "name": "Vertigo Entertainment",
      "id": 829
    },
    {
      "name": "Lin Pictures",
      "id": 2723
    },
    {
      "name": "RatPac-Dune Entertainment",
      "id": 41624
    },
    {
      "name": "KatzSmith Productions",
      "id": 87671
    }
  ],
  "production_countries": [
    {
      "iso_3166_1": "US",
      "name": "United States of America"
    }
  ],
  "release_date": "2017-09-05",
  "revenue": 555575232,
  "runtime": 135,
  "spoken_languages": [
    {
      "iso_639_1": "en",
      "name": "English"
    },
    {
      "iso_639_1": "de",
      "name": "Deutsch"
    }
  ],
  "status": "Released",
  "tagline": "Your fears are unleashed",
  "title": "It",
  "video": false,
  "vote_average": 7.4,
  "vote_count": 2861
}
      */
    int id;
    @SerializedName("vote_count")
    int voteCount;
    @SerializedName("vote_average")
    double voteAverage;
    double popularity;
    String title;
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("backdrop_path")
    String backdropPath;
    String overview;
    @SerializedName("release_date")
    String releaseDate;

    @SerializedName("genres")
    ArrayList<Genre> genresList;
    public String runtime;

    public class Genre{
        @SerializedName("id")
        int genreId;
        @SerializedName("name")
        String genreName;
    }
}
