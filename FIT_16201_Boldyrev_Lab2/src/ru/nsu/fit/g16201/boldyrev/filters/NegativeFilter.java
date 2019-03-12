package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.image.BufferedImage;

public class NegativeFilter {
    public NegativeFilter() {

    }

    public static BufferedImage filterNegative(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < sourceHeight; i++) {
            for (int j = 0; j < sourceWidth; j++) {
                int rgb = source.getRGB(i, j);
                int r = 255 - (rgb >> 16) & 255;
                int g = 255 - (rgb >> 8) & 255;
                int b = 255 - rgb & 255;

                int newRGB = ((r << 16) | (g << 8) | b);
                result.setRGB(i, j, newRGB);
            }
        }

//        MyImage newMyImage = new MyImage(result);

        return result;
    }
}
