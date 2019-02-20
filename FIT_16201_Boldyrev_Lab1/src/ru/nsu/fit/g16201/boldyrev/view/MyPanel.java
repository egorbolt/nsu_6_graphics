package ru.nsu.fit.g16201.boldyrev.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {
    private Color borderColor;

    public MyPanel() {
        this.borderColor = Color.BLACK;
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);

        BufferedImage image = new BufferedImage(720, 480, BufferedImage.TYPE_INT_RGB);
        Graphics2D g1 = image.createGraphics();
        g1.setColor(Color.RED);
        g1.setPaint(new Color(255, 255, 255));
        g1.fillRect(0, 0, 720, 480);
        g1.setPaint(new Color(0, 0, 0));
        DrawTools.drawField(g1, 6, 5, 20, 1);
        g1.setPaint(new Color(0, 128, 0));
        //DrawTools.spanColoring(image, new Color(0,0,0), new Color(0, 128, 0), 250, 250);

        g.drawImage(image, 0, 0,this);
    }

    public Color getBorderColor() {
        return this.borderColor;
    }
}
