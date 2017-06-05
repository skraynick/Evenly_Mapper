package com.evenlysarahapp.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
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
import com.foursquare.android.nativeoauth.FoursquareCancelException;
import com.foursquare.android.nativeoauth.FoursquareDenyException;
import com.foursquare.android.nativeoauth.FoursquareInvalidRequestException;
import com.foursquare.android.nativeoauth.FoursquareOAuth;
import com.foursquare.android.nativeoauth.FoursquareOAuthException;
import com.foursquare.android.nativeoauth.FoursquareUnsupportedVersionException;
import com.foursquare.android.nativeoauth.model.AccessTokenResponse;
import com.foursquare.android.nativeoauth.model.AuthCodeResponse;

import java.util.List;

import javax.inject.Inject;

/**
 * //TODO need to implement mapping functionality.
 */
public class MainActivity extends BaseApp implements DetailsFragment.OnFragmentInteractionListener, MainView, MainUiObserver.MainUiView {

    private RecyclerView list;

    private ProgressBar progressBar;

    private MainPresenter mainPresenter;

    public MainUiObserver mainUiObserver;

    @Inject
    public NetworkService networkService;

    private FrameLayout frameLayout;

    private DetailsFragment detailsFragment;

    private static final int REQUEST_CODE_FSQ_CONNECT = 200;

    private static final int REQUEST_CODE_FSQ_TOKEN_EXCHANGE = 201;

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

        getauthentiacationCode();
    }

    private void getauthentiacationCode() {
        Intent intent = FoursquareOAuth.getConnectIntent(getApplicationContext(), NetworkService.CLIENT_ID);
        startActivityForResult(intent, REQUEST_CODE_FSQ_CONNECT);
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
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        frameLayout.setVisibility(View.GONE);
        list = (RecyclerView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void openDetailsScreen(Venue venue) {
        detailsFragment = DetailsFragment.newInstance(venue);
        frameLayout.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_container, detailsFragment, "thing").commit();


        list.setVisibility(View.GONE);
    }

    @Override
    public void closeDetailsScreen() {
        frameLayout.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_FSQ_CONNECT:
                onCompleteConnect(resultCode, data);
                break;

            case REQUEST_CODE_FSQ_TOKEN_EXCHANGE:
                onCompleteTokenExchange(resultCode, data);
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onCompleteConnect(int resultCode, Intent data) {
        AuthCodeResponse codeResponse = FoursquareOAuth.getAuthCodeFromResult(resultCode, data);
        Exception exception = codeResponse.getException();

        if (exception == null) {
            // Success.
            String code = codeResponse.getCode();
            performTokenExchange(code);

        } else {
            if (exception instanceof FoursquareCancelException) {
                // Cancel.
                toastMessage(this, "Canceled");

            } else if (exception instanceof FoursquareDenyException) {
                // Deny.
                toastMessage(this, "Denied");

            } else if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = exception.getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                toastMessage(this, errorMessage + " [" + errorCode + "]");

            } else if (exception instanceof FoursquareUnsupportedVersionException) {
                // Unsupported Fourquare app version on the device.
                toastError(this, exception);

            } else if (exception instanceof FoursquareInvalidRequestException) {
                // Invalid request.
                toastError(this, exception);

            } else {
                // Error.
                toastError(this, exception);
            }
        }
    }

    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastError(Context context, Throwable t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Exchange a code for an OAuth Token. Note that we do not recommend you
     * do this in your app, rather do the exchange on your server. Added here
     * for demo purposes.
     *
     * @param code
     *          The auth code returned from the native auth flow.
     */
    private void performTokenExchange(String code) {
        Intent intent = FoursquareOAuth.getTokenExchangeIntent(this,
                NetworkService.CLIENT_ID,
                NetworkService.CLIENT_SECRET,
                code);
        startActivityForResult(intent, REQUEST_CODE_FSQ_TOKEN_EXCHANGE);
    }

    private void onCompleteTokenExchange(int resultCode, Intent data) {
        AccessTokenResponse tokenResponse = FoursquareOAuth.getTokenFromResult(resultCode, data);
        Exception exception = tokenResponse.getException();

        if (exception == null) {
            String accessToken = tokenResponse.getAccessToken();
            // Success.
            toastMessage(this, "Access token: " + accessToken);

            //Set the oauth
            mainPresenter.setoAuthToken(accessToken);
            //get the venue list
            mainPresenter.getVenueList();
        } else {
            if (exception instanceof FoursquareOAuthException) {
                // OAuth error.
                String errorMessage = ((FoursquareOAuthException) exception).getMessage();
                String errorCode = ((FoursquareOAuthException) exception).getErrorCode();
                toastMessage(this, errorMessage + " [" + errorCode + "]");

            } else {
                // Other exception type.
                toastError(this, exception);
            }
        }
    }
}
