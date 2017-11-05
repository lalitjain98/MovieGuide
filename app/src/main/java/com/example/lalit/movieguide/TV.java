package com.example.lalit.movieguide;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jainl on 02-11-2017.
 */
/*
      "original_name": "Mindhunter",
      "genre_ids": [
        80,
        18
      ],
      "name": "Mindhunter",
      "popularity": 280.093513,
      "origin_country": [
        "US"
      ],
      "vote_count": 37,
      "first_air_date": "2017-10-13",
      "backdrop_path": "/63tvg7N3o1bodWb2Vjzo9jxbPWc.jpg",
      "original_language": "en",
      "id": 67744,
      "vote_average": 7.9,
      "overview": "An agent in the FBI's Elite Serial Crime Unit develops profiling techniques as he pursues notorious serial killers and rapists.",
      "poster_path": "/r7RIwuceOaDP4KTmU1EFeDniRq4.jpg"
    }*/
@Entity
public class TV {
    @PrimaryKey
    int id;
    @SerializedName("vote_count")
    int voteCount;
    @SerializedName("vote_average")
    double voteAverage;
    double popularity;
    @SerializedName("name")
    String title;
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("backdrop_path")
    String backdropPath;
    String overview;
    @SerializedName("first_air_date")
    String firstAirDate;
}
