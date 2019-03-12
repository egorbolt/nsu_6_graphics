package ru.nsu.fit.g16201.boldyrev.view.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyImage extends JPanel {
    private BufferedImage image;

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
}
