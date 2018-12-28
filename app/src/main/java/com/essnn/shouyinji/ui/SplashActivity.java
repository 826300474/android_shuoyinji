package com.essnn.shouyinji.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.essnn.shouyinji.MainActivity;
import com.essnn.shouyinji.R;
import com.essnn.shouyinji.utils.ShareUtils;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class SplashActivity extends AppCompatActivity {
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {

        subscription =  Observable.timer(3,TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                if(isLogin()){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                }
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    private boolean isLogin(){
        if(ShareUtils.getString(this,"username","") != ""){
            return true;
        }else {
            return false;
        }
    }
}
