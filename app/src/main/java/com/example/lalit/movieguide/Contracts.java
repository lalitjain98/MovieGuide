package com.example.lalit.movieguide;

import android.provider.BaseColumns;

/**
 * Created by jainl on 04-11-2017.
 */

class Contracts {

    public static final String DATABASE_NAME = "movie_guide_db";
    public class Movie implements BaseColumns {
        public static final String TABLE_NAME = "movie_table";

    }
    public class TV implements BaseColumns{
        public static final String TABLE_NAME = "tv_table";

    }
}
