package com.example.travelexpertsandroidapp.views.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelexpertsandroidapp.R;
import com.example.travelexpertsandroidapp.adapters.BookingRecyclerViewAdapter;
import com.example.travelexpertsandroidapp.adapters.PackageRecyclerViewAdapter;
import com.example.travelexpertsandroidapp.models.Bookingdetail;
import com.example.travelexpertsandroidapp.models.TravelExpertsApp;
import com.example.travelexpertsandroidapp.models.TravelPackage;
import com.example.travelexpertsandroidapp.viewmodels.BookingViewModel;
import com.example.travelexpertsandroidapp.viewmodels.PackageViewModel;

import java.util.List;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    private RecyclerView rvBookings;
    private RecyclerView.Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_booking, container, false);
        rvBookings = root.findViewById(R.id.rvBookings);

        int customerId =((TravelExpertsApp)getActivity().getApplication()).getLoggedInUser().getCustomerId();
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.getBookingsFromRepo(customerId);

        bookingViewModel.getBookings().observe(this, new Observer<List<Bookingdetail>>() {
            @Override
            public void onChanged(List<Bookingdetail> bookingdetails) {
                //Only for debugging. remove once done.
                StringBuilder sb= new StringBuilder();
                for(Bookingdetail booking : bookingdetails){
                    sb.append(booking.getBookingDetailId());
                    sb.append("/");
                }
                Toast.makeText(getActivity(),sb.toString(),Toast.LENGTH_LONG).show();
                rvBookings.setAdapter( new BookingRecyclerViewAdapter(bookingdetails,getContext()));
            }
        });

        initPackageRecyclerView();

        bookingViewModel.getFeedback().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void initPackageRecyclerView() {
        rvBookings.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvBookings.setLayoutManager(llm);
        adapter = new BookingRecyclerViewAdapter(bookingViewModel.getBookings().getValue(), getContext());
        rvBookings.setAdapter(adapter);
    }

}