package com.evenlysarahapp.presentation.ui.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.evenlysarahapp.BaseApp;
import com.evenlysarahapp.R;
import com.evenlysarahapp.data.entities.Venue;
import com.evenlysarahapp.data.networking.NetworkService;
import com.evenlysarahapp.di.components.AppComponent;
import com.evenlysarahapp.presentation.observers.MainUiObserver;
import com.evenlysarahapp.presentation.ui.fragments.DetailsFragment;
import com.evenlysarahapp.presenters.MainPresenter;
import com.evenlysarahapp.presentation.ui.adapters.VenueSearchAdapter;
import com.evenlysarahapp.presentation.views.MainView;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseApp implements DetailsFragment.OnFragmentInteractionListener, MainView, MainUiObserver.MainUiView {

    private RecyclerView list;

    private ProgressBar progressBar;

    private MainPresenter mainPresenter;

    public MainUiObserver mainUiObserver;

    @Inject
    public NetworkService networkService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppComponent appComponent = getAppComponent();
        appComponent.inject(this);
        renderView();
        init();

        mainUiObserver = new MainUiObserver(this);

        appComponent.inject(mainUiObserver);

        mainPresenter = new MainPresenter(networkService, this);
        mainPresenter.getVenueList();
    }

    private void registerObservers() {
        mainUiObserver.subscribe();
    }

    private void init() {
        list.setLayoutManager(new LinearLayoutManager(this));
    }

    public void showWait(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    public void showVenueList() {
        list.setVisibility(View.VISIBLE);
    }

    public void onFailure(String appErrorMessage) {
    }

    public void getVenueListSuccess(List<Venue> venues) {
        VenueSearchAdapter venueSearchAdapter = new VenueSearchAdapter(getApplicationContext(), venues,
                new VenueSearchAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Venue item) {
                        mainPresenter.onUserClickVenue(item);
                    }
                });

        list.setAdapter(venueSearchAdapter);
    }

    public void renderView() {
        setContentView(R.layout.activity_main);
        list = (RecyclerView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void openDetailsScreen(Venue venue) {
        DetailsFragment firstFragment = DetailsFragment.newInstance(venue);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstFragment, "thing").commit();

        list.setVisibility(View.GONE);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //left empty intentially.
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerObservers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterObservers();
    }

    private void unregisterObservers() {
        mainUiObserver.unsubscribe();
    }
}
