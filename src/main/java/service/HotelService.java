package service;

import entities.Account;
import entities.Booking;
import entities.Customer;
import entities.Room;
import exception.AlreadyHaveRoomNumber;
import exception.IllRoomIdArgument;
import exception.NullCollection;
import exception.ParameterException;
import repository.AccountRepository;
import repository.BookingRepository;
import repository.CustomerRepository;
import repository.RoomRepository;

import java.io.Console;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class HotelService {
    private final CustomerRepository customerRepo;
    private final AccountRepository accountRepo;
    private final RoomRepository roomRepo;
    private final BookingRepository bookingRepo;

    public HotelService(CustomerRepository customerRepo, AccountRepository accountRepo, RoomRepository roomRepo, BookingRepository bookingRepo) {
        this.customerRepo = customerRepo;
        this.accountRepo = accountRepo;
        this.roomRepo = roomRepo;
        this.bookingRepo = bookingRepo;
    }

    public static void validateInput(String input) {
        boolean validInput = input.length() == 3 && input.matches("\\d{3}");
        if (!validInput) throw new IllRoomIdArgument();
    }

    public Customer registerCustomer(String name) {
        if (name == null) {
            return null;
        } else {
            name = name.trim();
            if (name.isEmpty()) {
                return null;
            }
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z]+(?: [a-zA-Z]+)*$");
        if (!pattern.matcher(name).matches()) {
            return null;
        }
        return customerRepo.create(name);
    }

    public Account registerAccount(String id) {
        var customer = customerRepo.getCustomer(id);
        if (customer == null) return null;
        return accountRepo.create(customer);
    }

    public boolean deleteAccount(String accountId) {
        var account = accountRepo.getAccount(accountId);
        if (account == null) return false;
        return accountRepo.delete(account);
    }

    public Customer getCustomer(String id) {
        if (id == null) return null;
        return customerRepo.getCustomer(id);
    }

    public Account getAccount(String accountId) {
        if (accountId == null) return null;
        return accountRepo.getAccount(accountId);
    }

    public Collection<Account> getAllAccounts() {
        return accountRepo.getAllAccounts();
    }

    public Collection<Customer> getAllCustomers() {
        return customerRepo.getAllCustomers();
    }

    public Collection<Customer> getAllCustomersByName(String name) {
        if (name == null) return null;
        return customerRepo.getCustomerByName(name);
    }

    public boolean addRoom(Room room) {
        if (room == null) throw new NullPointerException();
        String roomId = room.getRoomId();
        if (roomId == null) throw new NullPointerException();
        try {
            validateInput(roomId);
            roomRepo.create(room);
        } catch (IllRoomIdArgument e) {
            System.out.println("Please enter correct room number");
            return false;
        } catch (ParameterException e) {
            System.out.println("Please enter correct room information");
            return false;
        } catch (AlreadyHaveRoomNumber e) {
            System.out.println("Room number " + room.getRoomId() + " already exists");
            return false;
        }
        return true;
    }

    public boolean removeRoom(String number) {
        if (number == null) throw new IllRoomIdArgument();
        validateInput(number);
        var existRoom = roomRepo.findByNumber(number);
        if (existRoom == null) throw new NullPointerException();
        if (!existRoom.isAvailable()) return false;
        roomRepo.delete(number);
        return true;
    }

    public Room findByNumber(String number) {
        if (number == null) return null;
        return roomRepo.findByNumber(number);
    }

    public Collection<Room> getAllRooms() {
        return roomRepo.getAllRooms();
    }

    public boolean reserve(Room room, Account account) {
        if (room == null || !room.isAvailable()) {
            throw new IllRoomIdArgument();
        }
        if (account == null) {
            throw new NullPointerException();
        }
        var rb = bookingRepo.getAllBookings().stream().anyMatch(booking -> booking.getRoom().equals(room));
        if (rb) {
            return false;
        }
        bookingRepo.addBooking(new Booking(account, room));
        room.setAvailable(false);
        roomRepo.update(room);
        return true;
    }

    public boolean cancelReservation(String roomNumber, String accountId) {
        if (accountId == null || roomNumber == null) {
            return false;
        } else {
            roomNumber = roomNumber.trim();
            if (roomNumber.isEmpty()) {
                return false;
            }
        }
        var room = roomRepo.findByNumber(roomNumber);
        if (room == null) throw new IllRoomIdArgument();
        if (getBookingId(room.getRoomId()) == null) throw new IllRoomIdArgument();
        Booking booking = bookingRepo.getAllBookings().stream().filter(a -> a.getAccount().getAccountId().equals(accountId)).findFirst().orElse(null);
        if (booking == null) throw new NullPointerException();
        bookingRepo.deleteBooking(roomNumber);
        room.setAvailable(true);
        roomRepo.update(room);
        return true;
    }

    public Booking getBooking(String id) {
        if (id == null) return null;
        return bookingRepo.getBooking(id);
    }

    public Collection<Booking> getAllBookings() {
        return bookingRepo.getAllBookings();
    }

    public Collection<Booking> getAllBookingsOwnedByAccount(String accountId) {
        if (accountId == null) throw new ParameterException();
        if (accountRepo.getAccount(accountId) == null) throw new NullPointerException();
        Collection<Booking> bookings = bookingRepo.getAllBookingsOwnedByAccount(accountId);
        if (bookings == null) throw new NullCollection();
        return bookings;
    }

    public Stream<Room> getAllAvailableRooms() {
        if (roomRepo.getAllRooms().isEmpty()) return Stream.empty();
        return roomRepo.getAllRooms().stream();
    }

    public String getBookingId(String roomNumber) {
        if (roomNumber == null) return null;
        return bookingRepo.getBookingId(roomNumber);
    }

    public boolean adminAuthentication() {
        Console console = System.console();
        if (console == null) return false;
        String adminPassword = "ADMIN12345";
        char[] passwordArr = console.readPassword("Enter admin password: ");
        String password = new String(passwordArr);
        if (password.equals(adminPassword)) {
            String showPassword = console.readLine("Do you want to show password? (Y/N) ");
            if ("Y".equalsIgnoreCase(showPassword)) {
                System.out.println("Password entered: " + password);
            }
            Arrays.fill(passwordArr, ' ');
            return true;
        }
        return false;
    }

}
