package com.evenlysarahapp.presenters;

import android.util.Log;

import com.evenlysarahapp.data.entities.Response;
import com.evenlysarahapp.data.entities.VenueResponse;
import com.evenlysarahapp.data.networking.NetworkService;
import com.evenlysarahapp.ui.views.VenueListView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sarahkraynick on 2017-06-04.
 */
public class MainPresenter {

    private static final String TAG = "MainPresenter";
    private final NetworkService networkService;
    private final VenueListView venueListView;
    private CompositeSubscription subscriptions;

    public MainPresenter(NetworkService networkService, VenueListView venueListView) {
        this.networkService = networkService;
        this.venueListView  = venueListView;
        this.subscriptions  = new CompositeSubscription();
    }

    public void getVenueList() {
        venueListView.showWait();

        Subscription venueListSubscription = networkService.getCloseVenues(
            new NetworkService.GetVenueListCallback() {
        @Override
        public void onSuccess(VenueResponse venues) {
            Log.d("sarah_success", "onSuccess");
            venueListView.removeWait();
            venueListView.showVenueList();
            venueListView.getVenueListSuccess(venues.getResponse().getVenues());

        }

        @Override
        public void onError() {

        }
    });

        subscriptions.add(venueListSubscription);
    }
}
