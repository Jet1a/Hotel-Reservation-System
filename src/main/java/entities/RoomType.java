package entities;

import java.io.Serializable;

public enum RoomType implements Serializable {
    SINGLE("1"),
    DOUBLE("2");

    private final String value;

    RoomType(String value) {
        this.value = value;
    }

    public static RoomType fromString(String value) {
        for (RoomType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException();
    }
}
