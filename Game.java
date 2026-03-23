package com.snl;

import java.util.*;

public class Game {
    public Queue<Player> players;
    public Board b;
    public GameMode gameMode;
    public Dice dice;

    public Game(int boardSize, List<Player> playerList, GameMode gameMode) {
        this.b = new Board(boardSize);
        this.gameMode = gameMode;
        this.dice = new Dice();
        this.players = new LinkedList<>(playerList);

        for (Player p : playerList) {
            b.mp.put(p, new PlayerCoordinate(0));
        }

        int n = boardSize;
        b.snakes = gameMode.getSnakes(n, n);
        b.ladders = gameMode.getLadders(n, n);

        System.out.println("Board: " + n + "x" + n + " (" + (n*n) + " cells)");
        System.out.println("Snakes: " + b.snakes.size() + ", Ladders: " + b.ladders.size());
    }

    public void start() {
        System.out.println("\n=== Game Start ===");
        while (!gameMode.hasEnded(b)) {
            makeTurn();
        }

        for (Map.Entry<Player, PlayerCoordinate> entry : b.mp.entrySet()) {
            if (gameMode.hasWon(entry.getValue(), b)) {
                System.out.println("\n=== " + entry.getKey().name + " wins! ===");
            }
        }
    }

    public void makeTurn() {
        Player current = players.poll();
        PlayerCoordinate pc = b.getPlayerCoordinate(current);

        if (gameMode.hasWon(pc, b)) {
            return;
        }

        System.out.println(current.name + " at " + pc.position + ":");
        gameMode.makeTurn(b, pc, dice);

        if (gameMode.hasWon(pc, b)) {
            System.out.println("  " + current.name + " reached " + pc.position + " — wins!");
        } else {
            players.offer(current);
        }
    }

    public static void main(String[] args) {
        List<Player> playerList = Arrays.asList(
            new Player("Alice", "red"),
            new Player("Bob", "blue"),
            new Player("Carol", "green")
        );

        System.out.println("--- Easy Mode ---");
        Game easyGame = new Game(10, playerList, new EasyMode());
        easyGame.start();

        System.out.println("\n--- Hard Mode ---");
        Game hardGame = new Game(10, playerList, new HardMode());
        hardGame.start();
    }
}
