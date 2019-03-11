package ru.nsu.fit.g16201.boldyrev.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Stack;

public class DrawTools {
    public static void drawBresenham(Graphics2D g, int x0, int y0, int x1, int y1) {
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

        if (dx < 0) {
            dx = -dx;
        }
        if (dy < 0) {
            dy = -dy;
        }

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

    public static Point[] drawHexagon(Graphics2D g, int x0, int y0, int k, int thickness) {
        int semichord = (int)(k * Math.sqrt(3) / 2);
        int x1 = x0 + semichord;
        int y1 = y0 - k / 2;
        int x2 = x1 + semichord;
        int y2 = y0;
        int x3 = x2;
        int y3 = y2 + k;
        int x4 = x3 - semichord;
        int y4 = y3 + k / 2;
        int x5 = x4 - semichord;
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
            g.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawLine(x0, y0, x1, y1);
            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x2, y2, x3, y3);
            g.drawLine(x3, y3, x4, y4);
            g.drawLine(x4, y4, x5, y5);
            g.drawLine(x5, y5, x0, y0);
            g.setStroke(new BasicStroke(1));
        }

        Point[] p = new Point[3];
        p[0] = new Point(x2, y2);
        p[1] = new Point(x4, y4);
        p[2] = new Point(x1, y1 + k);

        return p;
    }

    public static void drawField(Graphics2D g, int n, int m, int k, int thickness, HashMap<Point, Point> centers,
                    HashMap<Point, Point> pixels) {
        int xInit = 3;
        int yInit = k / 2 + 3;
        int xStart = xInit;
        int yStart = yInit;
        int xStartOdd = 0;
        int yStartOdd = 0;
        Point[] p;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                p = drawHexagon(g, xStart, yStart, k, thickness);
                centers.put(p[2], new Point(i, j));
                pixels.put(new Point(i, j), p[2]);
                if (i % 2 == 0 && j == 0) {
                    xStartOdd = (int) p[1].getX();
                    yStartOdd = (int) p[1].getY();
                    xInit = xStart;
                    yInit = yStart;
                }
                xStart = (int) p[0].getX();
                if (i % 2 != 0 && j == m - 2) {
                    j++;
                }
            }
            if (i % 2 == 0) {
                xStart = xStartOdd;
                yStart = yStartOdd;
            }
            else {
                xStart = xInit;
                yStart = yInit + 3 * k;
                if (k % 2 != 0) {
                    yStart -= 1;
                }
            }

        }
    }

    public static void spanColoring(BufferedImage image, int impactsColor, int oldColor, int newColor, int x, int y) {
        Stack<Span> stack = new Stack<>();
        Span nextSpan;
        int spanLength;
        Span s;

        if (image.getRGB(x, y) == oldColor) {
            s = Span.getSpan(image, x, y, oldColor, impactsColor);
            stack.push(s);
        }

        while (!stack.empty()) {
            s = stack.pop();
            int seedY = s.getSeed();
            for (int i = s.getLeftBorder(); i <= s.getRightBorder(); i++) {
                image.setRGB(i, seedY, newColor);
                if (image.getRGB(i, seedY - 1) == oldColor || image.getRGB(i, seedY - 1) == impactsColor) {
                    nextSpan = Span.getSpan(image, i, seedY - 1, oldColor, impactsColor);
                    spanLength = nextSpan.getSpanLength();
                    for (int j = 0; j < spanLength - 1; j++) {
                        if (i <= s.getRightBorder()) {
                            image.setRGB(i, seedY, newColor);
                            i++;
                        }
                    }
                    stack.push(nextSpan);
                }
            }
            for (int i = s.getLeftBorder(); i <= s.getRightBorder(); i++) {
                if (image.getRGB(i, seedY + 1) == oldColor) {
                    nextSpan = Span.getSpan(image, i, seedY + 1, oldColor, impactsColor);
                    spanLength = nextSpan.getSpanLength();
                    i += spanLength;
                    stack.push(nextSpan);
                }
            }
        }
    }

    public static Point getCellbyCoord(BufferedImage image, int borderColor, int x, int y) {
        int x1;
        int x2;
        int y1;
        int y2;
        int xres;
        int yres;

        x1 = x;
        while (image.getRGB(x1, y) != borderColor) {
            x1--;
        }
        x2 = x;
        while (image.getRGB(x2, y) != borderColor) {
            x2++;
        }
        xres = (x1 + x2) / 2;
        y1 = y;
        while (image.getRGB(x, y1) != borderColor) {
            y1--;
        }
        y2 = y;
        while (image.getRGB(x, y2) != borderColor) {
            y2++;
        }
        yres = (y1 + y2) / 2;

        return new Point(xres, yres);
    }
}
