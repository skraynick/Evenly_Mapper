package com.evenlysarahapp.di.modules;

import android.app.Application;

import com.evenlysarahapp.presentation.observers.MainUiObserver;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sarahkraynick on 2017-06-05.
 */
@Module
public class AppModule {
    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }

}
