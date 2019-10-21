package com.example.travelexpertsandroidapp.views.packages;

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
import com.example.travelexpertsandroidapp.adapters.PackageRecyclerViewAdapter;
import com.example.travelexpertsandroidapp.models.Customer;
import com.example.travelexpertsandroidapp.models.TravelExpertsApp;
import com.example.travelexpertsandroidapp.models.TravelPackage;
import com.example.travelexpertsandroidapp.viewmodels.PackageViewModel;

import java.util.List;

public class PackageFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private PackageViewModel packageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_package, container, false);
        recyclerView = root.findViewById(R.id.rvPackages);

        packageViewModel = ViewModelProviders.of(this).get(PackageViewModel.class);

        packageViewModel.getPackages().observe(this, new Observer<List<TravelPackage>>() {
            @Override
            public void onChanged(List<TravelPackage> travelPackages) {
                TravelExpertsApp app = ((TravelExpertsApp)getContext().getApplicationContext());
                app.setPackages(travelPackages);
                recyclerView.setAdapter( new PackageRecyclerViewAdapter(travelPackages,getContext()));
            }
        });

        initPackageRecyclerView();

        /*packageViewModel.getFeedback().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
            }
        });*/

        packageViewModel.getBookingFeedback().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if( s != null){ Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();}
            }
        });

        return root;
    }

    private void initPackageRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new PackageRecyclerViewAdapter(packageViewModel.getPackages().getValue(),getContext());
        recyclerView.setAdapter(adapter);
    }

}