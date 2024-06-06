package entities;

import exception.ParameterException;

import java.io.Serializable;

public class Booking implements Serializable {
    private final Account account;
    private final Room room;

    public Booking(Account account, Room room) {
        if (account == null || room == null) throw new ParameterException("Account and room are null");
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
        return "\n+++++++++ Bookings +++++++++++"
                + "\n" + this.account
                + "\n" + this.room
                + "\n+++++++++++++++++++++++++++++++";
    }
}
