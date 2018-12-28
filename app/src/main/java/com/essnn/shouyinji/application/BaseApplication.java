package com.essnn.shouyinji.application;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "f9ccee14805fb63518b98c32d40e3a16");
    }
}
