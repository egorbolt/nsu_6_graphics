package ru.nsu.fit.g16201.boldyrev.utilities;

import ru.nsu.fit.g16201.boldyrev.model.Model;
import ru.nsu.fit.g16201.boldyrev.view.Frame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

public class SaveLoad {
    private static final String DIR_WHERE_SAVE = "FIT_16201_Boldyrev_Life_Data/";
    private JFileChooser fileChooser;

    private int nStart;
    private int mStart;
    private int tStart;
    private int kStart;
    private boolean[][] fieldAliveStart;

    public SaveLoad() {
        this.fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(DIR_WHERE_SAVE));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt"));
    }

    public boolean save(Frame frame, Model model) {
        int n = model.getN();
        int m = model.getM();
        int k = frame.getK();
        int t = frame.getT();
        ArrayList<Integer[]> aliveCells = model.getAliveCells();
        String fileName;
        File file = new File("file.txt");

        fileChooser.setSelectedFile(file);
        int ret = fileChooser.showSaveDialog(frame);
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            fileName = file.getAbsolutePath();
            if (!fileName.endsWith(".txt")) {
                file = new File(fileName + ".txt");
            }
        }
        else {
            return false;
        }
        if (file.exists()) {
            file.delete();
        }

        try (Writer writer = new BufferedWriter(new FileWriter(file))){
            file.createNewFile();
            writer.write(m + " " + n + "\n");
            writer.write(t + "\n");
            writer.write(k + " " + "\n");
            writer.write(aliveCells.size() + "\n");
            for (int i = 0; i < aliveCells.size(); i++) {
                Integer[] cell = aliveCells.get(i);
                writer.write(cell[1] + " " + cell[0] + "\n");
            }
        } catch (IOException eIO) {
            eIO.printStackTrace();
        }

        return true;
    }

    public void load(Frame frame, Model model) throws Exception {
        File file = new File("file.txt");
        String line;
        int[] params = new int[5];
        int index = 0;
        int n;
        int m;
        int t;
        int k;
        int cellAmount;

        fileChooser.setSelectedFile(file);
        int ret = fileChooser.showOpenDialog(frame);
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        else {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while((line = reader.readLine()) != null && index < 5) {
                if (line.contains("/")) {
                    line = line.substring(0, line.indexOf("/"));
                }
                line = line.trim();
                String[] sp = line.split(" ");
                for (int i = 0; i < sp.length; i++) {
                    params[index] = Integer.parseInt(sp[i]);
                    index++;
                }
            }

            m = params[0];
            n = params[1];
            t = params[2];
            k = params[3];
            cellAmount = params[4];
            if (m == 0 || n == 0 || t == 0) {
                throw new Exception();
            }

            frame.setK(k);
            frame.setT(t);

            model.setM(m);
            model.setN(n);
            model.resetField(n, m);

            for (int i = 0; i < cellAmount; i++) {
                if (line.contains("/")) {
                    line = line.substring(0, line.indexOf("/"));
                }
                line = line.trim();
                String[] sp = line.split(" ");
                int a = Integer.parseInt(sp[1]);
                int b = Integer.parseInt(sp[0]);
                if (a >= n || b >= m) {
                    throw new Exception();
                }

                model.makeCellAlive(a, b);
                line = reader.readLine();
            }
        } catch (IOException eIO) {
            eIO.printStackTrace();
        }
    }

    public void getStateAtLoad(Model model, Frame frame) {
        nStart = model.getN();
        mStart = model.getM();
        tStart = frame.getT();
        kStart = frame.getK();
        fieldAliveStart = cloneArray(model.getFieldAlive());
        int a = 0;
    }

    public boolean shouldSave(Model model, Frame frame) {
        boolean [][] fieldAlive = model.getFieldAlive();
        if (model.getN() != nStart || model.getM() != mStart || frame.getT() != tStart || frame.getK() != kStart) {
            return true;
        }

        for (int i = 0; i < nStart; i++) {
            for (int j = 0; j < mStart; j++) {
                if (i % 2 != 0 && j == mStart - 1) {
                    continue;
                }
                if (fieldAlive[i][j] != fieldAliveStart[i][j]) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean[][] cloneArray(boolean[][] src) {
        int length = src.length;
        boolean[][] target = new boolean[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }
}
