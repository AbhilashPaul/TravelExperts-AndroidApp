package com.example.travelexpertsandroidapp.models;

import android.app.Application;

public class TravelExpertsApp extends Application {
    private Customer loggedInUser;

    public Customer getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Customer loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
