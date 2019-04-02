package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.image.BufferedImage;

public class SobelFilter {
    private int coefficient;

    public SobelFilter(int coefficient) {
        this.coefficient = coefficient;
    }

    public BufferedImage filterSobel(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);

        for (int i = 1; i < sourceHeight - 1; i++) {
            for (int j = 1; j < sourceWidth - 1; j++) {
                int a = source.getRGB(i - 1, j - 1);
                int b = source.getRGB(i, j - 1);
                int c = source.getRGB(i, j - 1);
                int d = source.getRGB(i - 1, j);
                int f = source.getRGB(i + 1, j);
                int g = source.getRGB(i - 1, j + 1);
                int h = source.getRGB(i, j + 1);
                int k = source.getRGB(i + 1, j + 1);

                int rx = (((c >> 16) & 255) +
                        (2 * ((f >> 16) & 255)) +
                        ((k >> 16) & 255)) -
                        (((a >> 16) & 255) +
                        (2 * ((d >> 16) & 255)) +
                        ((g >> 16) & 255));

                int ry = (((g >> 16) & 255) +
                        (2 * ((h >> 16) & 255)) +
                        ((k >> 16) & 255)) -
                        (((a >> 16) & 255) +
                        (2 * ((b >> 16) & 255)) +
                        ((c >> 16) & 255));

                int gx = (((c >> 8) & 255) +
                        (2 * ((f >> 8) & 255)) +
                        ((k >> 8) & 255)) -
                        (((a >> 8) & 255) +
                                (2 * ((d >> 8) & 255)) +
                                ((g >> 8) & 255));

                int gy = (((g >> 8) & 255) +
                        (2 * ((h >> 8) & 255)) +
                        ((k >> 8) & 255)) -
                        (((a >> 8) & 255) +
                        (2 * ((b >> 8) & 255)) +
                        ((c >> 8) & 255));

                int bx = ((c & 255) +
                        (2 * (f & 255)) +
                        (k & 255)) -
                        ((a & 255) +
                        (2 * (d & 255)) +
                        (g & 255));

                int by = ((g & 255) +
                        (2 * (h & 255)) +
                        (k & 255)) -
                        ((a & 255) +
                        (2 * (b & 255)) +
                        (c & 255));

                int red = (int) Math.sqrt(rx * rx + ry * ry);
                int green = (int) Math.sqrt(gx * gx + gy * gy);
                int blue = (int) Math.sqrt(bx * bx + by * by);

                red = normalizeColorPart(red);
                green = normalizeColorPart(green);
                blue = normalizeColorPart(blue);

                int newRGB = ((red << 16) | (green << 8) | blue);
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
