package entities;

public class Room {
    private final String roomId;
    private final RoomType roomType;
    private double price;
    private boolean isAvailable;

    public Room(String roomId, RoomType roomType, double price) {
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

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomType=" + roomType +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
