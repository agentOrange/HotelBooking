package com.example.HotelBooking;

import java.util.Date;

/**
 * Managers bookings for a hotel
 */
public interface BookingManager {

        /**
         * Return true if there is no booking for the given room on the date, otherwise false.
         *
         * @param room
         * @param date
         * @return
         */
        public boolean isRoomAvailable(Integer room, Date date);

        /**
         * Add a booking for the given guest in the given room on the given date.  If the room is not available, throw a suitable exception.
         *
         * @param guest
         * @param room
         * @param date
         */
        public void addBooking(String guest, Integer room, Date date);

        /**
         * Return a list of all the available room numbers for the given date.
         * @param date
         * @return
         */
        public Iterable<Integer> getAvailableRooms(Date date);

}
