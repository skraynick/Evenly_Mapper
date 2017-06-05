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

    public static final String CLIENT_ID = "Z0UDXJN1FWYCH2H2AJVQAE5KDYK2ITLVEWSBEBTLDMH0OVMC";

    public static final String CLIENT_SECRET = "QREY1MVAZY4KCTFRJN5R2GXPFUL3LKDDACUYMDNQATZT43BB";

    private static final String VERSION = "20170101";

    private VenueSearchInterface venueSearchInterface;

    public NetworkService(VenueSearchInterface venueSearchInterface) {
        this.venueSearchInterface = venueSearchInterface;
    }

    public Subscription getCloseVenues(String oauthCode, final GetVenueListCallback venueListCallback) {
        return venueSearchInterface.getVenueSearch("52.500342,13.425170",
                oauthCode,
                VERSION)//TODO separate out values
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VenueResponse>() {
                    @Override
                    public void onCompleted() {
                        //venueListCallback.onSuccess(customers);

                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO log error properly
                    }

                    @Override
                    public void onNext(VenueResponse venue) {
                        venueListCallback.onSuccess(venue);
                    }
                });
    }


    public interface GetVenueListCallback {
        void onSuccess(VenueResponse venueResponse);

        void onError();
    }

}
