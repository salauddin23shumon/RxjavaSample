package com.akr.rxjava1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.akr.rxjava1.model.City;
import com.akr.rxjava1.model.room.AppDb;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RxInterface {

    ListView listView;
    ProgressBar progressBar;
    ArrayAdapter<String> arr;
    List<String> names = new ArrayList<>();
    DatabaseHelper databaseHelper;
    List<City> cityList = new ArrayList<>();
    private AppDb db;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress_circular);
        databaseHelper = new DatabaseHelper(this);
        db = AppDb.getInstance(this);
    }

    public void loadData(View view) {
//        rxCall();
//        retrofitCall();
//        rxFlowable();
//        Rx();
        rx2();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void retrofitCall() {
        ApiInterface api = ApiClient.getApiService();
        Call<List<City>> call = api.getUpazilla0();
        call.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                if (response.isSuccessful()) {

                    for (City city : response.body()) {
                        names.add(city.getName());
                    }

                    arr = new ArrayAdapter<String>(
                            MainActivity.this, R.layout.support_simple_spinner_dropdown_item, names);

                    listView.setAdapter(arr);

                    progressBar.setVisibility(View.GONE);
                } else
                    Log.e(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.e(TAG, "onResponse: " + t.getMessage());

            }
        });
    }

//    private Observable<Integer> cityInsert(List<City>){
//
//        return
//
//    }

    private void rxCall() {



       Completable completable1 =  Completable.fromAction(() -> ApiClient.getApiService().getCity1())
                .subscribeOn(Schedulers.io());




        Observable<Response<List<City>>> cityObservable = ApiClient.getApiService().getCity1();
        cityObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<City>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe: " + Thread.currentThread());
                    }

                    @Override
                    public void onNext(@NonNull Response<List<City>> response) {

                        if (response != null) {
                            Log.e(TAG, "onNext: " + response.code() + " " + Thread.currentThread());
                            for (City city : response.body()) {
                                names.add(city.getName());
                            }

                            arr = new ArrayAdapter<String>(
                                    MainActivity.this, R.layout.support_simple_spinner_dropdown_item, names);

                            listView.setAdapter(arr);
                        } else {
                            Log.e(TAG, "onNext: null");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        if (e instanceof HttpException) {
                            Log.e(TAG, "onError: " + ((HttpException) e).code());
                        } else {
                            // This is another exception, like invalid JSON, etc.
                            Log.e(TAG, "onError: ", e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onComplete: " + Thread.currentThread());
                    }
                });



        /*cityObservable.fromCallable(new Response<List<Upazilla>>() {
            @Override
            public Response<List<Upazilla>> call() throws Exception {
                Log.e(TAG, "call: ");
                return loadInBackground();
                }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Response<List<Upazilla>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "onSubscribe: " + Thread.currentThread());
                    }

                    @Override
                    public void onNext(@NonNull Response<List<Upazilla>> response) {
//                        Log.e(TAG, "onNext: "+response.body().size() );     // TODO: 08-Sep-21 null exception
                        if (response != null) {
                            Log.e(TAG, "onNext: " + response.code() + " " + Thread.currentThread());
                            for (Upazilla upazilla : response.body()) {
                                names.add(upazilla.getName());
                            }

                            upazillaList = response.body();

                            arr = new ArrayAdapter<String>(
                                    MainActivity.this, R.layout.support_simple_spinner_dropdown_item, names);

                            listView.setAdapter(arr);
                        } else {
                            Log.e(TAG, "onNext: null");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (e instanceof HttpException) {
                            Log.e(TAG, "onError: " + ((HttpException) e).code());
                        } else {
                            // This is another exception, like invalid JSON, etc.
                            Log.e(TAG, "onError: ", e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " + Thread.currentThread());
                    }
                });*/

    }

    private void rxFlowable(){
        Flowable<Response<List<City>>> upazillaObservable = ApiClient.getApiService().getUpazilla2();

        upazillaObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Response<List<City>>>() {

                    @Override
                    public void onSubscribe(@NonNull Subscription s) {
                        s.request(Long.MAX_VALUE);
                        Log.e(TAG, "onSubscribe: "+Thread.currentThread().getName() );
                    }

                    @Override
                    public void onNext(@NonNull Response<List<City>> response) {
                        if (response != null) {
                            Log.e(TAG, "onNext: code-" + response.code() + " thread: " + Thread.currentThread()+ " size: "+response.body().size());
                            for (City city : response.body()) {
                                names.add(city.getName());
                                databaseHelper.insertUpazillaData(city);
                            }

                            arr = new ArrayAdapter<String>(
                                    MainActivity.this, R.layout.support_simple_spinner_dropdown_item, names);

                            listView.setAdapter(arr);
                        } else {
                            Log.e(TAG, "onNext: null");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        if (e instanceof HttpException) {
                            Log.e(TAG, "onError: " + ((HttpException) e).code());
                        } else {
                            // This is another exception, like invalid JSON, etc.
                            Log.e(TAG, "onError: ", e);
                        }
                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onComplete: " + Thread.currentThread());
                    }
                });
    }

    private void rx2(){
        loadDataFromCloud()
                .doOnNext(new Consumer<List<City>>() {
                    @Override
                    public void accept(List<City> data) throws Exception {
                        Log.d(TAG, "accept1: "+Thread.currentThread().getName());
                        MainActivity.this.saveDataToDisk(data)
                                .subscribeOn(Schedulers.io());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<City>>() {
                    @Override
                    public void accept(List<City> data) throws Exception {
                        updateUI(data);
                        Log.d(TAG, "accept: " + Thread.currentThread().getName());
                    }
                },Throwable::printStackTrace);
    }

    private void updateUI(List<City> cities) {
        for (City city : cities) {
            names.add(city.getName());
        }

        arr = new ArrayAdapter<String>(
                MainActivity.this, R.layout.support_simple_spinner_dropdown_item, names);

        listView.setAdapter(arr);
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public Observable<List<City>> loadDataFromCloud() {
        return ApiClient.getApiService().getCity();
    }

    @Override
    public Observable<Long> saveDataToDisk(List<City> cityList) {
        long ln = 0;
        Log.e(TAG, "saveDataToDisk: "+cityList.size() +" "+Thread.currentThread().getName());
        for (City city : cityList) {
            names.add(city.getName());
            ln = databaseHelper.insertUpazillaData(city);
        }
        return Observable.<Long>just(ln).
                cast(Long.class);
    }

}