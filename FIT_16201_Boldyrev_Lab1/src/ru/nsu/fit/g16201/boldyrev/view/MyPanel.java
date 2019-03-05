package ru.nsu.fit.g16201.boldyrev.view;

import ru.nsu.fit.g16201.boldyrev.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.Timer;

public class MyPanel extends JPanel {
    private int n;
    private int m;
    private int k;
    private int thickness;
    private Color borderColor;
    private Color liveColor;
    private Color deadColor;
    private Color impactsColor;
    private BufferedImage image;
    private Graphics2D g1;
    private int WIDTH;
    private int HEIGHT;
    private boolean xor;
    private boolean replace;
    private boolean impacts;
    private short[][] wasChanged;
    private Timer timer;
    private GameProcess gameProcess;

    private HashMap<Point, Point> pixelsToCenter;
    private HashMap<Point, Point> centersToPixels;
    private Model model;

    public MyPanel(int n, int m, int k, int t, int width, int height, Model model) {
        this.model = model;
        this.n = n;
        this.m = m;
        this.k = k;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.thickness = t;
        this.borderColor = Color.BLACK;
        this.liveColor = Color.GREEN;
        this.deadColor = Color.WHITE;
        this.impactsColor = Color.DARK_GRAY.darker();
        this.pixelsToCenter = new HashMap<>();
        this.centersToPixels = new HashMap<>();
        this.xor = false;
        this.replace = true;
        this.impacts = false;
        this.wasChanged = new short[n][m];
        this.timer = new Timer();
        this.gameProcess = new GameProcess(this);

        this.image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.g1 = image.createGraphics();

        g1.setColor(new Color(255, 255, 255));
        g1.fillRect(0, 0, WIDTH, HEIGHT);
        g1.setColor(new Color(0, 0, 0));
        DrawTools.drawField(g1, this.n, this.m, this.k, this.thickness, this.pixelsToCenter, this.centersToPixels);
        repaint();

        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (isField(image, borderColor, e.getX(), e.getY())) {
                    if (image.getRGB(e.getX(), e.getY()) != borderColor.getRGB()) {
                        int x = e.getX();
                        int y = e.getY();
                        Point p = DrawTools.getCellbyCoord(image, borderColor.getRGB(), x, y);
                        if (!pixelsToCenter.containsKey(p)) {
                            p.x += 1;
                        }
                        if (!pixelsToCenter.containsKey(p)) {
                            p.y += 1;
                        }
                        if (!pixelsToCenter.containsKey(p)) {
                            p.x -= 1;
                        }
                        Point p2 = pixelsToCenter.get(p);
                        if (replace && !xor) {

                            DrawTools.spanColoring(image, deadColor.getRGB(), liveColor.getRGB(), x, y);
                            model.makeCellAlive(p2.x, p2.y);
                            model.nextImpact();
                            int a = 0;
                        }
                        else if (xor && !replace) {
                            if (image.getRGB(x, y) == liveColor.getRGB()) {
                                DrawTools.spanColoring(image, liveColor.getRGB(), deadColor.getRGB(), x, y);
                                model.makeCellDead(p2.x, p2.y);
                                model.nextImpact();
                            }
                            else if (image.getRGB(x, y) == deadColor.getRGB()) {
                                DrawTools.spanColoring(image, deadColor.getRGB(), liveColor.getRGB(), x, y);
                                model.makeCellAlive(p2.x, p2.y);
                                model.nextImpact();
                            }
                        }
                    }
                    repaint();
                }
            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {
                wasChanged = new short[n][m];
            }

        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isField(image, borderColor, e.getX(), e.getY())) {
                    if (image.getRGB(e.getX(), e.getY()) != borderColor.getRGB()) {

                        int x = e.getX();
                        int y = e.getY();
                        Point p = DrawTools.getCellbyCoord(image, borderColor.getRGB(), x, y);
                        if (!pixelsToCenter.containsKey(p)) {
                            p.x += 1;
                        }
                        if (!pixelsToCenter.containsKey(p)) {
                            p.y += 1;
                        }
                        if (!pixelsToCenter.containsKey(p)) {
                            p.x -= 1;
                        }
                        Point p2 = pixelsToCenter.get(p);
                        if (replace && !xor) {
                            DrawTools.spanColoring(image, deadColor.getRGB(), liveColor.getRGB(), x, y);
                            model.makeCellAlive(p2.x, p2.y);
                            model.nextImpact();
                        }
                        else if (xor && !replace) {
                            int co = image.getRGB(x, y);
                            if (co == liveColor.getRGB() && wasChanged[p2.x][p2.y] != 2) {
                                DrawTools.spanColoring(image, liveColor.getRGB(), deadColor.getRGB(), x, y);
                                model.makeCellDead(p2.x, p2.y);
                                model.nextImpact();
                                wasChanged[p2.x][p2.y] = 1;
                            }
                            else if (co == deadColor.getRGB() && wasChanged[p2.x][p2.y] != 1) {
                                DrawTools.spanColoring(image, deadColor.getRGB(), liveColor.getRGB(), x, y);
                                model.makeCellAlive(p2.x, p2.y);
                                model.nextImpact();
                                wasChanged[p2.x][p2.y] = 2;
                            }
                        }
                    }
                    repaint();
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
        this.pixelsToCenter = new HashMap<>();
        this.centersToPixels = new HashMap<>();
        g1.setColor(Color.white);
        g1.fillRect(0, 0, WIDTH - 1, HEIGHT - 1);
        g1.setColor(Color.black);
        DrawTools.drawField(g1, this.n, this.m, this.k, this.thickness, this.pixelsToCenter, this.centersToPixels);
        repaint();
    }

