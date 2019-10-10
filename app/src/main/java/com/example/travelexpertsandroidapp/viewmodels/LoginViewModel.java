package com.example.travelexpertsandroidapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.travelexpertsandroidapp.repositories.LoginRepository;
import com.example.travelexpertsandroidapp.R;
import com.example.travelexpertsandroidapp.models.Customer;
import com.example.travelexpertsandroidapp.views.login.LoginFormState;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<Customer> loggedInUser = new MutableLiveData<>();
    private MutableLiveData<String> feedbackMessage = new MutableLiveData<>();
    private static LoginViewModel instance;
    private LoginRepository loginRepo;

    public LiveData<LoginFormState> getLoginFormState() { return loginFormState; }
    public LiveData<Customer> getLoggedInUser() {
        return loggedInUser;
    }
    public LiveData<String> getFeedbackMessage() { return feedbackMessage;}

    public LoginViewModel(){
        //get packages data from repo: executes retrofit call to gather package data set
        loginRepo = LoginRepository.getInstance();
        //retrieve data/feedback from repo
        this.loggedInUser = loginRepo.getLoggedInUser();
        this.feedbackMessage = loginRepo.getFeedbackMessage();
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Customer user = new Customer();
        user.setCustUsername(username);
        user.setCustPassword(password);
        //login
        loginRepo.login(user);
    }


    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.trim().length() > 5) {
            return true;
        } else {
            return false;
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
