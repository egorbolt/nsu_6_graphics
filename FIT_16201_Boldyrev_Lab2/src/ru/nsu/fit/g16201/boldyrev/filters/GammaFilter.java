package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.image.BufferedImage;

public class GammaFilter {
    private double coefficient;

    public GammaFilter(double coefficient) {
        this.coefficient = 1 / (coefficient / 10);
    }

    public BufferedImage filterGamma(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < sourceWidth; i++) {
            for (int j = 0; j < sourceHeight; j++) {
                int rgb = source.getRGB(i, j);
                int r = (rgb >> 16) & 255;
                int g = (rgb >> 8) & 255;
                int b = rgb & 255;

                r = (int) (Math.pow((double) r / (double) 255, coefficient) * 255);
                g = (int) (Math.pow((double) g / (double) 255, coefficient) * 255);
                b = (int) (Math.pow((double) b / (double) 255, coefficient) * 255);

                r = normalizeColorPart(r);
                g = normalizeColorPart(g);
                b = normalizeColorPart(b);

                int newRGB = ((r << 16) | (g << 8) | b);
                result.setRGB(i, j, newRGB);
            }
        }

        return result;
    }

    private int normalizeColorPart(int a) {
        if (a > 255) {
            a = 255;
        }
        if (a < 0) {
            a = 0;
        }

        return a;
    }
}
