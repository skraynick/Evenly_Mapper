package com.evenlysarahapp.presentation.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evenlysarahapp.R;
import com.evenlysarahapp.data.entities.Venue;

import butterknife.ButterKnife;

/**
 * TODO butterknife
 */
public class DetailsFragment extends Fragment {

    private TextView nameOfVenue;

    private TextView addressOfVenue;

    private TextView latitudeOfVenue;

    private TextView longitudeOfVenue;

    private TextView distanceFromLocation;

    private Venue venue;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(Venue venue) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("Object", venue);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            venue = (Venue)getArguments().getSerializable("Object");
        }
    }

    private void setValuesToFields() {
        nameOfVenue.setText(venue.getName());




        addressOfVenue.setText(formatedAddress(venue));
        latitudeOfVenue.setText(Double.toString(venue.getLocation().getLat()));//TODO better formatting
        longitudeOfVenue.setText(Double.toString(venue.getLocation().getLng()));
        distanceFromLocation.setText(Integer.toString(venue.getLocation().getDistance()));
    }

    private StringBuilder formatedAddress(Venue venue) {
        StringBuilder stringToReturn = new StringBuilder();

        for(String thing: venue.getLocation().getFormattedAddress()) {
            stringToReturn.append(thing);
            stringToReturn.append(System.getProperty("line.separator"));
        }
        return stringToReturn;
    }

    private void getViewReferences(View view) {
        View textDetails = view.findViewById(R.id.text_details);
        nameOfVenue = (TextView) textDetails.findViewById(R.id.name_of_venue);
        addressOfVenue = (TextView) textDetails.findViewById(R.id.address_of_venue);
        latitudeOfVenue = (TextView) textDetails.findViewById(R.id.lat_of_venue);
        longitudeOfVenue = (TextView) textDetails.findViewById(R.id.long_of_venue);
        distanceFromLocation = (TextView) textDetails.findViewById(R.id.distance_of_venue);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_page, container, false);
        ButterKnife.bind(this, view);
        getViewReferences(view);
        setValuesToFields();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
