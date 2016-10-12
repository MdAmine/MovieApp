package com.mdamine.movieapp;

import android.app.Application;
import android.content.Context;


/**
 * Created by SAOUD Mohamed Amine on 11/10/2016.
 */
public class MyApp extends Application {

    private static MyApp sInstance;


    public static MyApp getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
