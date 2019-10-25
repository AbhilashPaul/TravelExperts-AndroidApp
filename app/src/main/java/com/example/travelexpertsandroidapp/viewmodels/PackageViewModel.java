package com.example.travelexpertsandroidapp.viewmodels;

import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.travelexpertsandroidapp.adapters.PackageRecyclerViewAdapter;
import com.example.travelexpertsandroidapp.models.*;
import com.example.travelexpertsandroidapp.repositories.*;

import java.util.Date;
import java.util.List;

public class PackageViewModel extends ViewModel {

    private LiveData<List<TravelPackage>> packages;
    private LiveData<String> feedbackMessage;
    private LiveData<String> bookingFeedback;
    PackageRepository packageRepo;
    PackageRecyclerViewAdapter packageRecyclerViewAdapter;

    public PackageViewModel(){
        //get instance of package repo
        packageRepo = PackageRepository.getInstance();
        //execute retrofit call to gather package data set
        packageRepo.getPackages();
        this.packages = packageRepo.getPackageDataSet();
        this.feedbackMessage = packageRepo.getFeedbackMessage();
        this.bookingFeedback = packageRepo.getBookingFeedbackMessage();
    }

    public void bookOnClickListener(View v, int position){
        TravelExpertsApp app = ((TravelExpertsApp)v.getContext().getApplicationContext());
        Customer user = app.getLoggedInUser();
        TravelPackage travelPackage = app.getPackages().get(position);

        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Booking")
                .setMessage("You are about to book the package '"+travelPackage.getPkgName()+"'."+
                        "\n Would you like to continue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Booking booking = new Booking();
                        booking.setBookingDate(new Date());
                        booking.setCustomerId(user.getCustomerId());
                        booking.setTravelerCount(Constants.DEFAULT_TRAVELLER_COUNT);
                        booking.setPackageId(travelPackage.getPackageId());
                        booking.setTripTypeId(travelPackage.getTripTypeId());
                        packageRepo.createBooking(booking);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                })
                .show();
    }

    //getters
    public LiveData<List<TravelPackage>> getPackages(){
        return packages;
    }
    public LiveData<String> getFeedback() {
        return feedbackMessage;
    }
    public LiveData<String> getBookingFeedback() { return bookingFeedback; }
}

