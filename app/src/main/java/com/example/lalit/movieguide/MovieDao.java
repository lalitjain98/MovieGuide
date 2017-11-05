package com.example.lalit.movieguide;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by jainl on 04-11-2017.
 */
@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie" )
    List<Movie> getMovies();
    @Query("SELECT * FROM Movie WHERE id = :id ")
    Movie getMovie(int id);
    @Insert
    void addMovie(Movie movie);
    @Update
    void updateMovie(Movie movie);
    @Delete
    void deleteMovie(Movie movie);
}
