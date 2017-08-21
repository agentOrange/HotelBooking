package com.example.HotelBooking;

import com.google.common.base.Strings;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class BookingManagerImpl implements BookingManager {
    private final ConcurrentMap<Integer,Room> rooms = new ConcurrentHashMap();
    /**
     * Using fair ordering policy could cause a performance hit. However currently we just
     * want to ensure that bookings hash map is atomic
     */
    private final Lock lock = new ReentrantLock(true);

    public BookingManagerImpl(Integer...roomNumbers){
        for(Integer roomNumber:roomNumbers){
            Room room = new Room(roomNumber);
            rooms.put(roomNumber,room);
        }
    }

    /**
     * Checks the bookings hashmap for the condition of whether room number is present and if
     * the date matches the parameter date.
     * @param room
     * @param date
     * @return True if room is available otherwise false
     */
    public boolean isRoomAvailable(Integer room, Date date) {
        if(date==null) throw new NullPointerException();

        if(rooms.get(room).getBookings().containsKey(date)){
            return false;
        }else{
            return true;
        }
    }

    public void addBooking(String guest, Integer room, Date date) {
        if (Strings.isNullOrEmpty(guest) || room == null || date == null) {
            throw new IllegalArgumentException("Either guest, room or date parameter was null");
        }

        try {
            if(lock.tryLock(10, TimeUnit.SECONDS)){
                if (isRoomAvailable(room, date)) {
                    Room booking = rooms.get(room);
                    booking.addBooking(guest,date);
                    booking.setRoomNumber(room);
                }else{
                    throw new BookingFailureException("Booking failed for Guest:"+guest +" Room:"+room +" Date:"+date);
                }
            }
        }catch(InterruptedException ie){
            System.err.println(ie);
        }catch (BookingFailureException bfe){
            System.err.println(bfe);
        }
        finally {
            lock.unlock();
        }
    }

    /**
     *
     * @param date
     * @return returns a collection of
     */
    public Iterable<Integer> getAvailableRooms(Date date) {
        return rooms.values().stream()
                .filter(r -> !(r.getBookings().containsKey(date)))
                .map(Room::getRoomNumber)
                .collect(Collectors.toSet());
    }
}
