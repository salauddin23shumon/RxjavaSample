package com.akr.rxjava1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.akr.rxjava1.model.City;
import com.akr.rxjava1.model.District;
import com.akr.rxjava1.model.Division;
import com.akr.rxjava1.model.room.AppDb;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class MainActivity2 extends AppCompatActivity {

    ListView listView1, listView2;
    ProgressBar progressBar1, progressBar2;
    ArrayAdapter<String> arr1, arr2;
    List<String> names1 = new ArrayList<>();
    List<String> names2 = new ArrayList<>();
//    DatabaseHelper databaseHelper;
    List<City> cityList = new ArrayList<>();
    List<District> districtList = new ArrayList<>();
    private AppDb db;
    Disposable disposables;
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView1 = findViewById(R.id.list1);
        listView2 = findViewById(R.id.list2);
        progressBar1 = findViewById(R.id.progress_circular1);
        progressBar2 = findViewById(R.id.progress_circular2);
//        databaseHelper = new DatabaseHelper(this);
        db = AppDb.getInstance(this);
    }

    public void backTo(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void loadData(View view) {
        rxCall();
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
    }

    private void rxCall() {

        Flowable<List<Division>> division = db.dao().getAllDivision();
        Flowable<List<District>> district = db.dao().getAllDistrict();

        Flowable<List<City>> observable1 = ApiClient.getApiService().getCity();
        Flowable<List<District>> observable2 = ApiClient.getApiService().getDistrict();
        Flowable<List<Division>> observable3 = ApiClient.getApiService().getDivision();

        Flowable<List<Object>> result =
                Flowable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers
                        .io()), observable3.subscribeOn(Schedulers.io()), new Function3<List<City>, List<District>, List<Division>, List<Object>>() {
                    @NonNull
                    @Override
                    public List<Object> apply(@NonNull List<City> cities, @NonNull List<District> districts, @NonNull List<Division> divisions) throws Exception {
                        List<Object> objects = new ArrayList<>();
                        Log.e(TAG, "apply: "+Thread.currentThread().getName() );
                        objects.addAll(cities);
                        objects.addAll(districts);
                        objects.addAll(divisions);

                        saveDistrict(cities,districts,divisions);
                        return objects;
                    }
                });

        /*result
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new FlowableSubscriber<List<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        Log.e(TAG, "onSubscribe: " );
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List<Object> objects) {
                        Log.e(TAG, "onNext: "+objects.size() );
                        Log.e(TAG, "onNext: "+Thread.currentThread().getName() );
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: "+t.getMessage() );
                    }

                    @Override
                    public void onComplete() {
                        progressBar1.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);
                        Log.e(TAG, "onComplete: " );
                    }
                } );*/

        /*result
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
                .subscribeWith(new Observer<List<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe: " );
                        disposables=d;
                    }

                    @Override
                    public void onNext(@NonNull List<Object> objects) {
                        Log.e(TAG, "onNext: "+objects.size() );
                        Log.e(TAG, "onNext: "+Thread.currentThread().getName() );

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onComplete() {
                        progressBar1.setVisibility(View.GONE);
                        progressBar2.setVisibility(View.GONE);
                        Log.e(TAG, "onComplete: " );
                    }
                });*/



        division.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Division>>() {
                    @Override
                    public void accept(List<Division> divisions) throws Exception {
                        updateUI(divisions);
                    }
                });

        district.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<District>>() {
                    @Override
                    public void accept(List<District> districts) throws Exception {
                        updateUI2(districts);
                    }
                });

       Flowable<List<?>> result2 =  Flowable.concat(result, division, district).subscribeOn(Schedulers.io());
       Flowable<List<?>> result3 =  Flowable.concat( division, district).subscribeOn(Schedulers.io());


                if (!db.dao().ifDbExist()){
                    result2.observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                }else {
                    result3.observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                }


    }

    public void saveDistrict(List<City> cities, List<District> districts, List<Division> divisions) {
        long ln = 0;
        Maybe<Long> lg = null;
        Log.e(TAG, "savDisk: "+districts.size() +" "+Thread.currentThread().getName());
        db.dao().insertDistrict(districts);
        db.dao().insertCities(cities);
        db.dao().insertDivision(divisions);

    }

    private void updateUI(List<Division> divisions) {
        Log.e(TAG, "updateUI: "+divisions.size()+" "+Thread.currentThread().getName() );
        List<String> names= new ArrayList<>();
        for (Division division : divisions) {
            names.add(division.getName());
        }

        ArrayAdapter<String> arr = new ArrayAdapter<String>(
                MainActivity2.this, R.layout.support_simple_spinner_dropdown_item, names);

        listView1.setAdapter(arr);
        progressBar1.setVisibility(View.GONE);
    }


    private void updateUI2(List<District> districts) {
        Log.e(TAG, "updateUI2: "+districts.size()+" "+Thread.currentThread().getName() );
        List<String> names= new ArrayList<>();
        for (District division : districts) {
            names.add(division.getName());
        }

        ArrayAdapter<String> arr = new ArrayAdapter<String>(
                MainActivity2.this, R.layout.support_simple_spinner_dropdown_item, names);

        listView2.setAdapter(arr);
        progressBar2.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
//        if (disposables!=null)
//        disposables.dispose();
        super.onDestroy();
    }
}