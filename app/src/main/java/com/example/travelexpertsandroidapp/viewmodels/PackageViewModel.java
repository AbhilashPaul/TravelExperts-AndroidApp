package com.example.travelexpertsandroidapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.travelexpertsandroidapp.models.TravelPackage;
import com.example.travelexpertsandroidapp.repositories.PackageRepository;
import java.util.List;

public class PackageViewModel extends ViewModel {

    private LiveData<List<TravelPackage>> packages;
    private LiveData<String> feedbackMessage;
    PackageRepository packageRepo;

    public PackageViewModel(){
        //get instance of package repo
        packageRepo = PackageRepository.getInstance();
        //execute retrofit call to gather package data set
        packageRepo.getPackages();
        this.packages = packageRepo.getPackageDataSet();
        this.feedbackMessage = packageRepo.getFeedbackMessage();
    }

    //getters
    public LiveData<List<TravelPackage>> getPackages(){
        return packages;
    }
    public LiveData<String> getFeedback() {
        return feedbackMessage;
    }
}

