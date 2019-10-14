package com.example.travelexpertsandroidapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.travelexpertsandroidapp.models.Customer;
import com.example.travelexpertsandroidapp.models.TravelExpertsApp;
import com.example.travelexpertsandroidapp.repositories.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Customer loggedInUser = (Customer)bundle.getSerializable("user");
        TravelExpertsApp app = ((TravelExpertsApp)this.getApplication());
        app.setLoggedInUser(loggedInUser);

        if (checkPlatform() && checkPermission()) {
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(),loggedInUser.getCustFirstName()+" "+loggedInUser.getCustLastName(),
                Toast.LENGTH_LONG).show();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_packages, R.id.navigation_bookings, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        } else {
            explainPermission();
            requestPermission();
        }
    }

    //Check the platform
    private boolean checkPlatform() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    //Check if permission is granted
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            //not granted
            return false;
        }
        //granted
        return true;
    }

    //explain permission
    private void explainPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.INTERNET)) {
            Toast.makeText(this, "Network Connection is required!", Toast.LENGTH_SHORT).show();
        }
    }

    //request permission
    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.INTERNET},
                Constants.PERMISSION_REQUEST_CODE);
    }

}
