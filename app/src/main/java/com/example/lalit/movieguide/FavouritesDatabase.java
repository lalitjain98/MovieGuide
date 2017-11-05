package com.example.lalit.movieguide;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by jainl on 04-11-2017.
 */
@Database(entities = {Movie.class,TV.class}, version = 1)
public abstract class FavouritesDatabase extends RoomDatabase {
    public static FavouritesDatabase database;

    public static FavouritesDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context,FavouritesDatabase.class,Contracts.DATABASE_NAME).allowMainThreadQueries().build();
        }
        return database;
    }
    public abstract MovieDao getMovieDao();
    public abstract TVDao getTVDao();
}