    public void colorAliveCells() {
        boolean[][] fieldAlive = model.getFieldAlive();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (fieldAlive[i][j]) {
                    Point a = new Point(i, j);
                    int x = (int) centersToPixels.get(a).getX();
                    int y = (int) centersToPixels.get(a).getY();
                    DrawTools.spanColoring(image, deadColor.getRGB(), liveColor.getRGB(), x, y);
                }
            }
        }
    }

    public void setXOR(boolean s) {
        this.xor = s;
        this.replace = !s;
    }

    public boolean getXOR() {
        return this.xor;
    }

    public void setReplace(boolean s) {
        this.replace = s;
        this.xor = !s;
    }

    public boolean getReplace() {
        return this.replace;
    }

    public void setImpacts(boolean impacts) {
        this.impacts = impacts;
    }

    public void nextStep() {
        model.nextImpact();
        model.checkCellsStatus();
        ArrayList<Integer[]> aliveCells = model.getAliveCells();
        ArrayList<Integer[]> deadCells = model.getDeadCells();
        int i = 0;
        int j = 0;

        for (i = 0; i < aliveCells.size(); i++) {
            Integer[] a = aliveCells.get(i);
            Point b = centersToPixels.get(new Point(a[0], a[1]));
            if (image.getRGB(b.x, b.y) == deadColor.getRGB()) {
                DrawTools.spanColoring(image, deadColor.getRGB(), liveColor.getRGB(), b.x, b.y);
            }
        }
        for (i = 0; i < deadCells.size(); i++) {
            Integer[] a = deadCells.get(i);
            Point b = centersToPixels.get(new Point(a[0], a[1]));
            if (image.getRGB(b.x, b.y) == liveColor.getRGB()) {
                DrawTools.spanColoring(image, liveColor.getRGB(), deadColor.getRGB(), b.x, b.y);
            }
        }

        if (impacts) {
            drawImpacts(Color.DARK_GRAY.darker());
        }

        repaint();
    }

    public void playGame() {
        timer.schedule(gameProcess, 0, 500);
    }

    public void stopGame() {
        timer.cancel();
        timer.purge();
        timer = new Timer();
        gameProcess = new GameProcess(this);
    }

    private void drawImpacts(Color color) {
        double [][] fieldImpact = model.getFieldImpact();
        String string = null;
        int x = 0;
        int y = 0;
        int i = 0;
        int j = 0;

        Font font = new Font("Plain", Font.PLAIN, k / 2);
        g1.setFont(font);
        g1.setColor(color);

        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if (i % 2 != 0 && j == m - 1) {
                    continue;
                }
                Point a = new Point(i, j);
                x = (int) centersToPixels.get(a).getX();
                y = (int) centersToPixels.get(a).getY();
                if (fieldImpact[i][j] % 1 == 0.00) {
                    string = String.format("%.0f", fieldImpact[i][j]).replace(",", ".");
                } else {
                    string = String.format("%.1f", fieldImpact[i][j]).replace(",", ".");
                }
                g1.drawString(string, x, y);
            }
        }
    }

    private class GameProcess extends TimerTask {
        MyPanel myPanel;

        GameProcess(MyPanel myPanel) {
            this.myPanel = myPanel;
        }

        @Override
        public void run() {
            myPanel.nextStep();
        }
    }
}
