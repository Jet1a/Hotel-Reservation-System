package ui;

import entities.Room;
import entities.RoomType;
import exception.IllRoomIdArgument;
import exception.ParameterException;
import service.HotelService;

import java.util.Scanner;

public class AdminMenu {

    private final HotelService service;

    public AdminMenu(HotelService service) {
        this.service = service;
    }

    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        if (!service.adminAuthentication()) return;
        boolean exit = false;
        while (!exit) {
            printAdminUI();
            System.out.print("Enter your choice: ");
            String line = scanner.nextLine();

            if (line.length() == 1) {
                char choice = line.charAt(0);
                switch (choice) {
                    case '1':
                        allCustomers();
                        break;
                    case '2':
                        allRooms();
                        break;
                    case '3':
                        allReservations();
                        break;
                    case '4':
                        addRoom();
                        break;
                    case '5':
                        removeRoom();
                        break;
                    case '6':
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
    }

    public void printAdminUI() {
        System.out.print("""
                                
                ********************************************
                Admin Menu
                --------------------------------------------
                1. See all Customers
                2. See all Rooms
                3. See all Reservations
                4. Add a Room
                5. Remove a Room
                6. Back to Main Menu
                --------------------------------------------
                Please select a number for the menu option:
                                
                """);
    }

    private void addRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("room number must only be number with 3 digits");
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();

        System.out.print("Enter price per night: ");
        var roomPrice = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter room type\n   1) for single bed\n   2) for double bed");
        System.out.print("Choose your choice[1,2]: ");
        try {
            var roomType = RoomType.fromString(scanner.nextLine());
            if (service.addRoom(new Room(roomNumber, roomType, roomPrice))) {
                System.out.println("Room " + roomNumber + " added successfully");
                System.out.print("Would like to add another room? Y/N: ");
                addAnotherRoom();
            } else {
                System.out.println("Room " + roomNumber + " could not be added");
            }
        } catch (NullPointerException e) {
            System.out.println("Room " + roomNumber + " could not be added");
            System.out.println("Please enter correct room number");
        } catch (ParameterException e) {
            System.out.println("Please enter correct price");
        } catch (IllegalArgumentException ee) {
            System.out.println("Please enter select room type (1,2)");
        }

    }

    private void addAnotherRoom() {
        Scanner scanner = new Scanner(System.in);

        try {
            String anotherRoom;

            anotherRoom = scanner.nextLine();

            while ((anotherRoom.charAt(0) != 'Y' && anotherRoom.charAt(0) != 'N')
                    || anotherRoom.length() != 1) {
                System.out.println("Please enter Y (Yes) or N (No)");
                anotherRoom = scanner.nextLine();
            }

            if (anotherRoom.charAt(0) == 'Y') {
                addRoom();
            } else if (anotherRoom.charAt(0) == 'N') {
                printAdminUI();
            } else {
                addAnotherRoom();
            }
        } catch (StringIndexOutOfBoundsException ex) {
            addAnotherRoom();
        }
    }

    private void removeRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("room number must only be number with 3 digits.");
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();
        try {
            if (service.removeRoom(roomNumber)) {
                System.out.println("Room " + roomNumber + " removed successfully.");
            } else {
                System.out.println("Room number " + roomNumber + " have been reserving by customer");
                System.out.println("Cannot remove room number " + roomNumber + ".");
            }
        } catch (IllRoomIdArgument | NullPointerException e) {
            System.out.println("Please enter correct room number");
        }


    }

    public void allCustomers() {
        var s = service.getAllCustomers();
        if (!s.isEmpty()) {
            for (var c : s) {
                System.out.println(c.toString());
            }
        } else {
            System.out.println("No customers found");
        }
    }

    public void allRooms() {
        var s = service.getAllRooms();
        if (!s.isEmpty()) {
            for (var room : s) {
                System.out.println(room);
            }
        } else {
            System.out.println("No rooms found.");
        }
    }

    public void allReservations() {
        var s = service.getAllBookings();
        if (!s.isEmpty()) {
            for (var booking : s) {
                System.out.println(booking);
            }
        } else {
            System.out.println("No bookings found.");
        }
    }

}
