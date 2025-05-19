package org.example.project3.utilities.enums;

public enum Role {
    TRAINER(1),
    CLIENT(2);

    private final int id;

    //costruttore di Role
    private Role(int id) {
        this.id = id;
    }

    //Converte un intero al ruolo corrispondente
    public static Role convertIntToRole(int id) {
        for (Role type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }
}