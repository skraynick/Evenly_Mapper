package com.evenlysarahapp.data.interfaces;

import com.evenlysarahapp.data.entities.VenueResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sarahkraynick on 2017-05-29.
 */
public interface VenueSearchInterface {

    @GET("venues/search?")
    Observable<VenueResponse> getVenueSearch(@Query("ll") String latLong,
                                             @Query("client_id") String clientId,
                                             @Query("client_secret") String clientSecret,
                                             @Query("v") String version);

    @GET("venues/search?")
    Observable<VenueResponse> getVenueSearch(@Query("ll") String latLong,
                                             @Query("oauth_token") String oAuth,
                                             @Query("v") String version);
}
