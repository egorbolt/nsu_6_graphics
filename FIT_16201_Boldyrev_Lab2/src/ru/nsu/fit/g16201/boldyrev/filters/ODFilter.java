package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.image.BufferedImage;

public class ODFilter {
    private int redShift;
    private int greenShift;
    private int blueShift;

    public ODFilter(int redShift, int greenShift, int blueShift) {
        this.redShift = redShift;
        this.greenShift = greenShift;
        this.blueShift = blueShift;
    }

    public BufferedImage filterOD(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);

        int[][] matrix = {{0, 32, 8, 40, 2, 34, 10, 42},
                {48, 16, 56, 24, 50, 18, 58, 26},
                {12, 44, 4, 36, 14, 46, 6, 38},
                {60, 28, 52, 20, 62, 30, 54, 22},
                {3, 35, 11, 43, 1, 33, 9, 41},
                {51, 19, 59, 27, 49, 17, 57, 25},
                {15, 47, 7, 39, 13, 45, 5, 37},
                {63, 31, 55, 23, 61, 29, 53, 21}};

        int[][] matrix1616 = new int[16][16];

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                matrix1616[i][j] = matrix[i][j] * 4;
            }
        }
        for (int i = 8; i < 16; i++) {
            for (int j = 0; j < 8; j++) {
                matrix1616[i][j] = matrix[i % 8][j] * 4 + 2;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 8; j < 16; j++) {
                matrix1616[i][j] = matrix[i][j % 8] * 4 + 3;
            }
        }
        for (int i = 8; i < 16; i++) {
            for (int j = 8; j < 16; j++) {
                matrix1616[i][j] = matrix[i % 8][j % 8] * 4 + 1;
            }
        }

        for (int i = 0; i < sourceHeight; i++) {
            for (int j = 0; j < sourceWidth; j++) {
                int rgb = source.getRGB(i, j);
                int b = rgb & 255;
                int g = (rgb >> 8) & 255;
                int r = (rgb >> 16) & 255;
                int newRed, newGreen, newBlue;

                if (r > matrix1616[i % 16][j % 16]) {
                    newRed = 255;
                }
                else {
                    newRed = 0;
                }
                if (g > matrix1616[i % 16][j % 16]) {
                    newGreen = 255;
                }
                else {
                    newGreen = 0;
                }
                if (b > matrix1616[i % 16][j % 16]) {
                    newBlue = 255;
                }
                else {
                    newBlue = 0;
                }

                int newRGB = ((newRed << 16) | (newGreen << 8) | newBlue);
                result.setRGB(i, j, newRGB);

            }
        }

//        int newRed = 256 / redShift;
//        int newGreen = 256 / greenShift;
//        int newBlue = 256 / blueShift;
//
//        int[] reducedRed = new int[256];
//        for (int i = 0; i < 256; i++) {
//            reducedRed[i] = ((i / (256 / redShift)) * (255 / (redShift - 1)));
//            if (reducedRed[i] > 255) {
//                reducedRed[i] = 255;
//            }
//        }
//        int[] reducedGreen = new int[256];
//        for (int i = 0; i < 256; i++) {
//            reducedGreen[i] = ((i / (256 / greenShift)) * (255 / (greenShift - 1)));
//            if (reducedGreen[i] > 255) {
//                reducedGreen[i] = 255;
//            }
//        }
//        int[] reducedBlue = new int[256];
//        for (int i = 0; i < 256; i++) {
//            reducedBlue[i] = ((i / (256 / blueShift)) * (255 / (blueShift - 1)));
//            if (reducedBlue[i] > 255) {
//                reducedBlue[i] = 255;
//            }
//        }
//
//        for (int i = 0; i < sourceHeight; i++) {
//            for (int j = 0; j < sourceWidth; j++) {
//                int rgb = source.getRGB(i, j);
//                int b = rgb & 255;
//                int g = (rgb >> 8) & 255;
//                int r = (rgb >> 16) & 255;
//
////                int newR = reducedRed[r +  (int) ((256 / 2) * (matrix[i % 8][j % 8] - 0.5))];
////                int newG = reducedGreen[g +  (int) ((256 / 2) * (matrix[i % 8][j % 8] - 0.5))];
////                int newB = reducedBlue[b +  (int) ((256 / 2) * (matrix[i % 8][j % 8] - 0.5))];
//
//                int newR = r +  (int) (newRed * (matrix[i % 8][j % 8] - 0.5));
//                int newG = g +  (int) (newGreen * (matrix[i % 8][j % 8] - 0.5));
//                int newB = b +  (int) (newBlue * (matrix[i % 8][j % 8] - 0.5));
//                int a = 0;
//
////                int newRGB = ((newR << 16) | (newG << 8) | newB);
////                result.setRGB(i, j, newRGB);
//            }
//        }

        return result;
    }
}
