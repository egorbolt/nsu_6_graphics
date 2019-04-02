package ru.nsu.fit.g16201.boldyrev.view.panels;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyBorder extends AbstractBorder {
    private BufferedImage image;
    private int centerX;
    private int centerY;

    public MyBorder(BufferedImage image, int centerX, int centerY) {
        this.image = image;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
        super.paintBorder(component, graphics, x, y, width, height);

        Graphics2D g1 = (Graphics2D) graphics;
        boolean gap = false;
        int i;
        int k = 0;

        try {
            for (i = 0; i < width; i++) {
                if (!gap) {
                    Color c = new Color(image.getRGB(x + centerX, y + i + centerY));
                    int r = c.getRed();
                    int g = c.getGreen();
                    int b = c.getBlue();
                    g1.setColor(new Color(255 - r, 255 - g, 255 - b));
                    g1.drawLine(x, y + i, x, y + i);
                    c = new Color(image.getRGB(x + height - 1 + centerX, y + i + centerY));
                    r = c.getRed();
                    g = c.getGreen();
                    b = c.getBlue();
                    g1.setColor(new Color(255 - r, 255 - g, 255 - b));
                    g1.drawLine(x + height - 1, y + i, x + height - 1, y + i);
                    k++;
                    if (k % 30 == 0) {
                        gap = true;
                    } else {
                        gap = false;
                    }
                } else {
                    Color c = new Color(image.getRGB(x + centerX, y + i + centerY));
                    g1.setColor(c);
                    g1.drawLine(x, y + i, x, y + i);
                    c = new Color(image.getRGB(x + height - 1 + centerX, y + i + centerY));
                    g1.setColor(c);
                    g1.drawLine(x + height - 1, y + i, x + height - 1, y + i);
                    k++;
                    if (k % 20 == 0) {
                        gap = false;
                        k = 0;
                    }
                }
            }

            for (i = 0; i < height; i++) {
                if (!gap) {
                    Color c = new Color(image.getRGB(x + i + centerX, y + centerY));
                    int r = c.getRed();
                    int g = c.getGreen();
                    int b = c.getBlue();
                    g1.setColor(new Color(255 - r, 255 - g, 255 - b));
                    g1.drawLine(x + i, y, x + i, y);
                    c = new Color(image.getRGB(x + i + centerX, y - 1 + width + centerY));
                    r = c.getRed();
                    g = c.getGreen();
                    b = c.getBlue();
                    g1.setColor(new Color(255 - r, 255 - g, 255 - b));
                    g1.drawLine(x + i, y - 1 + width, x + i, y - 1 + width);
                    k++;
                    if (k % 30 == 0) {
                        gap = true;
                    } else {
                        gap = false;
                    }
                } else {
                    Color c = new Color(image.getRGB(x + i + centerX, y + centerY));
                    g1.setColor(c);
                    g1.drawLine(x + i, y, x + i, y);
                    c = new Color(image.getRGB(x + i + centerX, y - 1 + width + centerY));
                    g1.setColor(c);
                    g1.drawLine(x + i, y - 1 + width, x + i, y - 1 + width);
                    k++;
                    if (k % 20 == 0) {
                        gap = false;
                        k = 0;
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException eOut) {

        }
    }
}
