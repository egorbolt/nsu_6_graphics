package ru.nsu.fit.g16201.boldyrev.view;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    public MyPanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //DrawTools.drawHexagon(g, 3, 100, 50);
        DrawTools.drawField(g, 6, 5, 20, 1);
    }
}
