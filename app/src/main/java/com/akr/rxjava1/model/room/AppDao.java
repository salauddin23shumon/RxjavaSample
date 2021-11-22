package com.akr.rxjava1.model.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.akr.rxjava1.model.City;
import com.akr.rxjava1.model.District;
import com.akr.rxjava1.model.Division;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AppDao {

//    @Insert(onConflict = REPLACE)
//    void insert(City city);

    @Query("SELECT * FROM city_tbl ")
    LiveData<List<City>> getAllData();


    @Insert
    Completable insert(City city);

    @Insert
    void insert(List<City> city);

    @Insert
    void insertDistrict(List<District> city);

    @Insert
    Maybe<Long> insert(District district);

    @Insert
    void insertCities(List<City> cities);

    @Insert
    void insertDivision(List<Division> divisions);

    @Query("SELECT * FROM DIVISION_TBL ORDER BY id DESC")
    Flowable<List<Division>> getAllDivision();

    @Query("SELECT * FROM DIVISION_TBL ORDER BY id DESC")
    Maybe<List<Division>> getAllDivision2();

    @Query("SELECT * FROM DISTRICT_TBL ORDER BY id DESC")
    Flowable<List<District>> getAllDistrict();

    @Query("SELECT EXISTS(SELECT * FROM DIVISION_TBL)")
    boolean ifDbExist();
}
