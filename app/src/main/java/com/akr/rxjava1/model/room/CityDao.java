package com.akr.rxjava1.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.akr.rxjava1.model.City;

import java.util.List;

import io.reactivex.Completable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CityDao {

//    @Insert(onConflict = REPLACE)
//    void insert(City city);

    @Query("SELECT * FROM city_tbl ")
    LiveData<List<City>> getAllData();


    @Insert
    Completable insert(City city);

}
