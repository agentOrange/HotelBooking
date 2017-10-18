package com.example.HotelBooking;

import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BookingManagerImplTest {
    private BookingManager testSubject;

    @Before
    public void setup(){
        testSubject = new BookingManagerImpl(101,102,201,202);
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnNullPointerExceptionWhenRoomIsNull(){
        testSubject.isRoomAvailable(null,parseDate("2017-01-01"));
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnNullPointerExceptionWhenDateIsNull(){
        testSubject.isRoomAvailable(101,null);
    }

    @Test
    public void shouldReturnTrueBecauseThereAreNoBookings(){
        assertTrue(testSubject.isRoomAvailable(101,parseDate("2017-01-01")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenAddBookingWithNullGuest() throws Exception {
        testSubject.addBooking(null, 101, parseDate("2017-01-01"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenAddBookingWithNullRoom() throws Exception {
        testSubject.addBooking("Mr  Bean",null,parseDate("2017-01-01"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenAddBookingWithNullDate() throws Exception {
        testSubject.addBooking("Mr  Bean",101,null);
    }

    @Test
    public void shouldReturnFalseWhenCheckingRoomAvailabilityAfterBooking() throws Exception {
        Date dateToBook = parseDate("2017-01-01");
        testSubject.addBooking("Mr Bean", 101, dateToBook);
        assertFalse(testSubject.isRoomAvailable(101,dateToBook));
    }

    @Test(expected = BookingFailureException.class)
    public void shouldThrowBookingFailureExceptionWhenDoubleBooking() throws Exception {
        Date dateToBook = parseDate("2017-01-01");
        testSubject.addBooking("Mr Bean", 101, dateToBook);
        testSubject.addBooking("Mr T",101,dateToBook);
    }

    @Test
    public void shouldReturnTrueWhenCheckingRoomAvailabilityUsingDifferentBooking() throws Exception {
        Date dateToBook = parseDate("2017-01-01");
        testSubject.addBooking("Mr Bean", 101, dateToBook);
        assertTrue(testSubject.isRoomAvailable(102,dateToBook));
    }

    @Test
    public void shouldReturnTwoAvailableRooms() throws Exception {
        Date unavailableDate = parseDate("2017-05-01");
        testSubject.addBooking("Mr A",101,unavailableDate);
        testSubject.addBooking("Mr B",201,unavailableDate);
        testSubject.addBooking("Mr c",202,parseDate("2017-01-01"));

        assertTrue(Iterables.size(testSubject.getAvailableRooms(unavailableDate))==2);
    }

    @Test
    public void shouldReturnAllAvailableRoomsBecauseNoBookings() {
        Date dateToCheck = parseDate("2017-05-01");

        assertTrue(Iterables.size(testSubject.getAvailableRooms(dateToCheck))==4);
    }

    private Date parseDate(String date){

        try{
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }catch(ParseException pe){
            System.err.println(pe);
        }
        return null;
    }
}
