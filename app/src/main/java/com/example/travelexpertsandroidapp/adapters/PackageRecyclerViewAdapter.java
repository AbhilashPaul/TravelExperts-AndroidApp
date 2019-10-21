package com.example.travelexpertsandroidapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.travelexpertsandroidapp.R;
import com.example.travelexpertsandroidapp.models.TravelPackage;
import com.example.travelexpertsandroidapp.viewmodels.PackageViewModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PackageRecyclerViewAdapter extends RecyclerView.Adapter<PackageRecyclerViewAdapter.ViewHolder>{
    private List<TravelPackage> packages;
    private Context context;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
    private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);

    public List<TravelPackage> getPackages() { return packages; }

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
        StringBuilder dateString = new StringBuilder();
        dateString.append(simpleDateFormat.format(pkg.getPkgStartDate()))
                .append(" to ")
                .append(simpleDateFormat.format(pkg.getPkgEndDate()));
        holder.tvPackageDates.setText(dateString.toString());
        StringBuilder priceString = new StringBuilder();
        priceString.append("Price: ")
                .append(currencyFormat.format(pkg.getPkgBasePrice()))
                .append(" (plus GST)");
        holder.tvPackagePrice.setText(priceString.toString());
    }

    @Override
    public int getItemCount() {
        return packages == null ? 0 : packages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvPackageName, tvPackageDesc, tvPackageDates,tvPackagePrice;
        public ImageView imagePackage;
        public Button btnBook;
        public ViewHolder(View itemView){
            super(itemView);
            tvPackageName = (TextView) itemView.findViewById(R.id.tvPackageName);
            imagePackage = (ImageView) itemView.findViewById(R.id.imagePackage);
            tvPackageDesc = (TextView) itemView.findViewById(R.id.tvPackageDesc);
            tvPackageDates = (TextView) itemView.findViewById(R.id.tvPackageDates);
            tvPackagePrice =(TextView) itemView.findViewById(R.id.tvPackagePrice);
            btnBook = (Button) itemView.findViewById(R.id.btnBook);


            btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PackageViewModel packageViewModel = new PackageViewModel();
                    packageViewModel.bookOnClickListener(v, getAdapterPosition());
                }
            });
        }
    }
}

