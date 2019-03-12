package ru.nsu.fit.g16201.boldyrev.view.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MyPanel extends JPanel {
    private final int ZONE_SIDE_SIZE = 350;
    private MyImage zoneA;
    private MyImage zoneB;
    private MyImage zoneC;
    private JPanel zoneABorder;
    private JPanel zoneBBorder;
    private JPanel zoneCBorder;
    private BufferedImage image;

    public MyPanel() {
        Container container = new Container();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

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
    }

    public MyImage getZoneA() {
        return this.zoneA;
    }

    public MyImage getZoneC() {
        return this.zoneC;
    }

    public void setZoneC(BufferedImage myImage) {
        zoneC.setImage(myImage);
    }

}
