package ru.nsu.fit.g16201.boldyrev.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {
    private int n;
    private int m;
    private int k;
    private int thickness;
    private Color borderColor;
    private Color liveColor;
    private Color deadColor;
    private BufferedImage image;
    private Graphics2D g1;


    public MyPanel(int n, int m, int k, int t, int WIDTH, int HEIGHT) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.thickness = t;
        this.borderColor = Color.BLACK;
        this.liveColor = Color.GREEN;
        this.deadColor = Color.WHITE;

        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g1 = image.createGraphics();

        g1.setColor(new Color(255, 255, 255));
        g1.fillRect(0, 0, WIDTH, HEIGHT);
        g1.setColor(new Color(0, 0, 0));
        DrawTools.drawField(g1, this.n, this.m, this.k, this.thickness);
        g1.drawImage(image, 0, 0,this);
        repaint();

        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (isField(image, borderColor, e.getX(), e.getY())) {
                    DrawTools.spanColoring(image, Color.white.getRGB(), Color.green.getRGB(), e.getX(), e.getY());
//                Point p = DrawTools.getCellByCoord(e.getX(), e.getY(), 20);
                    Double[] d = DrawTools.getCellByCoord(e.getX(), e.getY(), k);
//                System.out.println(p.getX() + " " + p.getY());
                    System.out.println(d[0] + " " + d[1]);
                    repaint();
                }
                else {
                    System.out.println("not field");
                }
            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isField(image, borderColor, e.getX(), e.getY())) {
                    DrawTools.spanColoring(image, Color.white.getRGB(), Color.green.getRGB(), e.getX(), e.getY());
//                Point p = DrawTools.getCellByCoord(e.getX(), e.getY(), 20);
                    Double[] d = DrawTools.getCellByCoord(e.getX(), e.getY(), k);
//                System.out.println(p.getX() + " " + p.getY());
                    System.out.println(d[0] + " " + d[1]);
                    repaint();
                }
                else {
                    System.out.println("not field");
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

//        g1.setColor(new Color(255, 255, 255));
//        g1.fillRect(0, 0, 720, 480);
//        g1.setColor(new Color(0, 0, 0));
//        DrawTools.drawField(g1, 6, 5, 20, 1);
//        g1.drawImage(image, 0, 0,this);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    private boolean isField(BufferedImage image, Color borderColor, int x, int y) {
        int i;
        int width = image.getWidth();
        int height = image.getHeight();

        for (i = x; i >= 0; i--) {
            if (image.getRGB(i, y) == borderColor.getRGB()) {
                break;
            }
        }
        if (i == -1) {
            return false;
        }
        for (i = x; i < width; i++) {
            if (image.getRGB(i, y) == borderColor.getRGB()) {
                break;
            }
        }
        if (i == width) {
            return false;
        }

        for (i = y; i >= 0; i--) {
            if (image.getRGB(x, i) == borderColor.getRGB()) {
                break;
            }
        }
        if (i == -1) {
            return false;
        }
        for (i = y; i < height; i++) {
            if (image.getRGB(x, i) == borderColor.getRGB()) {
                break;
            }
        }
        if (i == height) {
            return false;
        }

        return true;
    }

    public void clearField() {

    }
}
