package com.example.HotelBooking;

public class BookingFailureException extends Exception {
    public BookingFailureException(String reason) {
        super(reason);
    }
}
