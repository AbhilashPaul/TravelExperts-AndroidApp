package com.example.travelexpertsandroidapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelexpertsandroidapp.models.Customer;
import com.example.travelexpertsandroidapp.repositories.UserRepository;

public class AccountViewModel extends ViewModel {

    private UserRepository userRepository;
    private MutableLiveData<Customer> loggedInUser= new MutableLiveData<Customer>();
    private MutableLiveData<String> feedbackMessage = new MutableLiveData<>();

    //constructor
    //instantiates corresponding repository
    public AccountViewModel() {
        //get packages data from repo: executes retrofit call to gather package data set
        userRepository = UserRepository.getInstance();
        //retrieve userdata/feedback from repo
        this.loggedInUser = userRepository.getLoggedInUser();
        this.feedbackMessage = userRepository.getFeedbackMessage();
    }

    //update method call update method in repository
    public void updateUserData(Customer user){ userRepository.updateUser(user); }

    //getters
    public LiveData<Customer> getLoggedInUser() { return loggedInUser; }
    public LiveData<String> getFeedbackMessage() {
        return feedbackMessage;
    }
}