package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.image.BufferedImage;

public class FSFilter {
    private int redShift;
    private int greenShift;
    private int blueShift;

    public FSFilter(int redShift, int greenShift, int blueShift) {
        this.redShift = redShift;
        this.greenShift = greenShift;
        this.blueShift = blueShift;
    }

    public BufferedImage filterFS(MyImage myImage, int redShift, int greenShift, int blueShift) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();
        BufferedImage result = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        int[][] resultRed = new int[sourceWidth][sourceHeight];
        int[][] resultGreen = new int[sourceWidth][sourceHeight];
        int[][] resultBlue = new int[sourceWidth][sourceHeight];

        for (int i = 0; i < sourceHeight; i++) {
            for (int j = 0; j < sourceWidth; j++) {
                int rgb = source.getRGB(i, j);
                int b = rgb & 255;
                int g = (rgb >> 8) & 255;
                int r = (rgb >> 16) & 255;

                resultRed[i][j] = r;
                resultGreen[i][j] = g;
                resultBlue[i][j] = b;
            }
        }

        int newRed = 256 / redShift;
        int newGreen = 256 / greenShift;
        int newBlue = 256 / blueShift;

        for (int i = 0; i < sourceHeight; i++) {
            for (int j = 0; j < sourceWidth; j++) {
                int r = resultRed[i][j];
                int g = resultGreen[i][j];
                int b = resultBlue[i][j];

                int redError = r % newRed;
                int greenError = g % newGreen;
                int blueError = b % newBlue;

                r -= redError;
                g -= greenError;
                b -= blueError;

                if (j + 1 < sourceWidth) {
//                    int rgb1 = result.getRGB(i, j + 1);
//                    int r1 = (rgb1 >> 16) & 255;
//                    int g1 = (rgb1 >> 8) & 255;
//                    int b1 = rgb1 & 255;
//
//                    r1 += (int) (redError * 7.0 / 16);
//                    g1 += (int) (greenError * 7.0 / 16);
//                    b1 += (int) (blueError * 7.0 / 16);
//
////                    if (r1 > 255) r1 = 255;
////                    if (g1 > 255) g1 = 255;
////                    if (b1 > 255) b1 = 255;
//
//                    int newRGB1 = ((r1 << 16) | (g1 << 8) | b1);
//                    result.setRGB(i, j + 1, newRGB1);

                    resultRed[i][j + 1] += (int) (redError * 7 / 16.0);
                    resultGreen[i][j + 1] += (int) (greenError * 7 / 16.0);
                    resultBlue[i][j + 1] += (int) (blueError * 7 / 16.0);
                }

                if (i + 1 < sourceHeight && j - 1 > 0) {
//                    int rgb1 = result.getRGB(i + 1, j - 1);
//                    int r1 = (rgb1 >> 16) & 255;
//                    int g1 = (rgb1 >> 8) & 255;
//                    int b1 = rgb1 & 255;
//
////                    if (r1 > 255) r1 = 255;
////                    if (g1 > 255) g1 = 255;
////                    if (b1 > 255) b1 = 255;
//
//                    r1 += (int) (redError * 3.0 / 16);
//                    g1 += (int) (greenError * 3.0 / 16);
//                    b1 += (int) (blueError * 3.0 / 16);
//
//                    int newRGB1 = ((r1 << 16) | (g1 << 8) | b1);
//                    result.setRGB(i + 1, j - 1, newRGB1);
                    resultRed[i + 1][j - 1] += (int) (redError * 3/ 16.0);
                    resultGreen[i + 1][j - 1] += (int) (greenError * 3/ 16.0);
                    resultBlue[i + 1][j - 1] += (int) (blueError * 3/ 16.0);
                }

                if (i + 1 < sourceHeight) {
//                    int rgb1 = result.getRGB(i + 1, j);
//                    int r1 = (rgb1 >> 16) & 255;
//                    int g1 = (rgb1 >> 8) & 255;
//                    int b1 = rgb1 & 255;
//
//                    r1 += (int) (redError * 5.0 / 16);
//                    g1 += (int) (greenError * 5.0 / 16);
//                    b1 += (int) (blueError * 5.0 / 16);
//
////                    if (r1 > 255) r1 = 255;
////                    if (g1 > 255) g1 = 255;
////                    if (b1 > 255) b1 = 255;
//
//                    int newRGB1 = ((r1 << 16) | (g1 << 8) | b1);
//                    result.setRGB(i + 1, j, newRGB1);
                    resultRed[i + 1][j] += (int) (redError * 5/ 16.0);
                    resultGreen[i + 1][j] += (int) (greenError * 5/ 16.0);
                    resultBlue[i + 1][j] += (int) (blueError * 5/ 16.0);
                }

                if (i + 1 < sourceHeight && j + 1 < sourceWidth) {
//                    int rgb1 = result.getRGB(i + 1, j + 1);
//                    int r1 = (rgb1 >> 16) & 255;
//                    int g1 = (rgb1 >> 8) & 255;
//                    int b1 = rgb1 & 255;
//
//                    r1 += (int) (redError * 1.0 / 16);
//                    g1 += (int) (greenError * 1.0 / 16);
//                    b1 += (int) (blueError * 1.0 / 16);
//
////                    if (r1 > 255) r1 = 255;
////                    if (g1 > 255) g1 = 255;
////                    if (b1 > 255) b1 = 255;
//
//                    int newRGB1 = ((r1 << 16) | (g1 << 8) | b1);
//                    result.setRGB(i + 1, j + 1, newRGB1);
                    resultRed[i + 1][j + 1] += (int) (redError / 16.0);
                    resultGreen[i + 1][j + 1] += (int) (greenError / 16.0);
                    resultBlue[i + 1][j + 1] += (int) (blueError / 16.0);
                }

                if (r > 255) { r = 255; }
                if (g > 255) { g = 255; }
                if (b > 255) { b = 255; }

                int newRGB = ((r << 16) | (g << 8) | b);
                result.setRGB(i, j, newRGB);

            }
        }

        return result;
    }
}
