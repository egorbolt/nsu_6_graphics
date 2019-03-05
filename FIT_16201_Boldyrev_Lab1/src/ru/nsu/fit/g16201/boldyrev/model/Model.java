package ru.nsu.fit.g16201.boldyrev.model;

import java.util.ArrayList;

public class Model {
    private double LIVE_BEGIN;
    private double LIVE_END;
    private double BIRTH_BEGIN;
    private double BIRTH_END;
    private double FST_IMPACT;
    private double SND_IMPACT;

    private boolean[][] fieldAlive;
    private boolean[][] fieldAlivePrevious;
    private double[][] fieldImpact;
    private int n;
    private int m;

    public Model(int n, int m) {
        this.n = n;
        this.m = m;

        this.fieldAlive = new boolean[n][m];
        this.fieldImpact = new double[n][m];
        this.fieldAlivePrevious = new boolean[n][m];
        this.LIVE_BEGIN = 2.0;
        this.LIVE_END = 3.3;
        this.BIRTH_BEGIN = 2.3;
        this.BIRTH_END = 2.9;
        this.FST_IMPACT = 1.0;
        this.SND_IMPACT = 0.3;
    }

    public int getN() {
        return this.n;
    }

    public int getM() {
        return this.m;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void nextImpact() {
        int fstCount = 0;
        int sndCount = 0;

        for (int i = 0; i <= n - 1; i++) {
            for (int j = 0; j <= m - 1; j++) {
                if (i % 2 != 0 && j == m - 1) {
                    continue;
                }

                fstCount = 0;
                sndCount = 0;

                if (i % 2 == 0) {
                    if (i - 1 >= 0) {
                        if (j - 1 >= 0 && fieldAlive[i - 1][j - 1]) {
                            fstCount++;
                        }
                        if (j - 2 >= 0 && fieldAlive[i - 1][j - 2]) {
                            sndCount++;
                        }
                        if (j + 1 <= m - 2 && fieldAlive[i - 1][j + 1]) {
                            sndCount++;
                        }
                        if (fieldAlive[i - 1][j]) {
                            fstCount++;
                        }
                    }

                    if (i + 1 <= n - 1) {
                        if (j - 1 >= 0 && fieldAlive[i + 1][j - 1]) {
                            fstCount++;
                        }
                        if (j <= m - 2 && fieldAlive[i + 1][j]) {
                            fstCount++;
                        }
                        if (j - 2 >= 0 && fieldAlive[i + 1][j - 2]) {
                            sndCount++;
                        }
                        if (j + 1 <= m - 2 && fieldAlive[i + 1][j + 1]) {
                            sndCount++;
                        }
                    }

                    if (j - 1 >= 0 && fieldAlive[i][j - 1]) {
                        fstCount++;
                    }
                    if (j + 1 <= m - 1 && fieldAlive[i][j + 1]) {
                        fstCount++;
                    }

                }
                else {
                    if (i - 1 >= 0) {
                        if (fieldAlive[i - 1][j]) {
                            fstCount++;
                        }
                        if (j <= m - 2 && fieldAlive[i - 1][j + 1]) {
                            fstCount++;
                        }
                        if (j - 1 >= 0 && fieldAlive[i - 1][j - 1]) {
                            sndCount++;
                        }
                        if (j + 2 <= m - 1 && fieldAlive[i - 1][j + 2]) {
                            sndCount++;
                        }
                    }

                    if (i + 1 <= n - 1) {
                        if (fieldAlive[i + 1][j]) {
                            fstCount++;
                        }
                        if (j <= m - 1 && fieldAlive[i + 1][j + 1]) {
                            fstCount++;
                        }

                        if (j - 1 >= 0 && fieldAlive[i + 1][j - 1]) {
                            sndCount++;
                        }
                        if (j + 2 <= m - 1 && fieldAlive[i + 1][j + 2]) {
                            sndCount++;
                        }
                    }

                    if (j - 1 >= 0 && fieldAlive[i][j - 1]) {
                        fstCount++;
                    }
                    if (j + 1 <= m - 2 && fieldAlive[i][j + 1]) {
                        fstCount++;
                    }
                }

                if (i - 2 >= 0 && fieldAlive[i - 2][j]) {
                    sndCount++;
                }
                if (i + 2 <= n - 1 && fieldAlive[i + 2][j]) {
                    sndCount++;
                }

                fieldImpact[i][j] = FST_IMPACT * fstCount + SND_IMPACT * sndCount;
            }
        }
    }

    public void checkCellsStatus() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                fieldAlivePrevious[i][j] = fieldAlive[i][j];
                if (!fieldAlivePrevious[i][j]) {
                    if (fieldImpact[i][j] >= BIRTH_BEGIN && fieldImpact[i][j] <= BIRTH_END) {
                        fieldAlive[i][j] = true;
                    }
                }

                if (fieldImpact[i][j] < LIVE_BEGIN || fieldImpact[i][j] > LIVE_END) {
                        fieldAlive[i][j] = false;
                }

            }
        }

    }

    public boolean setParameters(double live_begin, double live_end, double birth_begin, double birth_end,
                                    double fst_impact, double snd_impact) {
        if (live_begin <= birth_begin && birth_begin <= birth_end && birth_end <= live_end) {
            this.LIVE_BEGIN = live_begin;
            this.LIVE_END = live_end;
            this.BIRTH_BEGIN = birth_begin;
            this.BIRTH_END = birth_end;
            this.FST_IMPACT = fst_impact;
            this.SND_IMPACT = snd_impact;

            return true;
        }
        else {
            return false;
        }
    }

    public void clearField() {
        this.fieldImpact = new double[n][m];
        this.fieldAlive = new boolean[n][m];
        this.fieldAlivePrevious = new boolean[n][m];
    }

    public void resetField(int n, int m) {
        this.setN(n);
        this.setM(m);
        this.fieldAlive = new boolean[n][m];
        this.fieldImpact = new double[n][m];
        this.fieldAlivePrevious = new boolean[n][m];
    }

    public ArrayList<Integer[]> getAliveCells() {
        ArrayList<Integer[]> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i % 2 != 0 && j == m - 1) {
                    continue;
                }
                if (fieldAlive[i][j]) {
                    Integer[] cell = new Integer[2];
                    cell[0] = i;
                    cell[1] = j;
                    list.add(cell);
                }
            }
        }

        return list;
    }

    public ArrayList<Integer[]> getDeadCells() {
        ArrayList<Integer[]> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i % 2 != 0 && j == m - 1) {
                    continue;
                }
                if (!fieldAlive[i][j]) {
                    Integer[] cell = new Integer[2];
                    cell[0] = i;
                    cell[1] = j;
                    list.add(cell);
                }
            }
        }

        return list;
    }

    public void makeCellAlive(int x, int y) {
        fieldAlive[x][y] = true;
    }

    public void makeCellDead(int x, int y) { fieldAlive[x][y] = false; }

    public boolean[][] getFieldAlive() {
        return this.fieldAlive;
    }

    public double[][] getFieldImpact() {
        return this.fieldImpact;
    }
}
