package com.example.travelexpertsandroidapp.views.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
    private LinearLayout llmTrip, llmTripHist;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        rvBookings = root.findViewById(R.id.rvBookings);
        rvBookingsHist = root.findViewById(R.id.rvBookingsHist);
        txtTrips =root.findViewById(R.id.txtTrips);
        txtTripsHist=root.findViewById(R.id.txtTripHistory);
        llmTrip = root.findViewById(R.id.llmTrip);
        llmTripHist = root.findViewById(R.id.llmTripHist);
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);

        //grab the customer id from the app context
        int customerId =((TravelExpertsApp)getActivity().getApplication()).getLoggedInUser().getCustomerId();
        //retrieve booking and dispaly them on the fragment
        bookingViewModel.getBookingsFromRepo(customerId);
        bookingViewModel.getBookings().observe(this, new Observer<List<Bookingdetail>>() {
            @Override
            public void onChanged(List<Bookingdetail> bookingdetails) {
                List<Bookingdetail> upcomingTrips = new ArrayList<Bookingdetail>();
                List<Bookingdetail> pastTrips = new ArrayList<Bookingdetail>();

                //separate upcoming trips from past trips
                if(bookingdetails.size()!=0 ){
                    for (Bookingdetail db: bookingdetails) {
                        if (db.getTripStart().after(new Date())) {
                            upcomingTrips.add(db);
                        } else {
                            pastTrips.add(db);
                        }
                    }
                }else{
                    Toast.makeText(getActivity(),"You have not made any booking with us yet!",Toast.LENGTH_LONG).show();
                }
                //display the list of upcoming trips and trip history

                if(upcomingTrips.size()!=0){
                    llmTrip.setVisibility(View.VISIBLE);
                    rvBookings.setAdapter(new BookingRecyclerViewAdapter(upcomingTrips,getContext()));
                }else{
                    //if there are no upcoming trips, hide the upcoming trips section and remove padding
                    llmTrip.setPadding(0,0,0,0);
                    llmTrip.setVisibility(View.GONE);
                }
                if(pastTrips.size()!=0){
                    llmTripHist.setVisibility(View.VISIBLE);
                    rvBookingsHist.setAdapter(new BookingRecyclerViewAdapter(pastTrips,getContext()));
                }else{
                    //if there are no past trips, hide the trip history section
                    llmTripHist.setVisibility(View.GONE);
                }
            }
        });

        //initialize recycler views
        initBookingRecyclerView();
        initBookingHistRecyclerView();

        //get any feedback from the ViewModel and toast it here
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