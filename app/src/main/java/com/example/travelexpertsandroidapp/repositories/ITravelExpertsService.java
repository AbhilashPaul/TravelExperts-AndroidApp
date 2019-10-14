package com.example.travelexpertsandroidapp.repositories;

import com.example.travelexpertsandroidapp.models.Bookingdetail;
import com.example.travelexpertsandroidapp.models.Customer;
import com.example.travelexpertsandroidapp.models.TravelPackage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ITravelExpertsService {

    @GET("packages/getallpackages")
    Call<List<TravelPackage>> getAllPackages();

    @POST("login/authenticateuser")
    Call<Customer> authenticateUser(@Body Customer user);

    @GET("customer/getbookings/{id}")
    Call<List<Bookingdetail>> getBookings(@Path("id") int id);

    @PUT("customer/updatecustomer")
    Call<Customer> updateUser(@Body Customer user);
}
