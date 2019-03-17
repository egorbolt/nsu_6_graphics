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
    private int selectHeight;
    private int selectWidth;
    private int selectHeightShow;
    private int selectWidthShow;
    private int selectSize;



    public MyImage() {

    }

    public MyImage(File newImage) {
        try {
            this.image = ImageIO.read(new File("./FIT_16201_Boldyrev_Lab2_Data/Lena.bmp"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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


//        if (width <= ZONE_SIDE_SIZE && height <= ZONE_SIDE_SIZE) {
//            selectHeight = height;
//            selectWidth = width;
//            Image buffer = image.getScaledInstance(ZONE_SIDE_SIZE, ZONE_SIDE_SIZE, Image.SCALE_SMOOTH);
////            imageBorders = new BufferedImage(buffer.getWidth(null), buffer.getHeight(null), BufferedImage.TYPE_INT_ARGB);
//            imageBorders = itobi(buffer);
//            g.drawImage(image, 0, 0, this);
//        }
//        else {
//            double resizing = (double)(width) / height;
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
        g.drawImage(image, 0, 0, this);
    }

    public void loadImage(File file) {
        try {
            this.image = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        repaint();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    public int getSelectSize() {
        return this.selectSize;
    }

    public int getSelectHeight() {
        return this.selectHeight;
    }

    public int getSelectWidth() {
        return this.selectWidth;
    }

    public BufferedImage getImageBorders() {
        return this.imageBorders;
    }

    private BufferedImage itobi(Image source) {
        BufferedImage result = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g1 = result.createGraphics();
        g1.drawImage(source, 0, 0, null);
        g1.dispose();
        return result;
    }
}
