package repository.file;

import entities.Room;
import repository.RoomRepository;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RoomFileRepository implements RoomRepository, Serializable {
    private final String filename = "C:\\Users\\User\\Desktop\\project\\HotelBooking\\src\\main\\java\\data\\rooms.dat";
    private Map<String, Room> rooms;

    public RoomFileRepository() {
        File file = new File(filename);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                rooms = (Map<String, Room>) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            rooms = new HashMap<>();
        }
    }

    private void saveToFile() {
        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(rooms);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean create(Room room) {
        if (room == null) return false;
        var number = room.getRoomId();
        if (rooms.containsKey(number)) return false;
        rooms.put(number, room);
        saveToFile();
        return true;
    }

    @Override
    public boolean update(Room room) {
        if (room == null) return false;
        var number = room.getRoomId();
        if (!rooms.containsKey(number)) return false;
        rooms.put(number, room);
        saveToFile();
        return true;
    }

    @Override
    public boolean delete(String number) {
        if (number == null) return false;
        if (!rooms.containsKey(number)) return false;
        rooms.remove(number);
        saveToFile();
        return true;
    }

    @Override
    public Room findByNumber(String number) {
        if (number == null) return null;
        return rooms.get(number);
    }

    @Override
    public Collection<Room> getAllRooms() {
        return rooms.values();
    }
}
