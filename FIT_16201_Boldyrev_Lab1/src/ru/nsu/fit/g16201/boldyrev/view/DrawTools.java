package ru.nsu.fit.g16201.boldyrev.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;

public class DrawTools {
    public static void drawBresenham(Graphics g, int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int directionX = Integer.compare(dx, 0);
        int directionY =  Integer.compare(dy, 0);
        int pdx;
        int pdy;
        int error;
        int es;
        int el;
        int x;
        int y;


        dx = Math.abs(dx);
        dy = Math.abs(dy);

        if (dx > dy) {
            pdx = directionX;
            pdy = 0;
            es = dy;
            el = dx;
        }
        else {
            pdx = 0;
            pdy = directionY;
            es = dx;
            el = dy;
        }

        x = x0;
        y = y0;
        error = el / 2;
        g.drawLine(x, y, x, y);

        for (int i = 0; i < el; i++) {
            error -= es;
            if (error < 0) {
                error += el;
                x += directionX;
                y += directionY;
            }
            else {
                x += pdx;
                y += pdy;
            }
            g.drawLine(x, y, x, y);
        }
    }

    public static void drawHexagon(Graphics g, int x0, int y0, int k, int thickness) {
        int x1 = x0 + (int)(k * Math.sqrt(3) / 2);
        int y1 = y0 - k / 2;
        int x2 = x1 + (int)(k * Math.sqrt(3) / 2);
        int y2 = y0;
        int x3 = x2;
        int y3 = y2 + k;
        int x4 = x3 - (int)(k * Math.sqrt(3) / 2);
        int y4 = y3 + k / 2;
        int x5 = x4 - (int)(k * Math.sqrt(3) / 2);
        int y5 = y3;

        if (thickness == 1) {
            DrawTools.drawBresenham(g, x0, y0, x1, y1);
            DrawTools.drawBresenham(g, x1, y1, x2, y2);
            DrawTools.drawBresenham(g, x2, y2, x3, y3);
            DrawTools.drawBresenham(g, x3, y3, x4, y4);
            DrawTools.drawBresenham(g, x4, y4, x5, y5);
            DrawTools.drawBresenham(g, x5, y5, x0, y0);
        }
        else {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(thickness));
            g2d.drawLine(x0, y0, x1, y1);
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y2, x3, y3);
            g2d.drawLine(x3, y3, x4, y4);
            g2d.drawLine(x4, y4, x5, y5);
            g2d.drawLine(x5, y5, x0, y0);
        }
    }

    public static void drawField(Graphics g, int n, int m, int k, int thickness) {
        int xInitEven = 3;
        int yInitEven = k / 2 + 3;
        int xInitOdd = (int)(k * Math.sqrt(3) / 2) + 3;
        int yInitOdd = k * 2 + 3;
        int xStart = xInitEven;
        int yStart = yInitEven;
        int xNext = (int)(k * Math.sqrt(3));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                drawHexagon(g, xStart, yStart, k, thickness);
                xStart += xNext;
                if (i % 2 != 0 && j == m - 2) {
                    j++;
                }
            }
            if (i % 2 == 0) {
                xStart = xInitOdd;
                yStart = yInitOdd + k * 3 * (i / 2);
            }
            else {
                xStart = xInitEven;
                yStart = yInitEven + k * 3 * (i / 2 + 1);
            }
        }
    }

    public static void spanColoring(BufferedImage image, Color borderColor, Color cellColor, int x, int y) {
        Stack<Span> stack = new Stack<>();
        int spanLeft = x;
        int spanRight = x;
        int startX = x;
        int startY = y;
        Span curSpan;
        int curSpanSeed;

        while (new Color(image.getRGB(spanLeft, startY)) != borderColor) {
            spanLeft--;
        }
        while (new Color (image.getRGB(spanRight, startY)) != borderColor) {
            spanRight++;
        }

        startX = (spanLeft + spanRight) / 2;

        stack.add(new Span(startX, spanRight, spanLeft));

        int spanY = startY - 1;
        int spanX = startX - 1;
        spanLeft = spanRight = spanX;
        while (new Color(image.getRGB(startX, spanY)) != borderColor) {
            while (new Color(image.getRGB(spanLeft, spanY)) != borderColor) {
                spanLeft--;
            }
            while (new Color(image.getRGB(spanRight, spanY)) != borderColor) {
                spanRight++;
            }

            stack.add(new Span(startX, spanRight, spanLeft));
            spanY--;
            spanLeft = spanRight = startX - 1;
        }

        spanY = startY + 1;
        spanLeft = spanRight = startX + 1;
        while (new Color(image.getRGB(startX, spanY)) != borderColor) {
            while (new Color((spanLeft, spanY)) != borderColor) {
                spanLeft--;
            }
            while (new Color(image.getRGB(spanRight, spanY)) != borderColor) {
                spanRight++;
            }

            stack.add(new Span(startX, spanRight, spanLeft));
            spanY++;
        }

        while (!stack.isEmpty()) {
            curSpan = stack.pop();
            curSpanSeed = curSpan.getSeed();
            for (int i = curSpan.getLeftBorder() + 1; i < curSpan.getRightBorder(); i++) {
                image.setRGB(curSpanSeed, i, borderColor);
            }
        }
    }
}
