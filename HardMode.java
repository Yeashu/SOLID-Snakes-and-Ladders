package com.snl;

import java.util.*;

public class HardMode extends DefaultMode {
    private final Map<Player, Integer> consecutiveSixes = new HashMap<>();

    @Override
    public void makeTurn(Board b, PlayerCoordinate pc, Dice d) {
        Player currentPlayer = null;
        for (Map.Entry<Player, PlayerCoordinate> entry : b.mp.entrySet()) {
            if (entry.getValue() == pc) {
                currentPlayer = entry.getKey();
                break;
            }
        }

        int roll = d.roll();
        if (roll == 6) {
            int streak = consecutiveSixes.getOrDefault(currentPlayer, 0) + 1;
            consecutiveSixes.put(currentPlayer, streak);
            if (streak >= 3) {
                System.out.println("  Rolled " + roll + " (3 sixes in a row!) — turn forfeited");
                consecutiveSixes.put(currentPlayer, 0);
                return;
            }
        } else {
            consecutiveSixes.put(currentPlayer, 0);
        }

        int maxPos = b.size * b.size;
        int newPos = pc.position + roll;

        if (newPos > maxPos) {
            System.out.println("  Rolled " + roll + " — overshoots, stays at " + pc.position);
            return;
        }

        pc.position = newPos;
        System.out.println("  Rolled " + roll + " → position " + pc.position);

        for (Snake snake : b.snakes) {
            if (snake.head == pc.position) {
                System.out.println("  Snake! " + snake.head + " → " + snake.tail);
                pc.position = snake.tail;
                return;
            }
        }

        for (Ladder ladder : b.ladders) {
            if (ladder.bottom == pc.position) {
                System.out.println("  Ladder! " + ladder.bottom + " → " + ladder.top);
                pc.position = ladder.top;
                return;
            }
        }
    }
}
