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
    private double[][] fieldImpact;
    private int n;
    private int m;

    public Model(int n, int m) {
        this.n = n;
        this.m = m;

        this.fieldAlive = new boolean[n][m];
        this.fieldImpact = new double[n][m];
        this.LIVE_BEGIN = 2.0;
        this.LIVE_END = 3.3;
        this.BIRTH_BEGIN = 2.3;
        this.BIRTH_END = 2.9;
        this.FST_IMPACT = 1.0;
        this.SND_IMPACT = 0.3;
    }

    public void nextGeneration() {
        int fstCount = 0;
        int sndCount = 0;

        for (int i = 0; i <= n -1; i++) {
            for (int j = 0; j <= m - 1; j++) {
                if (i - 1 >= 0 && j - 1 >= 0 && fieldAlive[i - 1][j - 1]) {
                    fstCount++;
                }
                if (i - 1 >= 0 && j >= 0 && fieldAlive[i - 1][j]) {
                    fstCount++;
                }
                if (i >= 0 && j - 1 >= 0 && fieldAlive[i][j - 1]) {
                    fstCount++;
                }
                if (i % 2 != 0 && j + 1 <= m - 2 && fieldAlive[i][j + 1]) {
                    fstCount++;
                }
                if (i % 2 == 0 && j + 1 <= m - 1 && fieldAlive[i][j + 1]) {
                    fstCount++;
                }
                if (i + 1 <= n - 1 && j - 1 >= 0 && fieldAlive[i + 1][j - 1]) {
                    fstCount++;
                }
                if (j % 2 != 1 && i + 1 <= n - 1 && j <= m - 2 && fieldAlive[i + 1][j]) {
                    fstCount++;
                }
                if (j % 2 == 0 && i + 1 <= n - 1 && j <= m - 1 && fieldAlive[i + 1][j]) {
                    fstCount++;
                }

                /*-------------------------------------*/
                if (i - 2 >= 0 && fieldAlive[i - 2][j]) {
                    sndCount++;
                }
                if (i + 2 <= n - 1 && fieldAlive[i + 2][j]) {
                    sndCount++;
                }

                if (j - 2 >= 0) {
                    if ((i - 1 >= 0 && fieldAlive[i - 1][j - 2]) || (i + 1 <= n - 1 && fieldAlive[i + 1][j - 2])) {
                        sndCount++;
                    }

                }
//                if (i - 1 >= 0 && j - 2 >= 0 && fieldAlive[i - 1][j - 2]) {
//                    sndCount++;
//                }
//                if (i + 1 <= n - 1 && j - 2 >= 0 && fieldAlive[i + 1][j - 2]) {
//                    sndCount++;
//                }

                if (i % 2 == 0 && j + 1 <= m - 2) {
                    if ((i - 1 >= 0 && fieldAlive[i - 1][j + 1]) || (i + 1 <= n - 1 && fieldAlive[i + 1][j + 1])) {
                        sndCount++;
                    }
                }
//                if (i % 2 == 0 && i - 1 >= 0 && j + 1 <= m - 2 && fieldAlive[i - 1][j + 1]) {
//                    sndCount++;
//                }
//                if (i % 2 == 0 && i + 1 <= n - 1 && j + 1 <= m - 2 && fieldAlive[i + 1][j + 1]) {
//                    sndCount++;
//                }

                if (i % 2 != 0 && j + 1 <= m - 1) {
                    if ((i - 1 >= 0 && fieldAlive[i - 1][j + 1]) || (i + 1 <= n - 1 && fieldAlive[i + 1][j + 1])) {
                        sndCount++;
                    }
                }
//                if (i % 2 != 0 && i - 1 >= 0 && j + 1 <= m - 1 && fieldAlive[i - 1][j + 1]) {
//                    sndCount++;
//                }
//                if (i % 2 != 0 && i + 1 <= n - 1 && j + 1 <= m - 1 && fieldAlive[i + 1][j + 1]) {
//                    sndCount++;
//                }

                fieldImpact[i][j] = FST_IMPACT * fstCount * SND_IMPACT * sndCount;
            }
        }
    }

    public void invertField() {
        for (int i = 0; i <= n - 1; i++) {
            for (int j = 0; j <= m - 1; j++) {
                fieldAlive[i][j] = !fieldAlive[i][j];
                if (i % 2 != 0) {
                    j += 2;
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
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                fieldAlive[i][j] = false;
                fieldImpact[i][j] = 0;
            }
        }
    }

}
