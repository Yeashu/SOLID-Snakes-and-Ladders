package com.snl;

import java.util.*;

public class Board {
    public Map<Player, PlayerCoordinate> mp;
    public List<Snake> snakes;
    public List<Ladder> ladders;
    public int size;

    public Board(int size) {
        this.size = size;
        this.mp = new HashMap<>();
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
    }

    public PlayerCoordinate getPlayerCoordinate(Player player) {
        return mp.get(player);
    }
}
