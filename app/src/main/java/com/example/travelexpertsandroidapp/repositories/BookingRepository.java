package com.example.travelexpertsandroidapp.repositories;

import androidx.lifecycle.MutableLiveData;
import com.example.travelexpertsandroidapp.models.Booking;
import com.example.travelexpertsandroidapp.models.Bookingdetail;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingRepository {

    private static BookingRepository instance;
    private MutableLiveData<String> feedbackMessage = new MutableLiveData<>();
    private MutableLiveData<List<Bookingdetail>> bookingDataSet = new MutableLiveData<>();

    //singleton instance constructor
    public static BookingRepository getInstance(){
        if(instance == null){instance = new BookingRepository();}
        return instance;
    }

    public MutableLiveData<List<Bookingdetail>> getBookingDataSet() {
        return bookingDataSet;
    }
    public MutableLiveData<String> getFeedbackMessage() {
        return feedbackMessage;
    }

    public void getBookings(int customerId){

        Gson gson = new GsonBuilder().setDateFormat("MMM d, yyyy, hh:mm:ss a").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_TRAVELEXPERTS_SERVICE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ITravelExpertsService mTravelExpertsService = retrofit.create(ITravelExpertsService.class);

        Call<List<Bookingdetail>> call = mTravelExpertsService.getBookings(customerId);

        call.enqueue(new Callback<List<Bookingdetail>>() {
            @Override
            public void onResponse(Call<List<Bookingdetail>> call, Response<List<Bookingdetail>> response) {
                if(!response.isSuccessful()){
                    feedbackMessage.setValue("Responded with Code:"+response.code());
                    return;
                }
                if(response.body() == null){
                    feedbackMessage.setValue("Nothing returned");
                    return;
                }
                bookingDataSet.setValue(response.body());
                feedbackMessage.setValue("Bookings received");
            }

            @Override
            public void onFailure(Call<List<Bookingdetail>> call, Throwable t) {
                feedbackMessage.setValue(t.getLocalizedMessage());
                return;
            }
        });
    }
}

