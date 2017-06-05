package com.evenlysarahapp.di.components;

import com.evenlysarahapp.presentation.ui.activities.MainActivity;
import com.evenlysarahapp.di.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sarahkraynick on 2017-05-29.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
}
