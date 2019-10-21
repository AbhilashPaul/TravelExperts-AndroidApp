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
public class UserRepository {

    private static volatile UserRepository instance;
    private MutableLiveData<Customer> loggedInUser = new MutableLiveData<>();
    private MutableLiveData<String> feedbackMessage = new MutableLiveData<>();


    // private constructor : singleton access
    private UserRepository() { }

    //public method for singleton access
    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    //getters
    public MutableLiveData<Customer> getLoggedInUser() {
        return loggedInUser;
    }
    public MutableLiveData<String> getFeedbackMessage() {
        return feedbackMessage;
    }

    //method to authenticate using the TravelExperts Restful service
    //call returns a customer object in JSON string format on successful authentication
    public void login(Customer user) {
        //build rest service retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_TRAVELEXPERTS_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //create the service in order to utilize available services
        ITravelExpertsService mTravelExpertsService = retrofit.create(ITravelExpertsService.class);

        //create a call to authenticate the user
        Call<Customer> call = mTravelExpertsService.authenticateUser(user);

        //enque the call
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {

                if(!response.isSuccessful()){  //the request could not establish connection
                    feedbackMessage.setValue("Unable to connect to the server. Status: "+response.code());
                    return;
                }
                if(response.body() == null){    //request was successful but response was null
                    feedbackMessage.setValue("Unable to login. Please verify login credentials!");
                    return;
                }
                //response contains customer object json string
                loggedInUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                feedbackMessage.setValue("Unable to connect to the server. Please verify network connectivity!");
                return;
            }
        });
    }

    public void updateUser(Customer user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_TRAVELEXPERTS_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ITravelExpertsService mTravelExpertsService = retrofit.create(ITravelExpertsService.class);

        Call<Customer> call = mTravelExpertsService.updateUser(user);

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if(!response.isSuccessful()){
                    feedbackMessage.setValue("Unable to connect to the server. Status: "+response.code());
                    return;
                }
                if(response.body() == null){
                    feedbackMessage.setValue("Unable to perform update. Please try again!");
                    return;
                }

                loggedInUser.setValue(response.body());
                feedbackMessage.setValue("Successfully updated");

            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                feedbackMessage.setValue("Unable to connect to the server. Please verify network connectivity!");
                return;
            }
        });
    }
}
