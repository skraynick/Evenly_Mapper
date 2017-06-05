package com.evenlysarahapp.presenters;

import android.util.Log;

import com.evenlysarahapp.data.entities.VenueResponse;
import com.evenlysarahapp.data.networking.NetworkService;
import com.evenlysarahapp.presentation.views.MainView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sarahkraynick on 2017-06-04.
 */
public class MainPresenter {

    private static final String TAG = "MainPresenter";
    private final NetworkService networkService;
    private final MainView MainView;
    private CompositeSubscription subscriptions;

    @Inject
    EventBus eventBus;

    public MainPresenter(NetworkService networkService, MainView MainView) {
        this.networkService = networkService;
        this.MainView  = MainView;
        this.subscriptions  = new CompositeSubscription();
    }

    public void getVenueList() {
        MainView.showWait();

        Subscription venueListSubscription = networkService.getCloseVenues(
            new NetworkService.GetVenueListCallback() {
        @Override
        public void onSuccess(VenueResponse venues) {
            Log.d("sarah_success", "onSuccess");
            MainView.removeWait();
            MainView.showVenueList();
            MainView.getVenueListSuccess(venues.getResponse().getVenues());

        }

        @Override
        public void onError() {

        }
    });

        subscriptions.add(venueListSubscription);
    }
}
