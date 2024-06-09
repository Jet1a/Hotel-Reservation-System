package repository.memory;

import entities.Room;
import repository.RoomRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RoomMemoryRepository implements RoomRepository {
    private final Map<String, Room> rooms;

    public RoomMemoryRepository() {
        rooms = new HashMap<>();
    }

    @Override
    public boolean create(Room room) {
        if (room == null) return false;
        var number = room.getRoomId();
        if (rooms.containsKey(number)) return false;
        rooms.put(number, room);
        return true;
    }

    @Override
    public boolean update(Room room) {
        if (room == null) return false;
        var number = room.getRoomId();
        if (!rooms.containsKey(number)) return false;
        rooms.put(number, room);
        return true;
    }

    @Override
    public boolean delete(String number) {
        if (number == null) return false;
        if (!rooms.containsKey(number)) return false;
        rooms.remove(number);
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

    @Override
    public Stream<Room> getAvailableRooms() {
        return rooms.values().stream().filter(room -> !room.isAvailable());
    }
}
