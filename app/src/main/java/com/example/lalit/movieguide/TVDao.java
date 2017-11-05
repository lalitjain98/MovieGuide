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
interface TVDao {
    @Query("SELECT * FROM TV")
    List<TV> getTV();
    @Query("SELECT * FROM TV WHERE id = :id ")
    TV getTV(int id);
    @Insert
    void addTV(TV expense);
    @Update
    void updateTV(TV expense);
    @Delete
    void deleteTV(TV expense);
}
