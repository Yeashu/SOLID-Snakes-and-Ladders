package com.snl;

public class Player {
    public String name;
    public String colour;

    public Player(String name, String colour) {
        this.name = name;
        this.colour = colour;
    }

    @Override
    public String toString() {
        return name + "(" + colour + ")";
    }
}
