package com.snl;

public class Snake {
    public int head;
    public int tail;

    public Snake(int head, int tail) {
        if (head <= tail) throw new IllegalArgumentException("Snake head must be > tail");
        this.head = head;
        this.tail = tail;
    }
}
