package com.snl;

import java.util.*;

public abstract class DefaultMode implements GameMode {

    @Override
    public boolean hasWon(PlayerCoordinate pc, Board b) {
        return pc.position == b.size * b.size;
    }

    @Override
    public boolean hasEnded(Board b) {
        long remaining = b.mp.values().stream()
                .filter(pc -> pc.position < b.size * b.size)
                .count();
        return remaining < 2;
    }

    @Override
    public void makeTurn(Board b, PlayerCoordinate pc, Dice d) {
        int roll = d.roll();
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

    protected Set<Integer> usedPositions = new HashSet<>();

    @Override
    public List<Snake> getSnakes(int bSize, int nSnakes) {
        usedPositions.clear();
        int maxPos = bSize * bSize;
        List<Snake> snakes = new ArrayList<>();
        Random rand = new Random();

        while (snakes.size() < nSnakes) {
            int head = 2 + rand.nextInt(maxPos - 2);
            int tail = 1 + rand.nextInt(head - 1);
            if (!usedPositions.contains(head) && !usedPositions.contains(tail)) {
                snakes.add(new Snake(head, tail));
                usedPositions.add(head);
                usedPositions.add(tail);
            }
        }
        return snakes;
    }

    @Override
    public List<Ladder> getLadders(int bSize, int nLadders) {
        int maxPos = bSize * bSize;
        List<Ladder> ladders = new ArrayList<>();
        Random rand = new Random();

        while (ladders.size() < nLadders) {
            int bottom = 1 + rand.nextInt(maxPos - 2);
            int top = bottom + 1 + rand.nextInt(maxPos - bottom);
            if (!usedPositions.contains(bottom) && !usedPositions.contains(top)) {
                ladders.add(new Ladder(bottom, top));
                usedPositions.add(bottom);
                usedPositions.add(top);
            }
        }
        return ladders;
    }
}
