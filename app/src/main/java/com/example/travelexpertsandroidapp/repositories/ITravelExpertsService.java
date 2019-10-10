package com.example.travelexpertsandroidapp.repositories;

import com.example.travelexpertsandroidapp.models.Customer;
import com.example.travelexpertsandroidapp.models.TravelPackage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ITravelExpertsService {

    //http://localhost:8181/TravelExperts/rs/packages/getallpackages

    @GET("packages/getallpackages")
    Call<List<TravelPackage>> getAllPackages();

    @GET("login/authenticateuser")
    Call<Customer> authenticateUser();


}
