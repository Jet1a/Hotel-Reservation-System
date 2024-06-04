package repository.memory;

import entities.Booking;
import repository.BookingRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BookingMemoryRepository implements BookingRepository {
    private final Map<String, Booking> bookings = new HashMap<>();
    private long bookId = 1;

    @Override
    public boolean addBooking(Booking booking) {
        if (booking == null) return false;
        var id = String.format("B%d", bookId);
        var book = new Booking(booking.getAccount(), booking.getRoom());
        booking.getRoom().setAvailable(false);
        bookings.put(id, book);
        bookId++;
        return true;
    }

    @Override
    public boolean updateBooking(Booking booking) {
        if (booking == null) return false;
        var id = String.format("B%d", bookId);
        bookings.replace(id, booking);
        return true;
    }

    @Override
    public boolean deleteBooking(String id) {
        if (id == null) return false;
        if (!bookings.containsKey(id)) return false;
        bookings.remove(id);
        return true;
    }

    @Override
    public Booking getBooking(String id) {
        if (id == null) return null;
        return bookings.get(id);
    }

    @Override
    public Collection<Booking> getAllBookings() {
        return bookings.values();
    }
}
