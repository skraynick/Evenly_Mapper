package com.evenlysarahapp.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sarahkraynick on 2017-05-29.
 */
public class Response {

    @SerializedName("status")
    public String status;
    @SerializedName("venues")
    @Expose
    private List<Venue> data = null;
    @SerializedName("confident")
    @Expose
    private Boolean confident;

    public List<Venue> getVenues() {
        return data;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public void setVenues(List<Venue> data) {
        this.data = data;
    }

    public Boolean getConfident() {
        return confident;
    }

    public void setConfident(Boolean confident) {
        this.confident = confident;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public Response(String status) {
        this.status = status;
    }
}
