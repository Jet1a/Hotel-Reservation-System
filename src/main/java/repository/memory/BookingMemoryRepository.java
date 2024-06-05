package repository.memory;

import entities.Booking;
import entities.Room;
import repository.BookingRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BookingMemoryRepository implements BookingRepository {
    private final Map<String, Booking> bookings;
    private long bookId = 1;

    public BookingMemoryRepository() {
        bookings = new HashMap<>();
    }

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
    public boolean deleteBooking(String roomNumber) {
        if (roomNumber == null) return false;
        String bookingIdToRemove = null;
        for (Map.Entry<String, Booking> entry : bookings.entrySet()) {
            if (entry.getValue().getRoom().getRoomId().equals(roomNumber)) {
                bookingIdToRemove = entry.getKey();
                entry.getValue().getRoom().setAvailable(true); // Set the room to available
                break;
            }
        }
        if (bookingIdToRemove != null) {
            bookings.remove(bookingIdToRemove); // Remove the booking entry
            return true;
        }
        return false;
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
