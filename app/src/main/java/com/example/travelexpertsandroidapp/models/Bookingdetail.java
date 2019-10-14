package com.example.travelexpertsandroidapp.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Bookingdetail implements Serializable {

    private int bookingDetailId;
    private BigDecimal agencyCommission;
    private BigDecimal basePrice;
    private String classId;
    private String description;
    private String destination;
    private String feeId;
    private double itineraryNo;
    private int productSupplierId;
    private String regionId;
    private Date tripEnd;
    private Date tripStart;
    private Booking booking;

    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public BigDecimal getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(BigDecimal agencyCommission) {
        this.agencyCommission = agencyCommission;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public double getItineraryNo() {
        return itineraryNo;
    }

    public void setItineraryNo(double itineraryNo) {
        this.itineraryNo = itineraryNo;
    }

    public int getProductSupplierId() {
        return productSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        this.productSupplierId = productSupplierId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Date getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(Date tripEnd) {
        this.tripEnd = tripEnd;
    }

    public Date getTripStart() {
        return tripStart;
    }

    public void setTripStart(Date tripStart) {
        this.tripStart = tripStart;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}