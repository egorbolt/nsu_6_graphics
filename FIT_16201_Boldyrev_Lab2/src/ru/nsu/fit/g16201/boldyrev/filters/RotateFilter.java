package ru.nsu.fit.g16201.boldyrev.filters;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RotateFilter {
    public RotateFilter() {

    }

    public BufferedImage filterRotate(MyImage myImage) {
        BufferedImage source = myImage.getImage();
        int sourceHeight = source.getHeight();
        int sourceWidth = source.getWidth();

        double degree = Math.toRadians(15);
        int newHeight = (int) (sourceWidth * Math.abs(Math.sin(degree)) + sourceHeight * Math.abs(Math.cos(degree)));
        int newWidth = (int) (sourceHeight * Math.abs(Math.sin(degree)) + sourceWidth * Math.abs(Math.cos(degree)));

        BufferedImage result = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g1 = result.createGraphics();
        g1.setColor(Color.WHITE);
        g1.fillRect(0, 0, newWidth, newHeight);
        g1.translate(newWidth/2, newHeight/2);
        g1.rotate(degree);
        g1.translate(-sourceWidth / 2, -sourceHeight / 2);
        g1.drawImage(source, 0, 0, null);
        g1.dispose();

        return result;
    }
}
