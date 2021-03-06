package com.evenlysarahapp.di.components;

import com.evenlysarahapp.di.modules.AppModule;
import com.evenlysarahapp.presentation.observers.MainUiObserver;
import com.evenlysarahapp.presentation.ui.activities.MainActivity;
import com.evenlysarahapp.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sarahkraynick on 2017-05-29.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(MainUiObserver mainUiObserver);
}
