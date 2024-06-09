package repository;

import entities.Room;

import java.util.Collection;
import java.util.stream.Stream;


public interface RoomRepository {
    boolean create(Room room);

    boolean update(Room room);

    boolean delete(String number);

    Room findByNumber(String number);

    Collection<Room> getAllRooms();

    Stream<Room> getAvailableRooms();
}
