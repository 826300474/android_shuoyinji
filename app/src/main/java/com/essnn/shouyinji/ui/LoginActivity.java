package com.essnn.shouyinji.ui;

import android.os.Bundle;
import android.util.Log;

import com.essnn.shouyinji.R;
import com.essnn.shouyinji.utils.RxWeatherService;
import com.essnn.shouyinji.utils.Translation;
import com.essnn.shouyinji.utils.WeatherEntity;

import java.util.Observable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        request();
    }



    public void request() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://wthrcdn.etouch.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        RxWeatherService rxWeatherService = retrofit.create(RxWeatherService.class);
        rxWeatherService.getMessage("北京")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.i("wode", "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("wode", "onError: ");
                    }

                    @Override
                    public void onNext(WeatherEntity weatherEntity) {
                        Log.e("wode", "response == " + weatherEntity.getData().getGanmao());
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        
    }
}
