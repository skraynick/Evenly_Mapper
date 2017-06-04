package com.evenlysarahapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.evenlysarahapp.R;
import com.evenlysarahapp.data.entities.Location;
import com.evenlysarahapp.data.entities.Venue;

import java.util.List;

/**
 * Created by sarahkraynick on 2017-05-29.
 */
public class VenueSearchAdapter extends RecyclerView.Adapter<VenueSearchAdapter.ViewHolder> {

    private Context context;
    private final OnItemClickListener listener;
    private List<Venue> venues;

    public VenueSearchAdapter(Context context,
                              List<Venue> venues,
                              OnItemClickListener listener) {
        this.context = context;
        this.venues = venues;
        this.listener = listener;
    }

    @Override
    public VenueSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_item, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT)
        );
        return new ViewHolder(view);
    }

    public interface OnItemClickListener {
        void onClick(Venue item);
    }

    @Override
    public void onBindViewHolder(VenueSearchAdapter.ViewHolder holder, int position) {
        holder.click(venues.get(position), listener);
        holder.venueName.setText(venues.get(position).getName());
        holder.venueAddress.setText(venues.get(position).getLocation().getAddress());
        holder.venueLat.setText(venues.get(position).getLocation().getLat().toString());
        holder.venueLong.setText(venues.get(position).getLocation().getLng().toString());// TODO: 2017-06-04  string format
        holder.venueDistanceFrom.setText(venues.get(position).getLocation().getDistance().toString());
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView venueName, venueAddress, venueLat, venueLong, venueDistanceFrom;

        public ViewHolder(View itemView) {
            super(itemView);
            venueName = (TextView) itemView.findViewById(R.id.venueName);
            venueAddress = (TextView) itemView.findViewById(R.id.venueAddress);
            venueLat = (TextView) itemView.findViewById(R.id.venueLat);
            venueLong = (TextView) itemView.findViewById(R.id.venueLong);
            venueDistanceFrom = (TextView) itemView.findViewById(R.id.venueDistanceFrom);
        }

        public void click(final Venue venue, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(venue);
                }
            });
        }
    }
}
