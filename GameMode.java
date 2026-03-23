package com.snl;

import java.util.List;

public interface GameMode {
    void makeTurn(Board b, PlayerCoordinate pc, Dice d);
    List<Snake> getSnakes(int bSize, int nSnakes);
    List<Ladder> getLadders(int bSize, int nLadders);
    boolean hasWon(PlayerCoordinate pc, Board b);
    boolean hasEnded(Board b);
}
