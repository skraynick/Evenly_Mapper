package com.evenlysarahapp.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.evenlysarahapp.BaseApp;
import com.evenlysarahapp.R;
import com.evenlysarahapp.data.entities.Location;
import com.evenlysarahapp.data.entities.Venue;
import com.evenlysarahapp.data.networking.NetworkService;
import com.evenlysarahapp.presenters.MainPresenter;
import com.evenlysarahapp.ui.adapters.VenueSearchAdapter;
import com.evenlysarahapp.ui.views.VenueListView;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseApp implements VenueListView {

    private TextView mTextMessage;

    private RecyclerView list;

    private ProgressBar progressBar;

    private MainPresenter mainPresenter;

    @Inject
    public NetworkService networkService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        renderView();
        init();

        mainPresenter = new MainPresenter(networkService, this);
        mainPresenter.getVenueList();
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
                        Toast.makeText(getApplicationContext(), item.getCategories().toString(),
                                Toast.LENGTH_LONG).show();

                    }
                });

        list.setAdapter(venueSearchAdapter);
    }

    public void renderView() {
        setContentView(R.layout.activity_main);
        list = (RecyclerView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }
}
