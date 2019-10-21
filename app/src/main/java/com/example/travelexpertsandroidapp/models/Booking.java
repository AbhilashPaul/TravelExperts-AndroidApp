package com.example.travelexpertsandroidapp.models;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {

    private int bookingId;
    private Date bookingDate;
    private String bookingNo;
    private int customerId;
    private int packageId;
    private double travelerCount;
    private String tripTypeId;


    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public Date getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getBookingNo() {
        return bookingNo;
    }
    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getPackageId() {
        return packageId;
    }
    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
    public double getTravelerCount() {
        return travelerCount;
    }
    public void setTravelerCount(double travelerCount) {
        this.travelerCount = travelerCount;
    }
    public String getTripTypeId() {
        return tripTypeId;
    }
    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

}
