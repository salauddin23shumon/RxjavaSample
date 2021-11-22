package com.akr.rxjava1;


import com.akr.rxjava1.model.District;
import com.akr.rxjava1.model.City;
import com.akr.rxjava1.model.Division;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiInterface {



    @GET("district/all")
    Flowable<List<District>> getDistrict();

    @GET("upazilla/all")
    Call<List<City>> getUpazilla0();

    @GET("upazilla/all")
    Observable<Response<List<City>>> getCity1();

    @GET("upazilla/all")
    Flowable<List<City>> getCity();

    @GET("upazilla/all")
    Observable<List<City>> getCity2();

    @GET("upazilla/all")
    Flowable<Response<List<City>>> getUpazilla2();

    @GET("division/all")
    Flowable<List<Division>> getDivision();
}

