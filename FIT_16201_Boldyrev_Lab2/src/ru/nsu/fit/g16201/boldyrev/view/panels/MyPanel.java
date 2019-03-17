package ru.nsu.fit.g16201.boldyrev.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

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

        isSelect = true;

//        addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (zoneA.getImage() == null || !isSelect) {
//                    return;
//                }
//
//                int centerX = e.getX();
//                int centerY = e.getY();
//
//                selectZone.setVisible(true);
//                selectZone.setOpaque(false);
//                int a = zoneA.getSelectWidth();
//                int b = zoneA.getSelectHeight();
//                selectZone.setPreferredSize(new Dimension(zoneA.getSelectWidth(), zoneA.getSelectHeight()));
//                BufferedImage buf = zoneA.getImageBorders();
//                zoneC.setImage(buf);
////                if (centerX < 0) {
////                    centerX = 0;
////                }
////                if (centerY < 0) {
////                    centerY = 0;
////                }
////                if (centerX > ZONE_SIDE_SIZE - zoneA.getSelectWidth()) {
////                    centerX = ZONE_SIDE_SIZE - zoneA.getSelectWidth();
////                }
////                if (centerY > ZONE_SIDE_SIZE - zoneA.getSelectHeight()) {
////                    centerY = ZONE_SIDE_SIZE - zoneA.getSelectHeight();
////                }
////
////                MyBorder myBorder = new MyBorder(zoneA.getImageBorders(), centerX, centerY);
////                selectZone.setBorder(myBorder);
////                selectZone.setLocation(centerX, centerY);
//
//                zoneA.repaint();
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent mouseEvent) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent mouseEvent) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent mouseEvent) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent mouseEvent) {
//
//            }
//        });
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

}
