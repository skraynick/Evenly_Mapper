package com.evenlysarahapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.evenlysarahapp.di.components.AppComponent;
import com.evenlysarahapp.di.components.DaggerAppComponent;
import com.evenlysarahapp.di.modules.NetworkModule;

import java.io.File;

/**
 * Created by sarahkraynick on 2017-06-04.
 */
public class BaseApp extends AppCompatActivity {
    AppComponent appComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File cacheFile = new File(getCacheDir(), "responses");
        appComponent = DaggerAppComponent.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
