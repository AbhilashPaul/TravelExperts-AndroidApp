package com.example.travelexpertsandroidapp.repositories;

import androidx.lifecycle.MutableLiveData;
import com.example.travelexpertsandroidapp.models.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that requests authentication and loggedInUser information from the remote data source and
 * maintains an in-memory cache of login status and loggedInUser credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;
    private MutableLiveData<Customer> loggedInUser = new MutableLiveData<>();
    private MutableLiveData<String> feedbackMessage = new MutableLiveData<>();

    // private constructor : singleton access
    private LoginRepository() { }

    //public method for singleton access
    public static LoginRepository getInstance(){
        if(instance == null){
            instance = new LoginRepository();
        }
        return instance;
    }

    public MutableLiveData<Customer> getLoggedInUser() {
        return loggedInUser;
    }

    public MutableLiveData<String> getFeedbackMessage() {
        return feedbackMessage;
    }

    public void login(Customer user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_TRAVELEXPERTS_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ITravelExpertsService mTravelExpertsService = retrofit.create(ITravelExpertsService.class);

        Call<Customer> call = mTravelExpertsService.authenticateUser(user);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(!response.isSuccessful()){
                    feedbackMessage.setValue("Unable to connect to the server. Status: "+response.code());
                    return;
                }
                if(response.body() == null){
                    feedbackMessage.setValue("Unable to login. Please verify login credentials!");
                    return;
                }

                loggedInUser.setValue(response.body());
                feedbackMessage.setValue("You are now logged in!");
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                feedbackMessage.setValue("Unable to connect to the server. Please verify network connectivity!");
                return;
            }
        });
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public void logout() {
        loggedInUser = null;
    }

}
