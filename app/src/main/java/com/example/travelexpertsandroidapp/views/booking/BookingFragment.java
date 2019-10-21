package com.example.travelexpertsandroidapp.views.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelexpertsandroidapp.R;
import com.example.travelexpertsandroidapp.adapters.BookingHistRecyclerViewAdapter;
import com.example.travelexpertsandroidapp.adapters.BookingRecyclerViewAdapter;
import com.example.travelexpertsandroidapp.models.Bookingdetail;
import com.example.travelexpertsandroidapp.models.TravelExpertsApp;
import com.example.travelexpertsandroidapp.viewmodels.BookingViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    private RecyclerView rvBookings;
    private RecyclerView rvBookingsHist;
    private RecyclerView.Adapter adapterBookingHist, adapterBooking;
    private TextView txtTrips, txtTripsHist;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        rvBookings = root.findViewById(R.id.rvBookings);
        rvBookingsHist = root.findViewById(R.id.rvBookingsHist);
        txtTrips =root.findViewById(R.id.txtTrips);
        txtTripsHist=root.findViewById(R.id.txtTripHistory);
        txtTripsHist.setVisibility(View.INVISIBLE);
        txtTrips.setVisibility(View.INVISIBLE);

        int customerId =((TravelExpertsApp)getActivity().getApplication()).getLoggedInUser().getCustomerId();
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.getBookingsFromRepo(customerId);

        bookingViewModel.getBookings().observe(this, new Observer<List<Bookingdetail>>() {
            @Override
            public void onChanged(List<Bookingdetail> bookingdetails) {
                List<Bookingdetail> upcomingTrips = new ArrayList<Bookingdetail>();
                List<Bookingdetail> pastTrips = new ArrayList<Bookingdetail>();
                //separate upcoming trips from past trips
                for (Bookingdetail db: bookingdetails){
                    if(db.getTripStart().after(new Date())){
                        upcomingTrips.add(db);
                    }else{
                        pastTrips.add(db);
                    }
                }
                //display both the list of trips
                if(upcomingTrips.size()!=0){
                    txtTrips.setVisibility(View.VISIBLE);
                    rvBookings.setVisibility(View.VISIBLE);
                    rvBookings.setAdapter(new BookingRecyclerViewAdapter(upcomingTrips,getContext()));
                }else{
                    txtTrips.setVisibility(View.GONE);
                    rvBookings.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"You have no upcoming Trips",Toast.LENGTH_LONG).show();
                }
                if(pastTrips.size()!=0){
                    txtTripsHist.setVisibility(View.VISIBLE);
                    rvBookingsHist.setVisibility(View.VISIBLE);
                    rvBookingsHist.setAdapter(new BookingRecyclerViewAdapter(pastTrips,getContext()));
                }else{
                    txtTripsHist.setVisibility(View.GONE);
                    rvBookingsHist.setVisibility(View.GONE);
                  }
            }
        });

        //initialize recycler views
        initBookingRecyclerView();
        initBookingHistRecyclerView();

        //get any feedback from ViewModel
        bookingViewModel.getFeedback().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    //initializes booking history recycler view
    private void initBookingHistRecyclerView() {
        rvBookingsHist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvBookingsHist.setLayoutManager(llm);
        adapterBookingHist = new BookingHistRecyclerViewAdapter(bookingViewModel.getBookings().getValue(), getContext());
        rvBookingsHist.setAdapter(adapterBookingHist);
    }
    //initializes booking recycler view
    private void initBookingRecyclerView() {
        rvBookings.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvBookings.setLayoutManager(llm);
        adapterBooking = new BookingHistRecyclerViewAdapter(bookingViewModel.getBookings().getValue(), getContext());
        rvBookings.setAdapter(adapterBooking);
    }

}