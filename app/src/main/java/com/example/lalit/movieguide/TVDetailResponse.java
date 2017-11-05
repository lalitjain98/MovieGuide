package com.example.lalit.movieguide;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jainl on 02-11-2017.
 */

public class TVDetailResponse {
//    {
//  "backdrop_path": "/h1qyblc5p9G3ZWIVK8ZrkpxcXgO.jpg",
//  "created_by": [
//    {
//      "id": 4027,
//      "name": "Frank Darabont",
//      "gender": 2,
//      "profile_path": "/9KVvZtDyy8DXacw2TTsjC9VLxQi.jpg"
//    }
//  ],
//  "episode_run_time": [
//    42,
//    60,
//    45
//  ],
//  "first_air_date": "2010-10-31",
//  "genres": [
//    {
//      "id": 10765,
//      "name": "Sci-Fi & Fantasy"
//    },
//    {
//      "id": 10759,
//      "name": "Action & Adventure"
//    },
//    {
//      "id": 18,
//      "name": "Drama"
//    }
//  ],
//  "homepage": "http://www.amctv.com/shows/the-walking-dead/",
//  "id": 1402,
//  "in_production": true,
//  "languages": [
//    "en"
//  ],
//  "last_air_date": "2017-11-26",
//  "name": "The Walking Dead",
//  "networks": [
//    {
//      "id": 174,
//      "name": "AMC"
//    }
//  ],
//  "number_of_episodes": 141,
//  "number_of_seasons": 8,
//  "origin_country": [
//    "US"
//  ],
//  "original_language": "en",
//  "original_name": "The Walking Dead",
//  "overview": "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
//  "popularity": 237.844135,
//  "poster_path": "/vxuoMW6YBt6UsxvMfRNwRl9LtWS.jpg",
//  "production_companies": [
//    {
//      "name": "Darkwoods Productions",
//      "id": 3982
//    },
//    {
//      "name": "American Movie Classics (AMC)",
//      "id": 4854
//    },
//    {
//      "name": "Valhalla Motion Pictures",
//      "id": 11533
//    },
//    {
//      "name": "AMC Studios",
//      "id": 23242
//    },
//    {
//      "name": "Circle of Confusion",
//      "id": 23921
//    }
//  ],
//  "seasons": [
//    {
//      "air_date": "2010-10-11",
//      "episode_count": 36,
//      "id": 3646,
//      "poster_path": "/dJ8jOjIwgG6CybD4X5rTunO4cZ6.jpg",
//      "season_number": 0
//    },
//    {
//      "air_date": "2010-10-31",
//      "episode_count": 6,
//      "id": 3643,
//      "poster_path": "/yaOB2Y8GcoXwjNQ3apq67bMbNHF.jpg",
//      "season_number": 1
//    },
//    {
//      "air_date": "2011-10-16",
//      "episode_count": 13,
//      "id": 3644,
//      "poster_path": "/wpG9SDyz23t3vU8dTbtSvEkxv8r.jpg",
//      "season_number": 2
//    },
//    {
//      "air_date": "2012-10-14",
//      "episode_count": 16,
//      "id": 3645,
//      "poster_path": "/mDVPeQ5ZaaeO2Qh7VlXZchHHuLN.jpg",
//      "season_number": 3
//    },
//    {
//      "air_date": "2013-10-13",
//      "episode_count": 16,
//      "id": 3647,
//      "poster_path": "/pLXlKKQOoUZAjWsXwacZCj6SWIb.jpg",
//      "season_number": 4
//    },
//    {
//      "air_date": "2014-10-12",
//      "episode_count": 16,
//      "id": 60391,
//      "poster_path": "/oiQbg5WqdHqBWMrtbzJeS6vb2Lt.jpg",
//      "season_number": 5
//    },
//    {
//      "air_date": "2015-10-11",
//      "episode_count": 16,
//      "id": 68814,
//      "poster_path": "/kwVAeAA6TlZWr6lRP2yVTyR2aif.jpg",
//      "season_number": 6
//    },
//    {
//      "air_date": "2016-10-23",
//      "episode_count": 16,
//      "id": 76834,
//      "poster_path": "/nO2rbewsaek4J1M6sN3PsS5Sf2C.jpg",
//      "season_number": 7
//    },
//    {
//      "air_date": "2017-10-22",
//      "episode_count": 6,
//      "id": 91735,
//      "poster_path": "/wmv0oIun52Xeq65sBKfHiUkiBKc.jpg",
//      "season_number": 8
//    }
//  ],
//  "status": "Returning Series",
//  "type": "Scripted",
//  "vote_average": 7.4,
//  "vote_count": 2545
//}
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

    @SerializedName("genres")
    ArrayList<TVDetailResponse.Genre> genresList;
    @SerializedName("episode_run_time")
    ArrayList<Integer> runtime;

    public class Genre{
        @SerializedName("id")
        int genreId;
        @SerializedName("name")
        String genreName;
    }
}
