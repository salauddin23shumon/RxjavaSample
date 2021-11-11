package com.akr.rxjava1;

import com.akr.rxjava1.model.City;

import java.util.List;

import io.reactivex.Observable;

public interface RxInterface {
    //Cloud loading
    Observable<List<City>> loadDataFromCloud();

    //Database loading
    Observable<Long> saveDataToDisk(List<City> cityList);
}
