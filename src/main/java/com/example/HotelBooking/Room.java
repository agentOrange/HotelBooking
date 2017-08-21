package com.example.HotelBooking;

import java.util.Date;
import java.util.HashMap;

public class Room {
    private Integer roomNumber;
    private HashMap<Date,String> bookings=new HashMap<>();

    public Room(Integer roomNumber){
        this.roomNumber = roomNumber;
    }

    protected Integer getRoomNumber(){
        return roomNumber;
    }

    protected void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    protected HashMap<Date,String> getBookings(){
        return bookings;
    }

    protected void addBooking(String guest, Date date){
        bookings.put(date,guest);
    }
}
