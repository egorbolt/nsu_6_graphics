package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.image.BufferedImage;

public class BlurFilter {
    public BlurFilter() {

    }

    public BufferedImage filterBlur(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);


        int i;
        int j;
        int redResult = 0;
        int greenResult = 0;
        int blueResult = 0;
        double[][] matrix = {{0.0625, 0.125, 0.0625},
                {0.125, 0.25, 0.125,},
                {0.0625, 0.125, 0.0625}};


        for (i = 1; i < sourceHeight - 1; i++) {
            for (j = 1; j < sourceWidth - 1; j++) {
                redResult = (int) (
                        matrix[0][0] * ((source.getRGB(i - 1, j - 1) >> 16) & 255) +
                        matrix[0][1] * ((source.getRGB(i - 1, j) >> 16) & 255) +
                        matrix[0][2] * ((source.getRGB(i - 1, j + 1) >> 16) & 255) +
                        matrix[1][0] * ((source.getRGB(i, j - 1) >> 16) & 255) +
                        matrix[1][1] * ((source.getRGB(i, j) >> 16) & 255) +
                        matrix[1][2] * ((source.getRGB(i, j + 1) >> 16) & 255) +
                        matrix[2][0] * ((source.getRGB(i + 1, j - 1) >> 16) & 255) +
                        matrix[2][1] * ((source.getRGB(i + 1, j) >> 16) & 255) +
                        matrix[2][2] * ((source.getRGB(i + 1, j + 1) >> 16) & 255));

                greenResult = (int) (
                        matrix[0][0] * ((source.getRGB(i - 1, j - 1) >> 8) & 255) +
                                matrix[0][1] * ((source.getRGB(i - 1, j) >> 8) & 255) +
                                matrix[0][2] * ((source.getRGB(i - 1, j + 1) >> 8) & 255) +
                                matrix[1][0] * ((source.getRGB(i, j - 1) >> 8) & 255) +
                                matrix[1][1] * ((source.getRGB(i, j) >> 8) & 255) +
                                matrix[1][2] * ((source.getRGB(i, j + 1) >> 8) & 255) +
                                matrix[2][0] * ((source.getRGB(i + 1, j - 1) >> 8) & 255) +
                                matrix[2][1] * ((source.getRGB(i + 1, j) >> 8) & 255) +
                                matrix[2][2] * ((source.getRGB(i + 1, j + 1) >> 8) & 255));
                blueResult = (int) (
                        matrix[0][0] * ((source.getRGB(i - 1, j - 1)) & 255) +
                                matrix[0][1] * ((source.getRGB(i - 1, j)) & 255) +
                                matrix[0][2] * ((source.getRGB(i - 1, j + 1)) & 255) +
                                matrix[1][0] * ((source.getRGB(i, j - 1)) & 255) +
                                matrix[1][1] * ((source.getRGB(i, j)) & 255) +
                                matrix[1][2] * ((source.getRGB(i, j + 1)) & 255) +
                                matrix[2][0] * ((source.getRGB(i + 1, j - 1)) & 255) +
                                matrix[2][1] * ((source.getRGB(i + 1, j)) & 255) +
                                matrix[2][2] * ((source.getRGB(i + 1, j + 1)) & 255));

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


}
