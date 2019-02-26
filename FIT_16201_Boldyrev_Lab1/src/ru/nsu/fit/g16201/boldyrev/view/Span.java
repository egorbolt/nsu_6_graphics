package ru.nsu.fit.g16201.boldyrev.view;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    public static Span getSpan(BufferedImage image, int x, int y, int oldColor) {
        int spanLeft = x;
        int spanRight = x;

        while (image.getRGB(spanLeft - 1, y) == oldColor) {
            spanLeft--;
        }

        while(image.getRGB(spanRight + 1, y) == oldColor) {
            spanRight++;
        }

        return new Span(y, spanRight, spanLeft);
    }

    public int getSpanLength() {
        return this.getRightBorder() - this.getLeftBorder() + 1;
    }
}
