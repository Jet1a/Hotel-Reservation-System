package service;

import entities.Account;
import entities.Booking;
import entities.Customer;
import entities.Room;
import repository.AccountRepository;
import repository.BookingRepository;
import repository.CustomerRepository;
import repository.RoomRepository;

import java.util.Collection;

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

    public Customer registerCustomer(String name) {
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
        if (room == null) return false;
        roomRepo.create(room);
        return true;
    }

    public boolean removeRoom(String number) {
        if (number == null) return false;
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
        if (room == null || account == null) return false;
        bookingRepo.addBooking(new Booking(account, room));
        return true;
    }

    public boolean cancelReservation(String roomNumber) {
        if (roomNumber == null) return false;
        bookingRepo.deleteBooking(roomNumber);
        return true;
    }

    public Booking getBooking(String id) {
        if (id == null) return null;
        return bookingRepo.getBooking(id);
    }

    public Collection<Booking> getAllBookings() {
        return bookingRepo.getAllBookings();
    }

}
