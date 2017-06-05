package com.evenlysarahapp.presentation.observers;

import android.util.Log;

import com.evenlysarahapp.presentation.events.OnOpenDetailsPageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

/**
 * Created by sarahkraynick on 2017-06-04.
 */

public class MainUiObserver {

    private static final String TAG = "MainUiObserver";
    //private MainUIView view;

    @Inject
    EventBus eventBus;

    private MainUiView view;

    public MainUiObserver(MainUiView view) {
        this.view = view;
    }

    public void subscribe() {
        eventBus.register(this);
    }

    public void unsubscribe() {
        eventBus.unregister(this);
    }

    @Subscribe
    public void onOpenDetailsScreen(OnOpenDetailsPageEvent onOpenDetailsPageEvent) {
        Log.d(TAG, "onOpenDetailsScreen");
        view.openDetailsScreen();
    }

    public interface MainUiView {
        void openDetailsScreen();
    }

}
