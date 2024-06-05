package entities;

import java.io.Serializable;

public class Customer implements Serializable {
    private final String id;
    private final String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "----------- Customer -----------"
                + "\nName: " + name
                + "\nID: " + id
                + "\n------------------------------";
    }
}
