package com.example.travelexpertsandroidapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.travelexpertsandroidapp.R;
import com.example.travelexpertsandroidapp.models.Bookingdetail;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookingHistRecyclerViewAdapter extends RecyclerView.Adapter<BookingHistRecyclerViewAdapter.ViewHolder>{
    private List<Bookingdetail> bookings;
    private Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
    DecimalFormat decimalFormat = new DecimalFormat("0.#");

    public BookingHistRecyclerViewAdapter(List<Bookingdetail> bookings, Context context) {
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
        holder.tvBookingNo.setText("Booking Ref: "+booking.getBooking().getBookingNo());
        holder.tvDestination.setText("Destination: "+booking.getDestination());
        holder.tvDates.setText(simpleDateFormat.format(booking.getTripStart())+" To "+
                simpleDateFormat.format(booking.getTripEnd()));
        holder.tvPrice.setText("Price: "+currencyFormat.format(booking.getBasePrice()));
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

