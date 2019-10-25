package com.example.travelexpertsandroidapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

public class NetworkUtil {

    public static void registerNetworkCallback(Context context)
    {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            connectivityManager.registerNetworkCallback(builder.build(),new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(Network network) {
                            NetworkGlobalVariable.isNetworkConnected = true; // Global Static Variable
                        }
                        @Override
                        public void onLost(Network network) {
                            NetworkGlobalVariable.isNetworkConnected = false; // Global Static Variable
                        }
                    }

            );
            NetworkGlobalVariable.isNetworkConnected = false;
        }catch (Exception e){
            NetworkGlobalVariable.isNetworkConnected = false;
        }
    }
}

