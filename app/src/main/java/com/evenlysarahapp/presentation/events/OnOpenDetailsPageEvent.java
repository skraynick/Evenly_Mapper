package com.evenlysarahapp.presentation.events;

import com.evenlysarahapp.data.entities.Venue;

/**
 * Created by sarahkraynick on 2017-06-04.
 */
public class OnOpenDetailsPageEvent {

    private Venue venue;

    public OnOpenDetailsPageEvent(Venue venue) {
        this.venue = venue;
    }

    public Venue getVenue() {
        return venue;
    }
}
