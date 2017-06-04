package com.evenlysarahapp.data.networking;

import android.util.Log;

import com.evenlysarahapp.data.entities.Response;
import com.evenlysarahapp.data.entities.VenueResponse;
import com.evenlysarahapp.data.interfaces.VenueSearchInterface;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sarahkraynick on 2017-05-29.
 */
public class NetworkService {

    private VenueSearchInterface venueSearchInterface;

    public NetworkService(VenueSearchInterface venueSearchInterface) {
        this.venueSearchInterface = venueSearchInterface;
    }

    public Subscription getCloseVenues(final GetVenueListCallback venueListCallback) {
        return venueSearchInterface.getVenueSearch("52.500342,13.425170",
                "Z0UDXJN1FWYCH2H2AJVQAE5KDYK2ITLVEWSBEBTLDMH0OVMC",
                "QREY1MVAZY4KCTFRJN5R2GXPFUL3LKDDACUYMDNQATZT43BB",
                "20170101")//need values
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VenueResponse>() {
                    @Override
                    public void onCompleted() {
                        //venueListCallback.onSuccess(customers);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Sarah_onNext", e.getMessage().toString());

                    }

                    @Override
                    public void onNext(VenueResponse venue) {
                        Log.d("Sarah_onNext", "here");
                        //response is null for some reason. why oh why.
                        venueListCallback.onSuccess(venue);
                    }
                });
    }


    public interface GetVenueListCallback {
        void onSuccess(VenueResponse venueResponse);

        void onError();
    }

}
