package ru.nsu.fit.g16201.boldyrev.view;

import ru.nsu.fit.g16201.boldyrev.model.Model;
import ru.nsu.fit.g16201.boldyrev.utilities.SaveLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.text.PlainDocument;

public class Frame extends JFrame {
    private double LIVE_BEGIN = 2.0;
    private double LIVE_END = 3.3;
    private double BIRTH_BEGIN = 2.3;
    private double BIRTH_END = 2.9;
    private double FST_IMPACT = 1.0;
    private double SND_IMPACT = 0.3;
    private Integer n;
    private Integer m;
    private Integer k;
    private Integer t;
    private double buffer = 0;

    private Color liveColor;
    private Color deadColor;

    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JScrollPane scrollPane;
    private JFileChooser fileChooser;

    private boolean xorMode = false;
    private boolean impacts = false;
    private boolean saved = false;
    private boolean needUpdating = false;

    private int WIDTH;
    private int HEIGHT;

    private Model model;
    MyPanel myPanel;

    public Frame() {
        SaveLoad saveload = new SaveLoad();

        n = 10;
        m = 10;
        k = 20;
        t = 1;
        WIDTH = 800;
        HEIGHT = 500;
        model = new Model(n, m);

//        frame = new JFrame();
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        setTitle("Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        menuBar = createMenuBar();
//        toolBar = createToolBar();

        /*creating toolBar*/
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        ButtonGroup group1 = new ButtonGroup();
        ButtonGroup group2 = new ButtonGroup();

        JButton bNew = new JButton(new ImageIcon("src/resources/new.png"));
        bNew.setToolTipText("New");
        toolBar.add(bNew);

        JButton bOpen = new JButton(new ImageIcon("src/resources/open.png"));
        bOpen.setToolTipText("Open");
        toolBar.add(bOpen);

        JButton bSave = new JButton(new ImageIcon("src/resources/save.png"));
        bSave.setToolTipText("Save");
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
//        bImpacts.setSelected(impacts);
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

        toolBar.setRollover(true);
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

        setJMenuBar(menuBar);
        add(toolBar, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        if (3 * k * (m / 2 + 3) > WIDTH) {
            WIDTH = 3 * k * (m / 2 + 3);
        }
        if (3 * k * (n / 2 + 1) > HEIGHT) {
            HEIGHT = 3 * k * (n / 2 + 1);
        }
        myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
        myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.add(myPanel);
        scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setWheelScrollingEnabled(true);

        add(scrollPane);

        pack();
        setLocationRelativeTo(null); //center the frame
        setVisible(true);
        setResizable(false);

        ActionListener lClear = l -> {
            model.clearField();
            bPlay.setSelected(false);
            bStop.setSelected(true);
            myPanel.clearField();
        };

        ActionListener lSave = l -> {
            bPlay.setSelected(false);
            bStop.setSelected(true);
            saveload.save(this, model);
        };

        ActionListener lOpen = l -> {
            bPlay.setSelected(false);
            bStop.setSelected(true);
            saveload.load(this, model);
            n = model.getN();
            m = model.getM();
            if (3 * k * (m / 2 + 3) > 800) {
                WIDTH = 3 * k * (m / 2 + 3);
            }
            else {
                WIDTH = 800;
            }
            if (3 * k * (n / 2) > 500) {
                HEIGHT = 3 * k * (n / 2 + 1);
            }
            else {
                HEIGHT = 500;
            }
            panel.remove(myPanel);
            myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
            myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            panel.add(myPanel);
            myPanel.colorAliveCells(model);
            pack();
        };

        ActionListener lOptions = l -> {
            optionsDialog();
        };

        ActionListener lNew = l -> {
            bPlay.setSelected(false);
            bStop.setSelected(true);
            int result = JOptionPane.showConfirmDialog(this,
                    "Save current field?", "New", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                saveload.save(this, model);
            }
            panel.remove(myPanel);
            myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
            myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            panel.add(myPanel);
            myPanel.colorAliveCells(model);
            pack();
        };

        ActionListener lAbout = l -> {
            JOptionPane.showMessageDialog(this, "Lab: FIT_16201_Boldyrev_Life\n" +
                            "Author:   Egor Boldyrev\n" +
                            "Made:  February 2019\n" +
                            "Thank you for playing!",
                       "About", JOptionPane.INFORMATION_MESSAGE);
        };

        bClear.addActionListener(lClear);
        bSave.addActionListener(lSave);
        bOpen.addActionListener(lOpen);
        bOptions.addActionListener(lOptions);
        bAbout.addActionListener(lAbout);
        bNew.addActionListener(lNew);
    }

    public int getK() {
        return this.k;
    }

    public int getT() {
        return this.t;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void optionsDialog() {
        JDialog dialog = new JDialog(this, "Settings", true);
        IntegerFilter integerFilter = new IntegerFilter();
        DoubleFilter doubleFilter = new DoubleFilter();

        JPanel mainPanel = new JPanel(new GridLayout(1, 7));
        JPanel panelName = new JPanel(new GridLayout(6, 1));
        JPanel panelSliders = new JPanel(new GridLayout(6, 1));
        JPanel panelValues = new JPanel(new GridLayout(6, 1));

        JPanel panelBegin = new JPanel(new GridLayout(6,1));
        JPanel panelValuesForBegin = new JPanel(new GridLayout(6,1));
        JPanel panelEnd = new JPanel(new GridLayout(6,1));
        JPanel panelValuesForEnd = new JPanel(new GridLayout(6,1));

        JLabel nLabel = new JLabel("N", SwingConstants.CENTER);
        JTextField nField = new JTextField();
        PlainDocument docN = (PlainDocument) nField.getDocument();
        docN.setDocumentFilter(integerFilter);
        JSlider jSliderN = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);
        nField.setText(n.toString());
        jSliderN.setValue(n);
        panelName.add(nLabel);
        panelSliders.add(jSliderN);
        panelValues.add(nField);

        JLabel mLabel = new JLabel("M", SwingConstants.CENTER);
        JTextField mField = new JTextField();
        PlainDocument docM = (PlainDocument) mField.getDocument();
        docM.setDocumentFilter(integerFilter);
        JSlider jSliderM = new JSlider(JSlider.HORIZONTAL,1,100,1);
        mField.setText(m.toString());
        jSliderM.setValue(m);
        panelName.add(mLabel);
        panelSliders.add(jSliderM);
        panelValues.add(mField);

        JLabel kLabel = new JLabel("K", SwingConstants.CENTER);
        JTextField kField = new JTextField();
        PlainDocument docK = (PlainDocument) kField.getDocument();
        docK.setDocumentFilter(integerFilter);
        JSlider jSliderK = new JSlider(JSlider.HORIZONTAL,3,50,10);
        kField.setText(k.toString());
        jSliderK.setValue(k);
        panelName.add(kLabel);
        panelSliders.add(jSliderK);
        panelValues.add(kField);

        JLabel tLabel = new JLabel("Thickness", SwingConstants.CENTER);
        JTextField tField = new JTextField();
        PlainDocument docT = (PlainDocument) tField.getDocument();
        docT.setDocumentFilter(integerFilter);
        JSlider jSliderT = new JSlider(JSlider.HORIZONTAL,1,8,1);
        tField.setText(t.toString());
        jSliderT.setValue(t);
        panelName.add(tLabel);
        panelSliders.add(jSliderT);
        panelValues.add(tField);

        ButtonGroup group = new ButtonGroup();

        JRadioButton xorButton = new JRadioButton("XOR", false);
        xorButton.setSelected(xorMode);
        group.add(xorButton);

        JRadioButton replaceButton = new JRadioButton("Replace", true);
        replaceButton.setSelected(!xorMode);
        group.add(replaceButton);

        JButton okButton = new JButton("OK");

        JLabel labelFST = new JLabel("FST_IMPACT", SwingConstants.CENTER);
        JTextField jFST = new JTextField();
        PlainDocument docFST = (PlainDocument)jFST.getDocument();
        docFST.setDocumentFilter(doubleFilter);
        if (FST_IMPACT % 1 == 0.00){
            jFST.setText(String.format("%.0f", FST_IMPACT).replace(",","."));
        } else
            jFST.setText(String.format("%.1f", FST_IMPACT).replace(",","."));

        panelBegin.add(labelFST);
        panelValuesForBegin.add(jFST);

        JLabel labelLB = new JLabel("LIVE_BEGIN", SwingConstants.CENTER);
        JTextField jLB = new JTextField();
        PlainDocument docLB = (PlainDocument) jLB.getDocument();
        docLB.setDocumentFilter(doubleFilter);
        if (LIVE_BEGIN % 1 == 0.00){
            jLB.setText(String.format("%.0f", LIVE_BEGIN).replace(",","."));
        } else
            jLB.setText(String.format("%.1f", LIVE_BEGIN).replace(",","."));

        panelBegin.add(labelLB);
        panelValuesForBegin.add(jLB);

        JLabel labelBB = new JLabel("BIRTH_BEGIN", SwingConstants.CENTER);
        JTextField jBB = new JTextField();
        PlainDocument docBB = (PlainDocument) jBB.getDocument();
        docBB.setDocumentFilter(doubleFilter);
        if (BIRTH_BEGIN % 1 == 0.00){
            jBB.setText(String.format("%.0f", BIRTH_BEGIN).replace(",","."));
        } else
            jBB.setText(String.format("%.1f", BIRTH_BEGIN).replace(",","."));

        panelBegin.add(labelBB);
        panelValuesForBegin.add(jBB);

        JLabel labelSND = new JLabel("SND_IMPACT", SwingConstants.CENTER);
        JTextField jSND = new JTextField();
        PlainDocument docSND = (PlainDocument) jSND.getDocument();
        docSND.setDocumentFilter(doubleFilter);
        if (SND_IMPACT % 1 == 0.00){
            jSND.setText(String.format("%.0f", SND_IMPACT).replace(",","."));
        } else
            jSND.setText(String.format("%.1f", SND_IMPACT).replace(",","."));

        panelEnd.add(labelSND);
        panelValuesForEnd.add(jSND);

        JLabel labelLE = new JLabel("LIVE_END", SwingConstants.CENTER);
        JTextField jLE = new JTextField();
        PlainDocument docLE = (PlainDocument) jLE.getDocument();
        docLE.setDocumentFilter(doubleFilter);
        if (LIVE_END % 1 == 0.00){
            jLE.setText(String.format("%.0f", LIVE_END).replace(",","."));
        } else
            jLE.setText(String.format("%.1f", LIVE_END).replace(",","."));

        panelEnd.add(labelLE);
        panelValuesForEnd.add(jLE);

        JLabel labelBE = new JLabel("BIRTH_END", SwingConstants.CENTER);
        JTextField jBE = new JTextField();
        PlainDocument docBE = (PlainDocument) jBE.getDocument();
        docBE.setDocumentFilter(doubleFilter);
        if (BIRTH_END % 1 == 0.00){
            jBE.setText(String.format("%.0f", BIRTH_END).replace(",","."));
        } else
            jBE.setText(String.format("%.1f", BIRTH_END).replace(",","."));

        panelEnd.add(labelBE);
        panelValuesForEnd.add(jBE);

        panelSliders.add(xorButton);
        panelValues.add(replaceButton);
        panelValues.add(okButton);

        mainPanel.add(panelName);
        mainPanel.add(panelSliders);
        mainPanel.add(panelValues);
        mainPanel.add(panelBegin);
        mainPanel.add(panelValuesForBegin);
        mainPanel.add(panelEnd);
        mainPanel.add(panelValuesForEnd);
        dialog.add(mainPanel);

        okButton.addActionListener(e-> {
            if (!mField.getText().isEmpty())
                m = Integer.parseInt(mField.getText());

            if (!nField.getText().isEmpty())
                n = Integer.parseInt(nField.getText());

            if (!kField.getText().isEmpty()) {
                k = Integer.parseInt(kField.getText());
                if (k < 3)
                    k = 3;
            }

            if (!tField.getText().isEmpty())
                t = Integer.parseInt(tField.getText());

            if (!jFST.getText().isEmpty())
                try {
                    FST_IMPACT = Double.parseDouble(jFST.getText().replace(",", "."));
                } catch (NumberFormatException err) {
                    err.printStackTrace();
                }

            if (!jSND.getText().isEmpty())
                try {
                    SND_IMPACT = Double.parseDouble(jSND.getText().replace(",", "."));
                } catch (NumberFormatException err) {
                    err.printStackTrace();
                }

            if (!jLB.getText().isEmpty()) {
                try {
                    buffer = Double.parseDouble(jLB.getText().replace(",", "."));
                    if (buffer <= BIRTH_BEGIN) {
                        LIVE_BEGIN = buffer;
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "LIVE_BEGIN should be LIVE_BEGIN <= BIRTH_BEGIN",
                                "Incorrect input", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException err) {
                    err.printStackTrace();
                }
            }


            if (!jBB.getText().isEmpty()) {
                try {
                    buffer = Double.parseDouble(jBB.getText().replace(",", "."));
                    if (buffer <= BIRTH_END && LIVE_BEGIN <= buffer) {
                        BIRTH_BEGIN = buffer;

                    } else {
                        JOptionPane.showMessageDialog(this,
                                "BIRTH_BEGIN should be BIRTH_BEGIN <= BIRTH_END",
                                "Incorrect input", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException err) {
                    err.printStackTrace();
                }
            }


            if (!jLE.getText().isEmpty()) {
                try {
                    buffer = Double.parseDouble(jLE.getText().replace(",", "."));
                    if (BIRTH_END <= buffer) {
                        LIVE_END = buffer;
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "LIVE_END should be BIRTH_END <= LIVE_END",
                                "Incorrect input", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (NumberFormatException n) {}
            }

            if (!jBE.getText().isEmpty()) {
                try {
                    buffer = Double.parseDouble(jBE.getText().replace(",", "."));
                    if (BIRTH_BEGIN <= buffer && buffer <= LIVE_END) {
                        BIRTH_END = buffer;
                    }
                    else {
                        JOptionPane.showMessageDialog(this,
                                "BIRTH_END should be BIRTH_BEGIN <= BIRTH_END <= LIVE_END",
                                "Incorrect input", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException n) {}
            }
//            if (tag == 4) {
//                needUpdating = true;
//                dialog.dispose();
//            }

            dialog.dispose();
        });

        jSliderM.addChangeListener(e ->
                mField.setText(((Integer) ((JSlider) e.getSource()).getValue()).toString())
        );

        jSliderN.addChangeListener(e ->
                nField.setText(((Integer) ((JSlider) e.getSource()).getValue()).toString())
        );

        jSliderK.addChangeListener(e ->
                kField.setText(((Integer)((JSlider)e.getSource()).getValue()).toString())
        );

        jSliderT.addChangeListener(e ->
                tField.setText(((Integer)((JSlider)e.getSource()).getValue()).toString())
        );

        mField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!mField.getText().isEmpty())
                    jSliderM.setValue(Integer.parseInt(mField.getText()));
            }
        });

        nField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!nField.getText().isEmpty())
                    jSliderN.setValue(Integer.parseInt(nField.getText()));
            }
        });

        kField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!kField.getText().isEmpty())
                    jSliderK.setValue(Integer.parseInt(kField.getText()));
            }
        });

        tField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(!tField.getText().isEmpty())
                    jSliderT.setValue(Integer.parseInt(tField.getText()));
            }
        });

        xorButton.addActionListener(e ->
                xorMode = true);
        replaceButton.addActionListener(e ->
                xorMode = false);


        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(700,300));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
