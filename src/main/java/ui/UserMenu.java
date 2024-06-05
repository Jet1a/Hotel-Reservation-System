package ui;

import entities.Customer;
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
                6. See all Bookings
                7. Back to Main Menu
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
                        allReservation();
                        break;
                    case '7':
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
            System.out.println("Your CustomerID is: " + customer.getId());
        }else{
            System.out.println("Error: Customer could not be registered.");
        }
    }

    public void newAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID: ");
        var id = scanner.nextLine();
        var account = service.registerAccount(id);
        if (account == null) {
            System.out.println("Account not registered.");
        } else {
            System.out.println("Account successfully registered.");
        }
    }

    public void reserveRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        System.out.print("Enter account id: ");
        String accountId = scanner.nextLine();
        if (service.reserve(service.findByNumber(roomNumber), service.getAccount(accountId))) {
            System.out.println("Reserved room " + roomNumber + " successfully.");
        } else {
            System.out.println("Reserved room " + roomNumber + " failed.");
        }
    }
    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        if (service.cancelReservation(roomNumber)) {
            System.out.println("Reservation has been cancelled.");
        }else{
            System.out.println("Reservation has not been cancelled.");
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
        System.out.println(booking);
    }

    public void allReservation() {
        var s = service.getAllBookings();
        if (!s.isEmpty()) {
            for (var booking : s) {
                System.out.println(booking);
            }
        } else {
            System.out.println("No bookings remain.");
        }
    }
}
