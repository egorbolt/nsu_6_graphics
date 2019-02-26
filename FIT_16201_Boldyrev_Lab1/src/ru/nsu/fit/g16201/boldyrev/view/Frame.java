package ru.nsu.fit.g16201.boldyrev.view;

import ru.nsu.fit.g16201.boldyrev.model.Model;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;

public class Frame extends JFrame {
    private double LIVE_BEGIN;
    private double LIVE_END;
    private double BIRTH_BEGIN;
    private double BIRTH_END;
    private double FST_IMPACT;
    private double SND_IMPACT;
    private int n;
    private int m;
    private int k;
    private int t;

    private Color liveColor;
    private Color deadColor;
    private JFrame frame;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JScrollPane scrollPane;

    private boolean xorMode = false;
    private boolean impacts = false;
    private boolean saved = false;
    private boolean needUpdating = false;

    private int WIDTH;
    private int HEIGHT;

    private Model model;


    public Frame() {
        n = 50;
        m = 50;
        k = 15;
        t = 1;
        WIDTH = 800;
        HEIGHT = 600;
        model = new Model(n, m);

        frame = new JFrame();
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLayout(new BorderLayout());
        frame.setTitle("Life");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

//        menuBar = createMenuBar();
//        toolBar = createToolBar();

        /*creating toolBar*/
        toolBar = new JToolBar();
        toolBar.setRollover(true);
        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();

        JButton bNew = new JButton(new ImageIcon("src/resources/new.png"));
        bNew.setToolTipText("New");
        toolBar.add(bNew);

        JButton bOpen = new JButton(new ImageIcon("src/resources/open.png"));
        bNew.setToolTipText("Open");
        toolBar.add(bOpen);

        JButton bSave = new JButton(new ImageIcon("src/resources/save.png"));
        bNew.setToolTipText("Save");
        toolBar.add(bSave);

        toolBar.add(new JToolBar.Separator());

        JButton bNext = new JButton(new ImageIcon("src/resources/next.png"));
        bNext.setToolTipText("Next");
        bNext.setSelected(false);
        toolBar.add(bNext);

        JButton bClear = new JButton(new ImageIcon("src/resources/clear.png"));
        bClear.setToolTipText("Clear");
        toolBar.add(bClear);

        JToggleButton bImpacts = new JToggleButton(new ImageIcon("src/resources/impacts.png"));
        bImpacts.setToolTipText("Impacts");
        bImpacts.setSelected(impacts);
        toolBar.add(bImpacts);

        toolBar.add(new JToolBar.Separator());

        JToggleButton bPlay = new JToggleButton(new ImageIcon("src/resources/play.png"));
        bPlay.setToolTipText("Play");
        bPlay.setSelected(false);
        group1.add(bPlay);
        toolBar.add(bPlay);

        JToggleButton bStop = new JToggleButton(new ImageIcon("src/resources/stop.png"));
        bStop.setToolTipText("Stop");
        bStop.setSelected(true);
        group1.add(bStop);
        toolBar.add(bStop);

        toolBar.add(new JToolBar.Separator());

        JToggleButton bReplace = new JToggleButton(new ImageIcon("src/resources/replace.png"));
        bReplace.setToolTipText("Replace");
        bReplace.setSelected(!xorMode);
        group2.add(bReplace);
        toolBar.add(bReplace);

        JToggleButton bXor = new JToggleButton(new ImageIcon("src/resources/xor.png"));
        bXor.setToolTipText("Xor");
        bReplace.setSelected(xorMode);
        group2.add(bXor);
        toolBar.add(bXor);

        toolBar.add(new JToolBar.Separator());

        JButton bOptions = new JButton(new ImageIcon("src/resources/options.png"));
        bOptions.setToolTipText("Options");
        toolBar.add(bOptions);

        JButton bAbout = new JButton(new ImageIcon("src/resources/about.png"));
        bAbout.setToolTipText("About");
        toolBar.add(bAbout);
        /*end of creating toolBar*/

        /*creating menuBar*/
        menuBar = new JMenuBar();
        ButtonGroup group3 = new ButtonGroup();

        JMenu fileMenu = new JMenu("File");
        JMenuItem jmiNG = new JMenuItem("New Game");
        fileMenu.add(jmiNG);
        JMenuItem jmOpen = new JMenuItem("Open");
        fileMenu.add(jmOpen);
        JMenuItem jmSave = new JMenuItem("Save");
        fileMenu.add(jmSave);
        JMenuItem jmSaveAs = new JMenuItem("Save As...");
        fileMenu.add(jmSaveAs);
        fileMenu.addSeparator();
        JMenuItem jmExit= new JMenuItem("Exit");
        jmExit.addActionListener(e -> { System.exit(0);});
        fileMenu.add(jmExit);

//        JMenu modifyMenu = new JMenu("Modify");
//        JMenuItem jmOptions = new JMenuItem("Options");
//        modifyMenu.add(jmOptions);
//        JMenuItem jmReplace = new JCheckBoxMenuItem("Replace");
//        jmReplace.setSelected(!xorMode);
//        modifyMenu.add(jmReplace);
//        group3.add(jmReplace);
//        JMenuItem jmXOR = new JCheckBoxMenuItem("XOR");
//        jmXOR.setSelected(xorMode);
//        modifyMenu.add(jmXOR);
//        group3.add(jmXOR);
//        JMenuItem jmImpacts = new JCheckBoxMenuItem("Impacts");
//        jmImpacts.setSelected(impacts);
//        modifyMenu.add(jmImpacts);

        JMenu actionMenu = new JMenu("Action");
        JMenuItem jmNext = new JMenuItem("Next step");
        actionMenu.add(jmNext);
        JMenuItem jmRun = new JMenuItem("Play");
        actionMenu.add(jmRun);
        JMenuItem jmStop = new JMenuItem("Stop");
        actionMenu.add(jmStop);
        actionMenu.addSeparator();
        JMenuItem jmClear = new JMenuItem("Clear");
        actionMenu.add(jmClear);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem jmiAbout = new JMenuItem("About");
        helpMenu.add(jmiAbout);

        menuBar.add(fileMenu);
        menuBar.add(actionMenu);
//        menuBar.add(modifyMenu);
        menuBar.add(helpMenu);
        /*end of creating menuBar*/

        frame.setJMenuBar(menuBar);
        frame.add(toolBar, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        if (2 * k * m > WIDTH) {
            WIDTH = 2 * k * m;
        }
        if (2 * k * n > HEIGHT) {
            HEIGHT = 2 * k * n;
        }
        MyPanel myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT);
        myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.add(myPanel);
        scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setWheelScrollingEnabled(true);

        frame.add(scrollPane);

        frame.pack();
        frame.setLocationRelativeTo(null); //center the frame
        frame.setVisible(true);
        frame.setResizable(false);

        ActionListener lClear = l -> {
            model.clearField();
            bPlay.setSelected(false);
            bStop.setSelected(true);
            myPanel.clearField();
        };

        bClear.addActionListener(lClear);
    }

}
