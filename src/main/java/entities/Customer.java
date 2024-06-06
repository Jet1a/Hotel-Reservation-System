package entities;

import error.ParameterException;

import java.io.Serializable;

public class Customer implements Serializable {
    private final String id;
    private final String name;

    public Customer(String id, String name) {
        if (id == null || id.trim().isEmpty()) throw new ParameterException("Id is null");
        if (name == null || name.trim().isEmpty()) throw new ParameterException("Name is null");
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
