package ru.nsu.fit.g16201.boldyrev.view.utils;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveLoad {
    private static final String DIR_WHERE_SAVE = "FIT_16201_Boldyrev_Lab2_Data/";
    private JFileChooser fileChooser;

    public SaveLoad() {
        this.fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(DIR_WHERE_SAVE));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("BMP, PNG, JPG, JPEG","bmp", "png", "jpg", "jpeg"));
    }

    public boolean load(Frame frame, MyPanel myPanel) {
        File file = new File("file.png");

        fileChooser.setSelectedFile(file);
        int ret = fileChooser.showOpenDialog(frame);
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            myPanel.getZoneA().loadImage(file);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean save(Frame frame, MyPanel myPanel) {
        if (myPanel.getZoneC().getImage() == null) {
            return false;
        }

        File file = new File("file.png");
        fileChooser.setSelectedFile(file);

        int ret = fileChooser.showSaveDialog(frame);
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }

        try {
            ImageIO.write(myPanel.getZoneC().getImage(), "jpg", file);
        } catch (IOException eIO) {
            eIO.printStackTrace();
        }
        return true;
    }
}
