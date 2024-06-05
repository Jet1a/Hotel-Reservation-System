package entities;

import java.io.Serializable;

public class Booking implements Serializable {
    private final Account account;
    private final Room room;

    public Booking(Account account, Room room) {
        this.account = account;
        this.room = room;
    }

    public Account getAccount() {
        return account;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "----------- Bookings -----------"
                + "\n" + this.account.toString()
                + "\n" + this.room.toString()
                + "\n---------------------------------";
    }
}
