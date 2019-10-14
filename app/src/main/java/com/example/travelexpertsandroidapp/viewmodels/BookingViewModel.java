package com.example.travelexpertsandroidapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelexpertsandroidapp.models.Bookingdetail;
import com.example.travelexpertsandroidapp.models.TravelPackage;
import com.example.travelexpertsandroidapp.repositories.BookingRepository;
import com.example.travelexpertsandroidapp.repositories.PackageRepository;

import java.util.List;

public class BookingViewModel extends ViewModel {

    private LiveData<List<Bookingdetail>> bookings;
    private LiveData<String> feedbackMessage;
    BookingRepository bookingRepo;

    public BookingViewModel(){
        //get instance of package repo
        bookingRepo = BookingRepository.getInstance();
    }

    public void getBookingsFromRepo(int customerId){
        //execute retrofit call to gather package data set
        bookingRepo.getBookings(customerId);
        this.bookings = bookingRepo.getBookingDataSet();
        this.feedbackMessage = bookingRepo.getFeedbackMessage();
    }

    //getters
    public LiveData<List<Bookingdetail>> getBookings(){
        return bookings;
    }
    public LiveData<String> getFeedback() {
        return feedbackMessage;
    }
}

