package ui;

import entities.Customer;
import exception.IllRoomIdArgument;
import exception.NullCollection;
import service.HotelService;

import java.util.Scanner;

public class UserMenu {

    private final HotelService service;

    public UserMenu(HotelService service) {
        this.service = service;
    }

    public static void printUserUI() {
        System.out.print("""
                                
                ********************************************
                User Menu
                --------------------------------------------
                1. Customer Register
                2. Account Register
                3. See all Rooms
                4. Reserve Room
                5. Cancel Reservation
                6. See my Bookings
                7. See my All Bookings
                8. Back to Main Menu
                --------------------------------------------
                Please select a number for the menu option:
                                
                """);
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printUserUI();
            System.out.print("Enter your choice: ");
            String line = scanner.nextLine();

            if (line.length() == 1) {
                char choice = line.charAt(0);
                switch (choice) {
                    case '1':
                        newCustomer();
                        break;
                    case '2':
                        newAccount();
                        break;
                    case '3':
                        allRoom();
                        break;
                    case '4':
                        reserveRoom();
                        break;
                    case '5':
                        cancelReservation();
                        break;
                    case '6':
                        myReservation();
                        break;
                    case '7':
                        myAllReservation(null);
                        break;
                    case '8':
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Error: Invalid action\n");
            }
        }
    }

    public void newCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        Customer customer = service.registerCustomer(name);
        if (customer != null) {
            System.out.println("Customer successfully registered.");
            System.out.println("=================================");
            System.out.println("Your Customer ID is: " + customer.getId());
        } else {
            System.out.println("Error: Customer could not be registered.");
        }
    }

    public void newAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID: ");
        var id = scanner.nextLine();
        var account = service.registerAccount(id);
        if (account != null) {
            System.out.println("Account successfully registered.");
            System.out.println("=================================");
            System.out.println("Your Account ID is: " + account.getAccountId());
        } else {
            System.out.println("Error: Account could not be registered.");
        }
    }

    public void reserveRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        System.out.print("Enter account id: ");
        String accountId = scanner.nextLine();
        try {
            if (service.reserve(service.findByNumber(roomNumber), service.getAccount(accountId))) {
                System.out.println("Reserved room " + roomNumber + " successfully.");
                var bookingId = service.getBookingId(roomNumber);
                System.out.println("Booking ID: " + bookingId);
            } else {
                System.out.println("Room number " + roomNumber + " is not available");
            }
        } catch (IllRoomIdArgument e) {
            System.out.println("Please enter correct room number");
        } catch (NullPointerException e) {
            System.out.println("Please enter correct account id");
        }
    }

    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account id: ");
        String accountId = scanner.nextLine();
        System.out.println("\n====================");
        System.out.println("All your reservation");
        myAllReservation(accountId);
        System.out.println();
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        try {
            if (service.cancelReservation(roomNumber, accountId)) {
                System.out.println("Reservation " + roomNumber + " has been canceled.");
            } else {
                System.out.println("No reservation found.");
            }
        } catch (IllRoomIdArgument e) {
            System.out.println("No " + roomNumber + " room booked");
        } catch (NullPointerException e) {
            System.out.print("Invalid account id");
        }
    }

    public void allRoom() {
        var rooms = service.getAllRooms();
        if (!rooms.isEmpty()) {
            for (var room : rooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("No rooms remain.");
        }
    }

    public void myReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Bookings ID: ");
        String id = scanner.nextLine();
        var booking = service.getBooking(id);
        if (booking != null) {
            System.out.println(booking);
        } else {
            System.out.println("Booking could not be found.");
        }
    }

    public void myAllReservation(String accountId) {
        while (true) {
            try {
                if (accountId == null) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Enter account id (enter -1 for exit): ");
                    accountId = scanner.nextLine();
                    if (accountId.equals("-1")) {
                        mainMenu();
                        break;
                    }
                }
                var bookings = service.getAllBookingsOwnedByAccount(accountId);
                if (bookings.isEmpty()) {
                    System.out.println("Booking could not be found.");
                } else {
                    for (var booking : bookings) {
                        System.out.println(booking);
                    }
                }
                break;
            } catch (NullPointerException e) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("account id " + accountId + " doesn't exist, please enter correct account id");
                System.out.print("Enter account id (enter -1 for exit): ");
                accountId = scanner.nextLine();
                if (accountId.equals("-1")) {
                    mainMenu();
                    break;
                }
            } catch (NullCollection e) {
                System.out.println("No booking reserved by " + accountId);
            }
        }
    }
}
