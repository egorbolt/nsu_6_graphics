package ru.nsu.fit.g16201.boldyrev.view;

public class Span {
    private int rBorder;
    private int lBorder;
    private int seed;

    public Span(int seed, int rBorder, int lBorder) {
        this.seed = seed;
        this.rBorder = rBorder;
        this.lBorder = lBorder;
    }

    public int getRightBorder() {
        return this.rBorder;
    }

    public int getLeftBorder() {
        return this.lBorder;
    }

    public int getSeed() {
        return this.seed;
    }
}
