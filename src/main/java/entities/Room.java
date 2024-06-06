package entities;

import error.ParameterException;

import java.io.Serializable;

public class Room implements Serializable {
    private final String roomId;
    private final RoomType roomType;
    private double price;
    private boolean isAvailable;

    public Room(String roomId, RoomType roomType, double price) {
        if (roomId == null || roomId.isEmpty() || roomType == null)
            throw new ParameterException("Room id and type are required");
        if (price <= 0) throw new ParameterException("Room price cannot be 0 or less than 0");
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = true;
    }

    public String getRoomId() {
        return roomId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public String availableString() {
        if (isAvailable) {
            return "Available";
        } else {
            return "Not Available";
        }
    }

    @Override
    public String toString() {
        return "----------- Room -----------"
                + "\nRoomId: " + roomId
                + "\nRoomType: " + roomType
                + "\nPrice: " + price
                + "\nAvailable: " + availableString()
                + "\n---------------------------";
    }
}
