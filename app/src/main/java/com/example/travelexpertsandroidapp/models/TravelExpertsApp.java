package com.example.travelexpertsandroidapp.models;

import android.app.Application;

import java.util.List;

public class TravelExpertsApp extends Application {
    private Customer loggedInUser;
    private List<TravelPackage> packages;

    public Customer getLoggedInUser() {
        return loggedInUser;
    }
    public void setLoggedInUser(Customer loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    public List<TravelPackage> getPackages() { return packages;}
    public void setPackages(List<TravelPackage> packages) { this.packages = packages; }
}
