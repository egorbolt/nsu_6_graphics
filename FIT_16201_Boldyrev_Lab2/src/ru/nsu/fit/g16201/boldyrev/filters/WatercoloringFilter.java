package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class WatercoloringFilter {
    public WatercoloringFilter() {

    }

    public BufferedImage filterWatercolor(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        BufferedImage buffer = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);

        int i;
        int j;
        int redResult = 0;
        int greenResult = 0;
        int blueResult = 0;
        int sourcePixelX;
        int sourcePixelY;
        double[][] matrix = {{-0.75, -0.75, -0.75},
                            {-0.75, 7, -0.75,},
                            {-0.75, -0.75, -0.75}};

        for (i = 0; i < sourceHeight; i++) {
            for (j = 0; j < sourceWidth; j++) {
                int[] r = new int[9];
                int[] g = new int[9];
                int[] b = new int[9];

                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 3; n++) {
                        if (i + m - 1 < 0) {
                            sourcePixelX = 0;
                        }
                        else if (i + m - 1 > sourceWidth - 1) {
                            sourcePixelX = 0;
                        }
                        else {
                            sourcePixelX = i + m - 1;
                        }
                        if (j + n - 1 < 0) {
                            sourcePixelY = 0;
                        }
                        else if (j + n - 1 > sourceHeight - 1) {
                            sourcePixelY = 0;
                        }
                        else {
                            sourcePixelY = j + n - 1;
                        }

                        int rgb = source.getRGB(sourcePixelX, sourcePixelY);
                        int red = (rgb >> 16) & 255;
                        int green = (rgb >> 8) & 255;
                        int blue = rgb & 255;

                        r[3 * m + n] = red;
                        g[3 * m + n] = green;
                        b[3 * m + n] = blue;
                    }
                }

                Arrays.sort(r);
                Arrays.sort(g);
                Arrays.sort(b);

                int newRGB = ((r[5] << 16) | (g[5] << 8) | b[5]);
                buffer.setRGB(i, j, newRGB);
            }
        }

        for (i = 1; i < sourceHeight - 1; i++) {
            for (j = 1; j < sourceWidth - 1; j++) {
                redResult = (int) (matrix[0][0] * ((buffer.getRGB(i - 1, j - 1) >> 16) & 255) +
                        matrix[0][1] * ((buffer.getRGB(i - 1, j) >> 16) & 255) +
                        matrix[0][2] * ((buffer.getRGB(i - 1, j + 1) >> 16) & 255) +
                        matrix[1][0] * ((buffer.getRGB(i, j - 1) >> 16) & 255) +
                        matrix[1][1] * ((buffer.getRGB(i, j) >> 16) & 255) +
                        matrix[1][2] * ((buffer.getRGB(i, j + 1) >> 16) & 255) +
                        matrix[2][0] * ((buffer.getRGB(i + 1, j - 1) >> 16) & 255) +
                        matrix[2][1] * ((buffer.getRGB(i + 1, j) >> 16) & 255) +
                        matrix[2][2] * ((buffer.getRGB(i + 1, j + 1) >> 16) & 255));
                redResult = normalizeColorPart(redResult);

                greenResult = (int) (matrix[0][0] * ((buffer.getRGB(i - 1, j - 1) >> 8) & 255) +
                        matrix[0][1] * ((buffer.getRGB(i - 1, j) >> 8) & 255) +
                        matrix[0][2] * ((buffer.getRGB(i - 1, j + 1) >> 8) & 255) +
                        matrix[1][0] * ((buffer.getRGB(i, j - 1) >> 8) & 255) +
                        matrix[1][1] * ((buffer.getRGB(i, j) >> 8) & 255) +
                        matrix[1][2] * ((buffer.getRGB(i, j + 1) >> 8) & 255) +
                        matrix[2][0] * ((buffer.getRGB(i + 1, j - 1) >> 8) & 255) +
                        matrix[2][1] * ((buffer.getRGB(i + 1, j) >> 8) & 255) +
                        matrix[2][2] * ((buffer.getRGB(i + 1, j + 1) >> 8) & 255));
                greenResult = normalizeColorPart(greenResult);

                blueResult = (int) (matrix[0][0] * (buffer.getRGB(i - 1, j - 1) & 255) +
                        matrix[0][1] * (buffer.getRGB(i - 1, j) & 255) +
                        matrix[0][2] * (buffer.getRGB(i - 1, j + 1) & 255) +
                        matrix[1][0] * (buffer.getRGB(i, j - 1) & 255) +
                        matrix[1][1] * (buffer.getRGB(i, j)& 255) +
                        matrix[1][2] * (buffer.getRGB(i, j + 1) & 255) +
                        matrix[2][0] * (buffer.getRGB(i + 1, j - 1) & 255) +
                        matrix[2][1] * (buffer.getRGB(i + 1, j) & 255) +
                        matrix[2][2] * (buffer.getRGB(i + 1, j + 1) & 255));
                blueResult = normalizeColorPart(blueResult);

                int newRGB = ((redResult << 16) | (greenResult << 8) | blueResult);
                result.setRGB(i, j, newRGB);
            }
        }

        for (i = 1; i < sourceWidth; i++) {
            result.setRGB(i, 0, result.getRGB(i, 1));
            result.setRGB(0, i, result.getRGB(1, i));
        }
        result.setRGB(0, 0, result.getRGB(1, 1));
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
