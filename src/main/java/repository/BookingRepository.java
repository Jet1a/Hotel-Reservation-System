package repository;

import entities.Booking;

import java.util.Collection;

public interface BookingRepository {
    boolean addBooking(Booking booking);

    boolean updateBooking(Booking booking);

    boolean deleteBooking(String roomNumber);

    Booking getBooking(String id);

    Collection<Booking> getAllBookings();

}
