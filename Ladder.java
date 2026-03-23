package com.snl;

public class Ladder {
    public int bottom;
    public int top;

    public Ladder(int bottom, int top) {
        if (bottom >= top) throw new IllegalArgumentException("Ladder bottom must be < top");
        this.bottom = bottom;
        this.top = top;
    }
}
