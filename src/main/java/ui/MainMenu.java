package ui;

import repository.file.AccountFileRepository;
import repository.file.BookingFileRepository;
import repository.file.CustomerFileRepository;
import repository.file.RoomFileRepository;
import repository.memory.AccountMemoryRepository;
import repository.memory.BookingMemoryRepository;
import repository.memory.CustomerMemoryRepository;
import repository.memory.RoomMemoryRepository;
import service.HotelService;

import java.util.Scanner;

public class MainMenu {
    private final HotelService service;
    private final UserMenu userMenu;
    private final AdminMenu adminMenu;

    public MainMenu(boolean fromFile) {
        if (fromFile) {
            service = new HotelService(
                    new CustomerFileRepository(),
                    new AccountFileRepository(),
                    new RoomFileRepository(),
                    new BookingFileRepository());
        } else {
            service = new HotelService(
                    new CustomerMemoryRepository(),
                    new AccountMemoryRepository(),
                    new RoomMemoryRepository(),
                    new BookingMemoryRepository()
            );
        }
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
                                
                ********************************************
                Welcome to Hotel Reservation System
                --------------------------------------------
                1. User Menu
                2. Admin Menu
                3. Exit
                --------------------------------------------
                Please select a number for the menu option:
                                
                """);
    }
}

