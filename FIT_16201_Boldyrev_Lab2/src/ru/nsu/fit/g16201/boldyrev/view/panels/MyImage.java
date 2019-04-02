package ru.nsu.fit.g16201.boldyrev.view.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyImage extends JPanel {
    private final int ZONE_SIDE_SIZE = 350;
    private BufferedImage image;
    private BufferedImage imageBorders;
    private int select;
    private int newHeight;
    private int newWidth;

    public MyImage() {

    }

    public MyImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width;
        int height;

        if (image == null) {
            return;
        }

        width = image.getWidth();
        height = image.getHeight();

        select = 350 * 350;
        if (height > width) {
            select /= height;
        }
        else {
            select /= width;
        }

//        if (width <= ZONE_SIDE_SIZE && height <= ZONE_SIDE_SIZE) {
//            selectHeight = height;
//            selectWidth = width;
//            Image buffer = image.getScaledInstance(ZONE_SIDE_SIZE, ZONE_SIDE_SIZE, Image.SCALE_SMOOTH);
//            imageBorders = itobi(buffer);
//            g.drawImage(image, 0, 0, this);
//        }
//        else {
//            double resizing = (double) (width) / height;
//
//            if (height > width) {
//                selectHeight = selectWidth = ZONE_SIDE_SIZE * ZONE_SIDE_SIZE / height;
//            }
//            else {
//                selectHeight = selectWidth = ZONE_SIDE_SIZE * ZONE_SIDE_SIZE / width;
//            }
//
//            if (resizing >= 1) {
//                Image buffer = image.getScaledInstance(ZONE_SIDE_SIZE, (int) (ZONE_SIDE_SIZE / resizing), Image.SCALE_SMOOTH);
//                imageBorders = itobi(buffer);
//                g.drawImage(image, 0, 0, ZONE_SIDE_SIZE, (int) (ZONE_SIDE_SIZE / resizing), null);
//            } else {
//                Image buffer = image.getScaledInstance((int) (ZONE_SIDE_SIZE * resizing), ZONE_SIDE_SIZE, Image.SCALE_SMOOTH);
//                imageBorders = itobi(buffer);
//                g.drawImage(image, 0, 0, (int) (ZONE_SIDE_SIZE * resizing), ZONE_SIDE_SIZE, null);
//            }
//        }

        double resize = (double) (width) / height;

        if (resize >= 1) {
            newWidth = ZONE_SIDE_SIZE;
            newHeight = (int) (ZONE_SIDE_SIZE / resize);
            g.drawImage(image, 0, 0, ZONE_SIDE_SIZE, (int) (ZONE_SIDE_SIZE / resize), null);
            Image buffer = image.getScaledInstance(ZONE_SIDE_SIZE, (int) (ZONE_SIDE_SIZE / resize), Image.SCALE_SMOOTH);
            imageBorders = itobi(buffer);
        }

        else {
            newWidth = (int) (ZONE_SIDE_SIZE * resize);
            newHeight = ZONE_SIDE_SIZE;
            g.drawImage(image, 0, 0, (int) (ZONE_SIDE_SIZE * resize), ZONE_SIDE_SIZE, null);
            Image buffer = image.getScaledInstance(ZONE_SIDE_SIZE, (int) (ZONE_SIDE_SIZE / resize), Image.SCALE_SMOOTH);
            imageBorders = itobi(buffer);
        }

    }

    public void loadImage(File file) {
        try {
            this.image = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        repaint();
    }

    private BufferedImage itobi(Image source) {
        BufferedImage result = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g1 = result.createGraphics();
        g1.drawImage(source, 0, 0, null);
        g1.dispose();
        return result;
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public int getImageWidth() {
        return image.getWidth();
    }

    public int getImageHeight() {
        return image.getHeight();
    }


    public BufferedImage getImageBorders() {
        return this.imageBorders;
    }

    public int getSelect() {
        return select;
    }

    public int getNewHeight() {
        return newHeight;
    }

}
