package com.example.travelexpertsandroidapp.repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
import com.example.travelexpertsandroidapp.models.TravelPackage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackageRepository {

    private static PackageRepository instance;
    private MutableLiveData<String> feedbackMessage = new MutableLiveData<>();
    private MutableLiveData<List<TravelPackage>> packageDataSet = new MutableLiveData<>();

    //singleton instance constructor
    public static PackageRepository getInstance(){
        if(instance == null){
            instance = new PackageRepository();
        }
        return instance;
    }

    public MutableLiveData<List<TravelPackage>> getPackageDataSet() {
        return packageDataSet;
    }

    public MutableLiveData<String> getFeedbackMessage() {
        return feedbackMessage;
    }

    public void getPackages(){

        Gson gson = new GsonBuilder().setDateFormat("MMM d, yyyy, hh:mm:ss a").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL_TRAVELEXPERTS_SERVICE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ITravelExpertsService mTravelExpertsService = retrofit.create(ITravelExpertsService.class);

        Call<List<TravelPackage>> call = mTravelExpertsService.getAllPackages();

        call.enqueue(new Callback<List<TravelPackage>>() {
            @Override
            public void onResponse(Call<List<TravelPackage>> call, Response<List<TravelPackage>> response) {
                if(!response.isSuccessful()){
                    feedbackMessage.setValue("Responded with Code:"+response.code());
                    return;
                }
                if(response.body() == null){
                    feedbackMessage.setValue("Nothing returned");
                    return;
                }
                packageDataSet.setValue(response.body());
                feedbackMessage.setValue("Packages received");
            }

            @Override
            public void onFailure(Call<List<TravelPackage>> call, Throwable t) {
                feedbackMessage.setValue(t.getLocalizedMessage());
                return;
            }
        });
    }
}

