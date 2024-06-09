package repository.file;

import entities.Booking;
import repository.BookingRepository;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingFileRepository implements BookingRepository, Serializable {
    private final String filename = "bookings.dat";
    private Map<String, Booking> bookings;
    private long bookId;

    public BookingFileRepository() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                bookings = (Map<String, Booking>) ois.readObject();
                bookId = ois.readLong();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            bookings = new HashMap<>();
            bookId = 1;
        }
    }

    private void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(bookings);
            oos.writeLong(bookId);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addBooking(Booking booking) {
        if (booking == null) return false;
        if (bookings.containsValue(booking)) return false;
        var id = String.format("B%d", bookId);
        var book = new Booking(booking.getAccount(), booking.getRoom());
        booking.getRoom().setAvailable(false);
        bookings.put(id, book);
        bookId++;
        saveToFile();
        return true;
    }

    @Override
    public boolean updateBooking(Booking booking) {
        if (booking == null) return false;
        var id = String.format("B%d", bookId);
        bookings.replace(id, booking);
        saveToFile();
        return true;
    }

    @Override
    public boolean deleteBooking(String roomNumber) {
        if (roomNumber == null) return false;
        String bookingIdToRemove = null;
        for (var entry : bookings.entrySet()) {
            if (entry.getValue().getRoom().getRoomId().equals(roomNumber)) {
                bookingIdToRemove = entry.getKey();
                break;
            }
        }
        if (bookingIdToRemove != null) {
            bookings.remove(bookingIdToRemove);
            saveToFile();
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
    public String getBookingId(String roomNumber) {
        if (roomNumber == null) return null;
        for (var entry : bookings.entrySet()) {
            if (entry.getValue().getRoom().getRoomId().equals(roomNumber)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Collection<Booking> getAllBookings() {
        return bookings.values();
    }

    @Override
    public Collection<Booking> getAllBookingsOwnedByAccount(String accountId) {
        return bookings.values()
                .stream()
                .filter(a -> a.getAccount().getAccountId().equals(accountId))
                .collect(Collectors.toList());
    }
}
