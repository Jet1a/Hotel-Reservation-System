package entities;

public class Booking {
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
        return "Booking{" +
                "account=" + account +
                ", room=" + room +
                '}';
    }
}
