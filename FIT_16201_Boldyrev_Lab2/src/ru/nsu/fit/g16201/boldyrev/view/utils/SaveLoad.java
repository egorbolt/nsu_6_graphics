package ru.nsu.fit.g16201.boldyrev.view.utils;

import ru.nsu.fit.g16201.boldyrev.view.panels.MyPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class SaveLoad {
    private static final String DIR_WHERE_SAVE = "FIT_16201_Boldyrev_Lab2_Data/";
    private JFileChooser fileChooser;

    public SaveLoad() {
        this.fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(DIR_WHERE_SAVE));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("BMP/PNG","bmp", "png"));
    }

    public void load(Frame frame, MyPanel myPanel) {
        File file = new File("file.txt");

        fileChooser.setSelectedFile(file);
        int ret = fileChooser.showOpenDialog(frame);
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        else {
            return;
        }

        myPanel.getZoneA().loadImage(file);
    }
}
