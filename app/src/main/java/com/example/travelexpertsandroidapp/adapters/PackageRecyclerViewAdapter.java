package com.example.travelexpertsandroidapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.travelexpertsandroidapp.R;
import com.example.travelexpertsandroidapp.models.TravelPackage;
import java.util.List;

public class PackageRecyclerViewAdapter extends RecyclerView.Adapter<PackageRecyclerViewAdapter.ViewHolder>{
    private List<TravelPackage> packages;
    private Context context;

    public PackageRecyclerViewAdapter(List<TravelPackage> packages, Context context) {
        this.packages = packages;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.package_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TravelPackage pkg = packages.get(position);

        holder.tvPackageName.setText(pkg.getPkgName());
        holder.tvPackageDesc.setText(pkg.getPkgDesc());
    }

    @Override
    public int getItemCount() {
        return packages == null ? 0 : packages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvPackageName, tvPackageDesc;
        public ImageView imagePackage;
        public ViewHolder(View itemView){
            super(itemView);
            tvPackageName = (TextView) itemView.findViewById(R.id.tvPackageName);
            imagePackage = (ImageView) itemView.findViewById(R.id.imagePackage);
            tvPackageDesc = (TextView) itemView.findViewById(R.id.tvPackageDesc);
        }
    }
}

