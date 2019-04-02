package ru.nsu.fit.g16201.boldyrev.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class MyPanel extends JPanel {
    private final int ZONE_SIDE_SIZE = 350;
    private MyImage zoneA;
    private MyImage zoneB;
    private MyImage zoneC;
    private JPanel zoneABorder;
    private JPanel zoneBBorder;
    private JPanel zoneCBorder;
    private JPanel selectZone;
    private BufferedImage image;

    private boolean isSelect = false;

    public MyPanel() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        selectZone = new JPanel();
        selectZone.setOpaque(false);

        zoneA = new MyImage();
        zoneABorder = new JPanel(new GridLayout(1, 1));
        zoneABorder.setBorder((BorderFactory.createDashedBorder
                (Color.BLACK, 1, 6, 3, true)));
        zoneABorder.setPreferredSize(new Dimension(ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));
        zoneABorder.add(zoneA);
        zoneA.add(selectZone);
        add(zoneABorder, container);
        add(Box.createRigidArea(new Dimension(10,0)));

        zoneB = new MyImage();
        zoneBBorder = new JPanel(new GridLayout(1, 1));
        zoneBBorder.setBorder((BorderFactory.createDashedBorder
                (Color.BLACK, 1, 6, 3, true)));
        zoneBBorder.setPreferredSize(new Dimension(ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));
        zoneBBorder.add(zoneB);
        add(zoneBBorder, container);
        add(Box.createRigidArea(new Dimension(10,0)));

        zoneC = new MyImage();
        zoneCBorder = new JPanel(new GridLayout(1, 1));
        zoneCBorder.setBorder((BorderFactory.createDashedBorder
                (Color.BLACK, 1, 6, 3, true)));
        zoneCBorder.setPreferredSize(new Dimension(ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));
        zoneCBorder.add(zoneC);
        add(zoneCBorder, container);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (zoneA.getImage() == null || !isSelect) {
                        return;
                    }
//                    int centerX = e.getX() - selectZone.getWidth();
//                    int centerY = e.getY() - selectZone.getWidth();
//
//                    selectZone.setPreferredSize(new Dimension(zoneA.getSelectWidth(), zoneA.getSelectHeight()));
//                    if (centerX < 0) {
//                        centerX = 0;
//                    }
//                    if (centerY < 0) {
//                        centerY = 0;
//                    }
//                    if (centerX > ZONE_SIDE_SIZE - zoneA.getSelectWidth()) {
//                        centerX = ZONE_SIDE_SIZE - zoneA.getSelectWidth();
//                    }
//                    if (centerY > ZONE_SIDE_SIZE - zoneA.getSelectHeight()) {
//                        centerY = ZONE_SIDE_SIZE - zoneA.getSelectHeight();
//                    }
//
//                    MyBorder myBorder = new MyBorder(zoneA.getImageBorders(), centerX, centerY);
//                    selectZone.setBorder(myBorder);
//                    selectZone.setLocation(centerX, centerY);
//                    selectZone.setVisible(true);
//                    selectZone.setOpaque(false);
//
//                    double buf1 = (double) (zoneA.getImageWidth()) / ZONE_SIDE_SIZE;
//                    double buf2 = (double) (zoneA.getImageHeight()) / ZONE_SIDE_SIZE;
//                    int x = (int) (e.getX() * buf1) - ZONE_SIDE_SIZE / 2;
//                    int y = (int) (e.getY() * buf2) - ZONE_SIDE_SIZE / 2;
//                    if (x < 0) {
//                        x = 0;
//                    } else if (x + ZONE_SIDE_SIZE > zoneA.getImageWidth()) {
//                        x = zoneA.getImageWidth() - ZONE_SIDE_SIZE;
//                    }
//
//                    if (y < 0) {
//                        y = 0;
//                    } else if (y + ZONE_SIDE_SIZE > zoneA.getImage().getHeight()) {
//                        y = zoneA.getImageHeight() - ZONE_SIDE_SIZE;
//                    }
//
//                    if (zoneA.getImageHeight() < ZONE_SIDE_SIZE && zoneA.getImageWidth() < ZONE_SIDE_SIZE) {
//                        zoneB.setImage(zoneA.getImage().getSubimage(x, y, zoneA.getImageWidth(), zoneA.getImageHeight()));
//
//                    }
//                    else {
//                        zoneB.setImage(zoneA.getImage().getSubimage(x, y, ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));
//                    }
//                    zoneB.repaint();

                    int centerX = e.getPoint().x - selectZone.getWidth();
                    int centerY = e.getPoint().y - selectZone.getWidth();

                    selectZone.setOpaque(false);
                    selectZone.setVisible(true);
                    selectZone.setPreferredSize(new Dimension(zoneA.getSelect(), zoneA.getSelect()));

                    if (centerX < 0)
                        centerX = 0;
                    if (centerX > 350 - zoneA.getSelect())
                        centerX = 350 - zoneA.getSelect();
                    if (centerY < 0)
                        centerY = 0;
                    if (centerY > zoneA.getNewHeight() - zoneA.getSelect())
                        centerY =  zoneA.getNewHeight() - zoneA.getSelect();

                    MyBorder borderPanel = new MyBorder(zoneA.getImageBorders(), centerX, centerY);
                    selectZone.setBorder(borderPanel);

                    selectZone.setLocation(centerX, centerY);

                    double buf = (double) (zoneA.getImageWidth()) / ZONE_SIDE_SIZE;
                    int x = (int) (e.getX() * buf) - ZONE_SIDE_SIZE;
                    int y = (int) (e.getY() * buf) - ZONE_SIDE_SIZE;

                    if (x < 0) {
                        x = 0;
                    } else if (x + ZONE_SIDE_SIZE > zoneA.getImageWidth()) {
                        x = zoneA.getImageWidth() - ZONE_SIDE_SIZE;
                    }

                    if (y < 0) {
                        y = 0;
                    } else if (y + ZONE_SIDE_SIZE > zoneA.getImageHeight()) {
                        y = zoneA.getImageHeight() - ZONE_SIDE_SIZE;
                    }

                    zoneB.setImage(zoneA.getImage().getSubimage(x, y, ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));
                    zoneB.repaint();

                } catch (RasterFormatException ea) {

                } catch (ArrayIndexOutOfBoundsException eIO) {

                }

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                try {
                    if (zoneA.getImage() == null || !isSelect) {
                        return;
                    }

//                    int centerX = e.getX() - selectZone.getWidth();
//                    int centerY = e.getY() - selectZone.getWidth();
//
//                    selectZone.setPreferredSize(new Dimension(zoneA.getSelectWidth(), zoneA.getSelectHeight()));
//                    if (centerX < 0) {
//                        centerX = 0;
//                    }
//                    if (centerY < 0) {
//                        centerY = 0;
//                    }
//                    if (centerX > ZONE_SIDE_SIZE - zoneA.getSelectWidth()) {
//                        centerX = ZONE_SIDE_SIZE - zoneA.getSelectWidth();
//                    }
//                    if (centerY > ZONE_SIDE_SIZE - zoneA.getSelectHeight()) {
//                        centerY = ZONE_SIDE_SIZE - zoneA.getSelectHeight();
//                    }
//
//                    MyBorder myBorder = new MyBorder(zoneA.getImageBorders(), centerX, centerY);
//                    selectZone.setBorder(myBorder);
//                    selectZone.setLocation(centerX, centerY);
//                    selectZone.setVisible(true);
//                    selectZone.setOpaque(false);
//
//                    double buf1 = (double) (zoneA.getImageWidth()) / ZONE_SIDE_SIZE;
//                    double buf2 = (double) (zoneA.getImageHeight()) / ZONE_SIDE_SIZE;
//                    int x = (int) (e.getX() * buf1) - ZONE_SIDE_SIZE / 2;
//                    int y = (int) (e.getY() * buf2) - ZONE_SIDE_SIZE / 2;
//                    if (x < 0) {
//                        x = 0;
//                    } else if (x + ZONE_SIDE_SIZE > zoneA.getImageWidth()) {
//                        x = zoneA.getImageWidth() - ZONE_SIDE_SIZE;
//                    }
//
//                    if (y < 0) {
//                        y = 0;
//                    } else if (y + ZONE_SIDE_SIZE > zoneA.getImage().getHeight()) {
//                        y = zoneA.getImageHeight() - ZONE_SIDE_SIZE;
//                    }
//
//                    if (zoneA.getImageHeight() < ZONE_SIDE_SIZE && zoneA.getImageWidth() < ZONE_SIDE_SIZE) {
//                        zoneB.setImage(zoneA.getImage().getSubimage(x, y, zoneA.getImageWidth(), zoneA.getImageHeight()));
//
//                    }
//                    else {
//                        zoneB.setImage(zoneA.getImage().getSubimage(x, y, ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));
//                    }
//                    zoneB.repaint();

                    int centerX = e.getPoint().x - selectZone.getWidth();
                    int centerY = e.getPoint().y - selectZone.getWidth();

                    selectZone.setOpaque(false);
                    selectZone.setVisible(true);
                    selectZone.setPreferredSize(new Dimension(zoneA.getSelect(), zoneA.getSelect()));

                    if (centerX < 0)
                        centerX = 0;
                    if (centerX > 350 - zoneA.getSelect())
                        centerX = 350 - zoneA.getSelect();
                    if (centerY < 0)
                        centerY = 0;
                    if (centerY > zoneA.getNewHeight() - zoneA.getSelect())
                        centerY =  zoneA.getNewHeight() - zoneA.getSelect();

                    MyBorder borderPanel = new MyBorder(zoneA.getImageBorders(), centerX, centerY);
                    selectZone.setBorder(borderPanel);

                    selectZone.setLocation(centerX, centerY);

                    double buf = (double) (zoneA.getImageWidth()) / ZONE_SIDE_SIZE;
                    int x = (int) (e.getX() * buf) - ZONE_SIDE_SIZE;
                    int y = (int) (e.getY() * buf) - ZONE_SIDE_SIZE;

                    if (x < 0) {
                        x = 0;
                    } else if (x + ZONE_SIDE_SIZE > zoneA.getImageWidth()) {
                        x = zoneA.getImageWidth() - ZONE_SIDE_SIZE;
                    }

                    if (y < 0) {
                        y = 0;
                    } else if (y + ZONE_SIDE_SIZE > zoneA.getImageHeight()) {
                        y = zoneA.getImageHeight() - ZONE_SIDE_SIZE;
                    }

                    zoneB.setImage(zoneA.getImage().getSubimage(x, y, ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));
                    zoneB.repaint();
                } catch (RasterFormatException ea) {

                } catch (ArrayIndexOutOfBoundsException eIO) {

                }
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

            }
        });
    }

    public MyImage getZoneA() {
        return this.zoneA;
    }

    public MyImage getZoneB() { return this.zoneB; }

    public MyImage getZoneC() {
        return this.zoneC;
    }

    public void setZoneC(BufferedImage myImage) {
        zoneC.setImage(myImage);
    }

    public void setZoneB(BufferedImage myImage) {
        zoneB.setImage(myImage);
    }

    public void clearAll() {
        selectZone.setVisible(false);
        zoneA.setImage(null);
        zoneB.setImage(null);
        zoneC.setImage(null);

        revalidate();
        repaint();
    }

    public void clearZoneB() {
        zoneB.setImage(null);
        repaint();
    }

    public void clearZoneC() {
        zoneC.setImage(null);
        repaint();
    }

    public void BC() {
        zoneC.setImage(zoneB.getImage().getSubimage(0, 0, ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));
    }

    public void CB() {
        zoneB.setImage(zoneC.getImage().getSubimage(0, 0, ZONE_SIDE_SIZE, ZONE_SIDE_SIZE));

    }

    public void resetSelectZone() {
        selectZone.setVisible(false);
    }

    public void setIsSelected(boolean isSelect) {
        this.isSelect = isSelect;
    }

}
