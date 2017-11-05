package com.example.lalit.movieguide;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jainl on 23-10-2017.
 */
@Entity
public class Movie {
    /*
      "vote_count": 2496,
      "id": 346364,
      "video": false,
      "vote_average": 7.4,
      "title": "It",
      "popularity": 902.275255,
      "poster_path": "/9E2y5Q7WlCVNEhP5GiVTjhEhx1o.jpg",
      "original_language": "en",
      "original_title": "It",
      "genre_ids": [
        12,
        18,
        27
      ],
      "backdrop_path": "/tcheoA2nPATCm2vvXw2hVQoaEFD.jpg",
      "adult": false,
      "overview": "In a small town in Maine, seven children known as The Losers Club come face to face with life problems, bullies and a monster that takes the shape of a clown called Pennywise.",
      "release_date": "2017-09-05"
      */
    @PrimaryKey
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
}
