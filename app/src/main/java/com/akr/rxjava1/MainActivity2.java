package com.akr.rxjava1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;

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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.functions.Function2;

public class MainActivity2 extends AppCompatActivity {

    ListView listView1, listView2;
    ProgressBar progressBar1, progressBar2;
    ArrayAdapter<String> arr1, arr2;
    List<String> names1 = new ArrayList<>();
    List<String> names2 = new ArrayList<>();
    DatabaseHelper databaseHelper;
    List<City> cityList = new ArrayList<>();
    List<District> districtList = new ArrayList<>();
    private AppDb db;
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView1 = findViewById(R.id.list1);
        listView2 = findViewById(R.id.list2);
        progressBar1 = findViewById(R.id.progress_circular1);
        progressBar2 = findViewById(R.id.progress_circular2);
        databaseHelper = new DatabaseHelper(this);
        db = AppDb.getInstance(this);
    }

    public void backTo(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void loadData(View view) {
        rxCall();
    }

    private void rxCall() {


        Observable<List<City>> observable1 = ApiClient.getApiService().getCity();
        Observable<List<District>> observable2 = ApiClient.getApiService().getDistrict();
        Observable<List<Division>> observable3 = ApiClient.getApiService().getDivision();

        Observable<List<Object>> result =
                Observable.zip(observable1.subscribeOn(Schedulers.io()), observable2.subscribeOn(Schedulers
                        .io()), observable3.subscribeOn(Schedulers.io()), new Function3<List<City>, List<District>, List<Division>, List<Object>>() {
                    @NonNull
                    @Override
                    public List<Object> apply(@NonNull List<City> cities, @NonNull List<District> districts, @NonNull List<Division> divisions) throws Exception {
                        List<Object> objects = new ArrayList<>();
                        Log.e(TAG, "apply: "+Thread.currentThread().getName() );
                        objects.addAll(cities);
                        objects.addAll(districts);
                        objects.addAll(divisions);
                        return objects;
                    }
                });

        result
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Object> objects) {
                        Log.e(TAG, "onNext: "+objects.size() );
                        Log.e(TAG, "onNext: "+Thread.currentThread().getName() );

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}