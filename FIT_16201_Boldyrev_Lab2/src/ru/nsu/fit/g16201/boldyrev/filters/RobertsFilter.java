package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;
import ru.nsu.fit.g16201.boldyrev.view.panels.MyPanel;

import java.awt.image.BufferedImage;

public class RobertsFilter {
    private int coefficient;

    public RobertsFilter(int coefficient) {
        this.coefficient = coefficient;
    }

    public BufferedImage filterRobert(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < sourceHeight - 1; i++) {
            for (int j = 1; j < sourceWidth - 1; j++) {
                int rgb1 = source.getRGB(i, j);
                int rgb2 = source.getRGB(i + 1, j + 1);
                int rgb3 = source.getRGB(i, j + 1);
                int rgb4 = source.getRGB(i - 1, j);

                int r1 = (rgb1 >> 16) & 255;
                int r2 = (rgb2 >> 16) & 255;
                int r3 = (rgb3 >> 16) & 255;
                int r4 = (rgb4 >> 16) & 255;

                int g1 = (rgb1 >> 8) & 255;
                int g2 = (rgb2 >> 8) & 255;
                int g3 = (rgb3 >> 8) & 255;
                int g4 = (rgb4 >> 8) & 255;

                int b1 = rgb1 & 255;
                int b2 = rgb2 & 255;
                int b3 = rgb3 & 255;
                int b4 = rgb4 & 255;

                int rf1 = r1 - r2;
                int rf2 = r3 - r4;
                int bf1 = b1 - b2;
                int bf2 = b3 - b4;
                int gf1 = g1 - g2;
                int gf2 = g3 - g4;

                int r = (int) Math.sqrt(rf1 * rf1 + rf2 * rf2);
                int g = (int) Math.sqrt(gf1 * gf1 + gf2 * gf2);
                int b = (int) Math.sqrt(bf1 * bf1 + bf2 * bf2);

                r = normalizeColorPart(r);
                g = normalizeColorPart(g);
                b = normalizeColorPart(b);

                int newRGB = ((r << 16) | (g << 8) | b);
                result.setRGB(i, j, newRGB);
            }
        }

        for (int i = 1; i < sourceWidth; i++) {
            result.setRGB(i, 0, result.getRGB(i, 1));
            result.setRGB(0, i, result.getRGB(1, i));
        }
        result.setRGB(0, 0, result.getRGB(1, 1));
        return result;
    }

    private int normalizeColorPart(int a) {
        if (a > coefficient) {
            a = 255;
        }
        if (a < coefficient) {
            a = 0;
        }

        return a;
    }
}
