package com.example.travelexpertsandroidapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ITravelExpertsService {

    //http://localhost:8181/TravelExperts/rs/packages/getallpackages

    @GET("/packages/getallpackages")
    Call<List<Package>> getAllPackages();

}
