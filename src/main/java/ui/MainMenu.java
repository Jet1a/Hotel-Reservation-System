package ui;

import entities.Customer;
import repository.memory.AccountMemoryRepository;
import repository.memory.BookingMemoryRepository;
import repository.memory.CustomerMemoryRepository;
import repository.memory.RoomMemoryRepository;
import service.HotelService;

import java.util.Scanner;

public class MainMenu {
    private final HotelService service;
    private UserMenu userMenu;
    private AdminMenu adminMenu;

    public MainMenu() {
        service = new HotelService(
                new CustomerMemoryRepository(),
                new AccountMemoryRepository(),
                new RoomMemoryRepository(),
                new BookingMemoryRepository()
        );
        userMenu = new UserMenu(service);
        adminMenu = new AdminMenu(service);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            menuUI();
            System.out.print("Enter your choice: ");
            String line = scanner.nextLine();

            if (line.length() == 1) {
                char choice = line.charAt(0);
                switch (choice) {
                    case '1':
                        userMenu.mainMenu();
                        break;
                    case '2':
                        adminMenu.mainMenu();
                        break;
                    case '3':
                        System.out.println("Exit Program . . .");
                        exit = true;
                        break;
                    default:
                        System.out.println("Unknown action\n");
                        break;
                }
            } else {
                System.out.println("Error: Invalid action\n");
            }
        }
        scanner.close();
    }

    public void menuUI() {
        System.out.print("""
                Welcome to Hotel Reservation System
                --------------------------------------------
                1. User Menu
                2. Admin Menu
                3. Exit
                --------------------------------------------
                Please select a number for the menu option:
                """);

    }

    public void printUserUI() {
        System.out.print("""
                User Menu
                --------------------------------------------
                1. Customer Register
                2. Account Register
                3. See all Rooms
                4. Reserve Room
                5. See Bookings
                6. Back to Main Menu
                --------------------------------------------
                Please select a number for the menu option:
                """);
        userUi();
    }

    public void userUi() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printUserUI();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    newCustomer();
                    break;
                case 2:
                    newAccount();
                    break;
                case 3:
                    allRoom();
                    break;
                case 4:
                    reserveRoom();
                    break;
                case 5:
                    myReservation();
                    break;
                case 6:
                    System.out.println("Exiting the program.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void newCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        Customer customer = service.registerCustomer(name);
        System.out.println("Customer successfully registered.");
        System.out.println("=================================");
        System.out.println("Your CustomerID is: " + customer.getId());
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
            System.out.println("Welcome " + account);
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

    public void allRoom() {
        var rooms = service.getAllRooms();
        if (rooms != null) {
            for (var room : rooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("No rooms remain.");
        }
    }

    public void myReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer ID: ");
        String id = scanner.nextLine();
        var booking = service.getBooking(id);
        System.out.println(booking);
    }


}

