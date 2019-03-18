package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.image.BufferedImage;

public class MagnifierFilter {
    public MagnifierFilter() {

    }

    public BufferedImage filterMagnify(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        int r, r1, r2, r3, r4;
        int g, g1, g2, g3, g4;
        int b, b1, b2, b3, b4;

        for (int i = 0; i < sourceHeight; i++) {
            for (int j = 0; j < sourceWidth; j++) {
                r = r1 = r2 = r3 = r4 = 0;
                g = g1 = g2 = g3 = g4 = 0;
                b = b1 = b2 = b3 = g4 = 0;

                if ((i - 1) % 2 == 0 && (j - 1) % 2 == 0) {
                    int x = sourceWidth / 4 + (i - 1) / 2;
                    int y = sourceHeight / 4 + (j - 1) / 2;

                    int rgb = source.getRGB(x, y);
                    r = (rgb >> 16) & 255;
                    g = (rgb >> 8) & 255;
                    b = rgb & 255;
                }
                else if ((i - 1) % 2 == 0) {
                    int x = sourceWidth / 4 + (i - 1) / 2;
                    int y = sourceHeight / 4 + (j - 2) / 2;

                    int rgb1 = source.getRGB(x, y);
                    int rgb2 = source.getRGB(x, y + 1);

                    r1 = (rgb1 >> 16) & 255;
                    g1 = (rgb1 >> 8) & 255;
                    b1 = rgb1 & 255;
                    r2 = (rgb2 >> 16) & 255;
                    g2 = (rgb2 >> 8) & 255;
                    b2 = rgb2 & 255;

                    r = (r1 + r2) / 2;
                    g = (g1 + g2) / 2;
                    b = (b1 + b2) / 2;
                } else if((j - 1) % 2 == 0) {
                    int x = sourceWidth / 4 + (i - 2) / 2;
                    int y = sourceHeight / 4 + (j - 1) / 2;

                    int rgb1 = source.getRGB(x, y);
                    int rgb2 = source.getRGB(x + 1, y);

                    r1 = (rgb1 >> 16) & 255;
                    g1 = (rgb1 >> 8) & 255;
                    b1 = rgb1 & 255;
                    r2 = (rgb2 >> 16) & 255;
                    g2 = (rgb2 >> 8) & 255;
                    b2 = rgb2 & 255;

                    r = (r1 + r2) / 2;
                    g = (g1 + g2) / 2;
                    b = (b1 + b2) / 2;
                } else {
                    int x = sourceWidth / 4 + (i - 2) / 2;
                    int y = sourceHeight / 4 + (j - 2) / 2;

                    int rgb1 = source.getRGB(x, y);
                    int rgb2 = source.getRGB(x + 1, y);
                    int rgb3 = source.getRGB(x, y + 1);
                    int rgb4 = source.getRGB(x + 1, y + 1);

                    r1 = (rgb1 >> 16) & 255;
                    g1 = (rgb1 >> 8) & 255;
                    b1 = rgb1 & 255;
                    r2 = (rgb2 >> 16) & 255;
                    g2 = (rgb2 >> 8) & 255;
                    b2 = rgb2 & 255;
                    r3 = (rgb3 >> 16) & 255;
                    g3 = (rgb3 >> 8) & 255;
                    b3 = rgb3 & 255;
                    r4 = (rgb4 >> 16) & 255;
                    g4 = (rgb4 >> 8) & 255;
                    b4 = rgb4 & 255;

                    r = (r1 + r2 + r3 + r4) / 4;
                    g = (g1 + g2 + g3 + g4) / 4;
                    b = (b1 + b2 + b3 + b4) / 4;
                }

                int newRGB = ((r << 16) | (g << 8) | b);
                result.setRGB(i, j, newRGB);
            }
        }

        return result;
    }
}