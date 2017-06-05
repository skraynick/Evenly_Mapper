package com.evenlysarahapp.presentation.views;

import com.evenlysarahapp.data.entities.Venue;

import java.util.List;

/**
 * Created by sarahkraynick on 2017-06-04.
 */
public interface MainView {

    void showWait();

    void removeWait();

    void showVenueList();

    void onFailure(String appErrorMessage);

    void getVenueListSuccess(List<Venue> venues);
}
