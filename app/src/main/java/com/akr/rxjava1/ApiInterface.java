package com.akr.rxjava1;


import com.akr.rxjava1.model.District;
import com.akr.rxjava1.model.Upazilla;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    /*
  Retrofit get annotation with our URL
  And our method that will return us the List of User List
  */

//    @FormUrlEncoded
//    @POST("mobileapi/insertUser.php")
//    Call<Register> registerUser(@FieldMap() Map<String, Object> value);

////    @FormUrlEncoded
//    @GET("Login/{userid}/{password}")
//    Call<LoginData> userLogin(@Path("userid")String userid,@Path("password")String password);
//
//
//    @GET("roundno/all")
//    Call<List<RoundModel>> getRound();
//
////    @GET("mobileapi/getServiceProviderList.php")
////    Call<ServiceProviderData> getServiceProviderList();
//
//    @GET("division/all")
//    Call<List<Datum>> getDivision();

    @GET("district/all")
    Call<List<District>> getDistrict();

    @GET("upazilla/all")
    Call<List<Upazilla>> getUpazilla0();

    @GET("upazilla/all")
    Observable<Response<List<Upazilla>>> getUpazilla1();
//    @GET("slum/all")
//    Call<List<Slum>> getSlum();
//    @GET("slumarea/all")
//    Call<List<SlumArea>> getSlumArea();
//
//    //    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("upazilla/{districtID}")
//    Call<List<DistrictDatum>> getUpazilla(@Path("districtID")Integer districtID);
//
//
//    //    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("slum/{thanaID}")
//    Call<List<DistrictDatum>> getSlum(@Path("thanaID")Integer thanaID);
//
//    @GET("slumarea/{slumID}")
//    Call<List<DistrictDatum>> getSlumArea(@Path("slumID")Integer slumID);
//
//    @GET("lookup/{entry}")
//    Call<List<DistrictDatum>> getEntryType(@Path("entry")String entry);
//
//    @GET("lookupdetails/all")
//    Call<List<LookupDetails>> getLookupDetails();
//
//    @GET("lookupmaster/all")
//    Call<List<LookupDetails>> getLookupMaster();
//
//    @GET("country/all")
//    Call<List<Datum>> getCountry();
//
//    @GET("household/{slumAreaID}")
//    Call<List<HouseholdDatum>> gethouseholdCode(@Path("slumAreaID")Integer slumAreaID);
}

