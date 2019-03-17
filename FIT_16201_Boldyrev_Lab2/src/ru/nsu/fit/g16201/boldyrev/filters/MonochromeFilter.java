package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.image.BufferedImage;

public class MonochromeFilter {
    public MonochromeFilter() {

    }

    public static BufferedImage filterMonochrome(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < sourceHeight; i++) {
            for (int j = 0; j < sourceWidth; j++) {
                int rgb = source.getRGB(i, j);
                int r = (rgb >> 16) & 255;
                int g = (rgb >> 8) & 255;
                int b = rgb & 255;

                int y = (int)(r * 0.299 + g * 0.587 + b * 0.114);
                int newRGB = ((y << 16) | (y << 8) | y);
                result.setRGB(i, j, newRGB);
            }
        }

        return result;
    }
}
