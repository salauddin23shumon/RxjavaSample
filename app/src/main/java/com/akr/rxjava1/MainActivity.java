package com.akr.rxjava1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akr.rxjava1.model.Upazilla;
import com.google.gson.Gson;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayAdapter<String> arr;
    List<String> names = new ArrayList<>();
    DatabaseHelper databaseHelper;
    List<Upazilla> upazillaList = new ArrayList<>();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress_circular);
        databaseHelper = new DatabaseHelper(this);
    }

    public void loadData(View view) {
        rxCall();
//        retrofitCall();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void retrofitCall() {
        ApiInterface api = ApiClient.getApiService();
        Call<List<Upazilla>> call = api.getUpazilla0();
        call.enqueue(new Callback<List<Upazilla>>() {
            @Override
            public void onResponse(Call<List<Upazilla>> call, Response<List<Upazilla>> response) {

                if (response.isSuccessful()) {

                    for (Upazilla upazilla : response.body()) {
                        names.add(upazilla.getName());
                    }

                    arr = new ArrayAdapter<String>(
                            MainActivity.this, R.layout.support_simple_spinner_dropdown_item, names);

                    listView.setAdapter(arr);

                    progressBar.setVisibility(View.GONE);
                } else
                    Log.e(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<List<Upazilla>> call, Throwable t) {
                Log.e(TAG, "onResponse: " + t.getMessage());

            }
        });
    }

    private void rxCall() {
        Observable<Response<List<Upazilla>>> upazillaObservable = ApiClient.getApiService().getUpazilla1();

        upazillaObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<Response<List<Upazilla>>>() {
                    @Override
                    public void accept(Response<List<Upazilla>> listResponse) throws Exception {
                        Log.e(TAG, "accept: " + Thread.currentThread() + " " + listResponse.body().size());
                    }
                })
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

        /*upazillaObservable.fromCallable(new Response<List<Upazilla>>() {
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

}