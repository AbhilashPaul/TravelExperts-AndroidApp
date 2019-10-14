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
import com.example.travelexpertsandroidapp.models.Bookingdetail;

import java.util.List;

public class BookingRecyclerViewAdapter extends RecyclerView.Adapter<BookingRecyclerViewAdapter.ViewHolder>{
    private List<Bookingdetail> bookings;
    private Context context;

    public BookingRecyclerViewAdapter(List<Bookingdetail> bookings, Context context) {
        this.bookings = bookings;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.booking_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bookingdetail booking = bookings.get(position);

        holder.tvBookingNo.setText(booking.getDestination());
        holder.tvDestination.setText(booking.getDescription());
        holder.tvDates.setText(booking.getTripStart()+" To "+booking.getTripEnd());
        holder.tvPrice.setText(String.valueOf(booking.getBasePrice()));
    }

    @Override
    public int getItemCount() {
        return bookings == null ? 0 : bookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvBookingNo, tvDestination, tvPrice, tvDates;
        public ViewHolder(View itemView){
            super(itemView);
            tvBookingNo = (TextView) itemView.findViewById(R.id.tvBookingNo);
            tvDestination = (TextView) itemView.findViewById(R.id.tvDestination);
            tvPrice= (TextView) itemView.findViewById(R.id.tvPrice);
            tvDates= (TextView) itemView.findViewById(R.id.tvDates);
        }
    }
}

