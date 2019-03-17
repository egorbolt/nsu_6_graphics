package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class WatercoloringFilter {
    public WatercoloringFilter() {

    }

    public static BufferedImage filterWatercolor(MyImage myImage) {
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
        double[][] matrix = {{-0.75, -0.75, -0.75},
                            {-0.75, 7, -0.75,},
                            {-0.75, -0.75, -0.75}};

        for (i = 1; i < sourceHeight - 1; i++) {
            for (j = 1; j < sourceWidth - 1; j++) {
                int[] r = new int[9];
                int[] g = new int[9];
                int[] b = new int[9];

                for (int m = 0; m < 3; m++) {
                    for (int n = 0; n < 3; n++) {
                        int rgb = source.getRGB(i + m - 1, j + n - 1);
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
                Color color = new Color(newRGB);
                buffer.setRGB(j, i, newRGB);
            }
        }

//        return buffer;

        for (i = 1; i < sourceHeight - 1; i++) {
            for (j = 1; j < sourceWidth - 1; j++) {
                int g = buffer.getRGB(i, j);
                int d = (buffer.getRGB(i - 1, j - 1) >> 16) & 255;
                int e = (buffer.getRGB(i - 1, j) >> 16) & 255;
                int f = (buffer.getRGB(i - 1, j + 1) >> 16) & 255;
                int z = ((buffer.getRGB(i, j - 1) >> 16) & 255);
                int t = (buffer.getRGB(i, j) >> 16) & 255;
                int k = ((buffer.getRGB(i, j + 1) >> 16) & 255);
                int o = ((buffer.getRGB(i + 1, j - 1) >> 16) & 255);
                int s = ((buffer.getRGB(i + 1, j) >> 16) & 255);
                int l = ((buffer.getRGB(i + 1, j + 1) >> 16) & 255);
                double a = matrix[0][0] * ((buffer.getRGB(i - 1, j - 1) >> 16) & 255);
                double b = matrix[0][1] * ((buffer.getRGB(i - 1, j) >> 16) & 255);
                double c = matrix[0][2] * ((buffer.getRGB(i - 1, j + 1) >> 16) & 255);

                redResult = (int) (matrix[0][0] * ((buffer.getRGB(i - 1, j - 1) >> 16) & 255) +
                        matrix[0][1] * ((buffer.getRGB(i - 1, j) >> 16) & 255) +
                        matrix[0][2] * ((buffer.getRGB(i - 1, j + 1) >> 16) & 255) +
                        matrix[1][0] * ((buffer.getRGB(i, j - 1) >> 16) & 255) +
                        matrix[1][1] * ((buffer.getRGB(i, j) >> 16) & 255) +
                        matrix[1][2] * ((buffer.getRGB(i, j + 1) >> 16) & 255) +
                        matrix[2][0] * ((buffer.getRGB(i + 1, j - 1) >> 16) & 255) +
                        matrix[2][1] * ((buffer.getRGB(i + 1, j) >> 16) & 255) +
                        matrix[2][2] * ((buffer.getRGB(i + 1, j + 1) >> 16) & 255));
                if (redResult < 0) {
                    redResult = 0;
                }
                if (redResult > 255) {
                    redResult = 255;
                }

                greenResult = (int) (matrix[0][0] * ((buffer.getRGB(i - 1, j - 1) >> 8) & 255) +
                        matrix[0][1] * ((buffer.getRGB(i - 1, j) >> 8) & 255) +
                        matrix[0][2] * ((buffer.getRGB(i - 1, j + 1) >> 8) & 255) +
                        matrix[1][0] * ((buffer.getRGB(i, j - 1) >> 8) & 255) +
                        matrix[1][1] * ((buffer.getRGB(i, j) >> 8) & 255) +
                        matrix[1][2] * ((buffer.getRGB(i, j + 1) >> 8) & 255) +
                        matrix[2][0] * ((buffer.getRGB(i + 1, j - 1) >> 8) & 255) +
                        matrix[2][1] * ((buffer.getRGB(i + 1, j) >> 8) & 255) +
                        matrix[2][2] * ((buffer.getRGB(i + 1, j + 1) >> 8) & 255));
                if (greenResult < 0) {
                    greenResult = 0;
                }
                if (greenResult > 255) {
                    greenResult = 255;
                }

                blueResult = (int) (matrix[0][0] * (buffer.getRGB(i - 1, j - 1) & 255) +
                        matrix[0][1] * (buffer.getRGB(i - 1, j) & 255) +
                        matrix[0][2] * (buffer.getRGB(i - 1, j + 1) & 255) +
                        matrix[1][0] * (buffer.getRGB(i, j - 1) & 255) +
                        matrix[1][1] * (buffer.getRGB(i, j)& 255) +
                        matrix[1][2] * (buffer.getRGB(i, j + 1) & 255) +
                        matrix[2][0] * (buffer.getRGB(i + 1, j - 1) & 255) +
                        matrix[2][1] * (buffer.getRGB(i + 1, j) & 255) +
                        matrix[2][2] * (buffer.getRGB(i + 1, j + 1) & 255));
                if (blueResult < 0) {
                    blueResult = 0;
                }
                if (blueResult > 255) {
                    blueResult = 255;
                }
            }
            int newRGB = ((redResult << 16) | (greenResult << 8) | blueResult);
            result.setRGB(i, j, newRGB);
        }
        return result;
    }
}
