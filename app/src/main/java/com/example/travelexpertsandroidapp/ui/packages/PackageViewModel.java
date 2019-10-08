package com.example.travelexpertsandroidapp.ui.packages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travelexpertsandroidapp.ITravelExpertsService;
import com.example.travelexpertsandroidapp.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PackageViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mErrorMessage = new MutableLiveData<>();
    private MutableLiveData<List<Package>> mDiscoveredPackages = new MutableLiveData<>();

    private ITravelExpertsService mTravelExpertsService;


    public PackageViewModel() {
        super();
        init();
        initAnonService();

    }

    private void init() {
        mText = new MutableLiveData<>();
        mText.setValue("This is account fragment");
    }
    private void initAnonService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL_TRAVELEXPERTS_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mTravelExpertsService = retrofit.create(ITravelExpertsService.class);
    }

    private void showError(String message) {
        mErrorMessage.postValue(message);
    }

    public LiveData<List<Package>> getDiscoveredPackages() {
        discoverPackages();
        return mDiscoveredPackages;
    }

    private void discoverPackages() {
        mTravelExpertsService.getAllPackages().enqueue(new Callback<List<Package>>() {
            @Override
            public void onResponse(Call<List<Package>> call, Response<List<Package>> response) {
                if(!response.isSuccessful()){
                    showError(response.message());
                    return;
                }
                if(response.body() != null){
                    mDiscoveredPackages.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Package>> call, Throwable t) {
                showError(t.getLocalizedMessage());
                return;
            }
        });
    }


}