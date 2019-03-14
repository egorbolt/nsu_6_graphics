package ru.nsu.fit.g16201.boldyrev.view;

import ru.nsu.fit.g16201.boldyrev.model.Model;
import ru.nsu.fit.g16201.boldyrev.utilities.SaveLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.NumberFormatter;
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
    private boolean isUpdating;

    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JScrollPane scrollPane;

    private boolean xorMode = false;
    private boolean impacts = false;
    private boolean isPlaying;

    private int WIDTH;
    private int HEIGHT;

    private int semichord;
    private int nframe;
    private int kDefault = 6;

    private Model model;
    MyPanel myPanel;

    public Frame() {
        SaveLoad saveload = new SaveLoad();
        isUpdating = false;
        isPlaying = false;

        n = 6;
        m = 6;
        k = 20;
        t = 1;
        model = new Model(n, m);
//813 702
        setPreferredSize(new Dimension(900, 750));
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(580, 500));

        setTitle("Life");

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

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
        JMenuItem jmNew = new JMenuItem("New");
        fileMenu.add(jmNew);
        JMenuItem jmOpen = new JMenuItem("Open");
        fileMenu.add(jmOpen);
        JMenuItem jmSave = new JMenuItem("Save");
        fileMenu.add(jmSave);
        fileMenu.addSeparator();
        JMenuItem jmExit= new JMenuItem("Exit");
        jmExit.addActionListener(e -> { System.exit(0);});
        fileMenu.add(jmExit);

        JMenu modifyMenu = new JMenu("Modify");
        JMenuItem jmOptions = new JMenuItem("Options");
        modifyMenu.add(jmOptions);
        JMenuItem jmReplace = new JCheckBoxMenuItem("Replace");
        jmReplace.setSelected(!xorMode);
        modifyMenu.add(jmReplace);
        group3.add(jmReplace);
        JMenuItem jmXOR = new JCheckBoxMenuItem("XOR");
        jmXOR.setSelected(xorMode);
        modifyMenu.add(jmXOR);
        group3.add(jmXOR);
        JMenuItem jmImpacts = new JCheckBoxMenuItem("Impacts");
        jmImpacts.setSelected(impacts);
        modifyMenu.add(jmImpacts);

        JMenu actionMenu = new JMenu("Action");
        JMenuItem jmNext = new JMenuItem("Next step");
        actionMenu.add(jmNext);
        JMenuItem jmPlay = new JMenuItem("Play");
        actionMenu.add(jmPlay);
        JMenuItem jmStop = new JMenuItem("Stop");
        actionMenu.add(jmStop);
        actionMenu.addSeparator();
        JMenuItem jmClear = new JMenuItem("Clear");
        actionMenu.add(jmClear);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem jmAbout = new JMenuItem("About");
        helpMenu.add(jmAbout);

        menuBar.add(fileMenu);
        menuBar.add(actionMenu);
        menuBar.add(modifyMenu);
        menuBar.add(helpMenu);
        /*end of creating menuBar*/

        setJMenuBar(menuBar);
        add(toolBar, BorderLayout.NORTH);

        semichord = (int)(k * Math.sqrt(3)) + 1;
        if (semichord * m > 800) {
            WIDTH = semichord * m - m + t;
        } else {
            WIDTH = 800;
        }
        nframe = n / 2;
        if (n % 2 != 0) {
            nframe += 1;
        }
        if (3 * k * nframe + t > 600) {
            HEIGHT = 3 * k * nframe + t;
            if (n % 2 == 0) {
                HEIGHT += k / 2 + t + 3;
            }
            else {
                HEIGHT -= k / 2 + t + 6;
            }
        } else {
            HEIGHT = 600;
        }

        //780 483

        myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
        myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(myPanel);
        saveload.getStateAtLoad(model, this);
        scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(scrollPane);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);

        ActionListener lClear = l -> {
            model.clearField();
            myPanel.stopGame();
            isPlaying = false;
            bPlay.setSelected(false);
            bStop.setSelected(true);
            myPanel.clearField();
        };

        ActionListener lSave = l -> {
            bPlay.setSelected(false);
            bStop.setSelected(true);
            myPanel.stopGame();
            isPlaying = false;
            saveload.save(this, model);
            myPanel.playGame();
        };

        ActionListener lOpen = l -> {
            bPlay.setSelected(false);
            bStop.setSelected(true);
            bImpacts.setSelected(false);
            impacts = false;
            myPanel.stopGame();
            isPlaying = false;
            try {
                if (saveload.shouldSave(model, this)) {
                    int result = JOptionPane.showConfirmDialog(this,
                            "Field have been changed.\n" +
                                    "Do you want to save current field?",
                            "Save?",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result != JOptionPane.CANCEL_OPTION) {
                        if (result == JOptionPane.YES_OPTION) {
                            saveload.save(this, model);
                            saveload.load(this, model);
                            myPanel.stopGame();
                            isPlaying = false;
                            n = model.getN();
                            m = model.getM();
                            semichord = (int)(k * Math.sqrt(3)) + 1;
                            if (semichord * m > 800) {
                                WIDTH = semichord * m - semichord + t;
                            } else {
                                WIDTH = 800;
                            }
                            nframe = n / 2;
                            if (n % 2 != 0) {
                                nframe += 1;
                            }
                            if (3 * k * nframe + t > 600) {
                                HEIGHT = 3 * k * nframe + t;
                                if (n % 2 == 0) {
                                    HEIGHT += k / 2 + t + 3;
                                }
                                else {
                                    HEIGHT -= k / 2 + t + 6;
                                }
                            } else {
                                HEIGHT = 600;
                            }
                            remove(scrollPane);
                            remove(myPanel);
                            myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
                            myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                            add(myPanel);
                            scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                            add(scrollPane);
                            saveload.getStateAtLoad(model, this);
                            myPanel.colorAliveCells();
                            myPanel.setImpacts(false);
                            xorMode = false;
                            bXor.setSelected(false);
                            bReplace.setSelected(true);
                            pack();
                        }
                        else if (result == JOptionPane.NO_OPTION) {
                            saveload.load(this, model);
                            n = model.getN();
                            m = model.getM();
                            semichord = (int)(k * Math.sqrt(3)) + 1;
                            if (semichord * m > 800) {
                                WIDTH = semichord * m - semichord + t;
                            } else {
                                WIDTH = 800;
                            }
                            nframe = n / 2;
                            if (n % 2 != 0) {
                                nframe += 1;
                            }
                            if (3 * k * nframe + t > 600) {
                                HEIGHT = 3 * k * nframe + t;
                                if (n % 2 == 0) {
                                    HEIGHT += k / 2 + t + 3;
                                }
                                else {
                                    HEIGHT -= k / 2 + t + 6;
                                }
                            } else {
                                HEIGHT = 600;
                            }
                            remove(scrollPane);
                            remove(myPanel);
                            myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
                            myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                            add(myPanel);
                            scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                            add(scrollPane);
                            saveload.getStateAtLoad(model, this);
                            myPanel.colorAliveCells();
                            myPanel.setImpacts(false);
                            xorMode = false;
                            bXor.setSelected(false);
                            bReplace.setSelected(true);
                            pack();
                        }
                    }
                    else {
                        myPanel.playGame();
                    }
                }
                else {
                    saveload.load(this, model);
                    myPanel.stopGame();
                    isPlaying = false;
                    n = model.getN();
                    m = model.getM();
                    semichord = (int)(k * Math.sqrt(3)) + 1;
                    if (semichord * m > 800) {
                        WIDTH = semichord * m - semichord + t;
                    } else {
                        WIDTH = 800;
                    }
                    nframe = n / 2;
                    if (n % 2 != 0) {
                        nframe += 1;
                    }
                    if (3 * k * nframe + t > 600) {
                        HEIGHT = 3 * k * nframe + t;
                        if (n % 2 == 0) {
                            HEIGHT += k / 2 + t + 3;
                        }
                        else {
                            HEIGHT -= k / 2 + t + 6;
                        }
                    } else {
                        HEIGHT = 600;
                    }
                    remove(scrollPane);
                    remove(myPanel);
                    myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
                    myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                    add(myPanel);
                    scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    add(scrollPane);
                    saveload.getStateAtLoad(model, this);
                    myPanel.colorAliveCells();
                    myPanel.setImpacts(false);
                    xorMode = false;
                    bXor.setSelected(false);
                    bReplace.setSelected(true);
                    pack();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Wrong .txt file! Check format!",
                        "Load error",
                        JOptionPane.ERROR_MESSAGE);
            }

        };

        ActionListener lOptions = l -> {
            boolean messageWarning = false;
            JPanel dialog = new JPanel();
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
            JSlider jSliderK = new JSlider(JSlider.HORIZONTAL,1,50,10);
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

            mainPanel.add(panelName);
            mainPanel.add(panelSliders);
            mainPanel.add(panelValues);
            mainPanel.add(panelBegin);
            mainPanel.add(panelValuesForBegin);
            mainPanel.add(panelEnd);
            mainPanel.add(panelValuesForEnd);
            dialog.add(mainPanel);

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
                    if(!mField.getText().isEmpty()) {
                        try {
                            Integer r = Integer.parseInt(mField.getText());
                            jSliderM.setValue(Integer.parseInt(mField.getText()));
                            if (r >= 100) {
                                mField.setText(r.toString());
                            }
                        } catch (NumberFormatException a) {

                        }
                    }
                }
            });

            nField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if(!nField.getText().isEmpty()) {
                        try {
                            Integer r = Integer.parseInt(nField.getText());
                            jSliderN.setValue(Integer.parseInt(nField.getText()));
                            if (r >= 100) {
                                nField.setText(r.toString());
                            }
                        } catch (NumberFormatException a) {

                        }
                    }
                }
            });

            kField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if(!kField.getText().isEmpty()) {
                        try {
                            Integer r = Integer.parseInt(kField.getText());
                            jSliderK.setValue(Integer.parseInt(kField.getText()));
                            if (r >= 100) {
                                kField.setText(r.toString());
                            }
                        } catch (NumberFormatException a) {

                        }
                    }
                }
            });

            tField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if(!tField.getText().isEmpty()) {
                        try {
                            Integer r = Integer.parseInt(tField.getText());
                            jSliderT.setValue(Integer.parseInt(tField.getText()));
                            if (r >= 100) {
                                tField.setText(r.toString());
                            }
                        } catch (NumberFormatException a) {

                        }
                    }
                }
            });

            xorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    xorMode = true;
                    bXor.setSelected(xorMode);
                    bReplace.setSelected(!xorMode);
                    jmXOR.setSelected(true);
                    jmReplace.setSelected(false);
                    myPanel.setXOR(xorMode);
                }
            });

            replaceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    xorMode = false;
                    bXor.setSelected(xorMode);
                    bReplace.setSelected(!xorMode);
                    jmXOR.setSelected(false);
                    jmReplace.setSelected(true);
                    myPanel.setXOR(xorMode);
                }
            });

            mainPanel.setPreferredSize(new Dimension(600, 250));
            dialog.setPreferredSize(new Dimension(600,250));
            dialog.setVisible(true);

            int res = JOptionPane.showConfirmDialog(null, dialog, "Settings",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res == JOptionPane.OK_OPTION) {
                if (!mField.getText().isEmpty()) {
                    int newM = Integer.parseInt(mField.getText());
                    if (newM > 100) {
                        messageWarning = true;
                    }
                    else
                    if (newM != m) {
                        m = newM;
                        isUpdating = true;
                    }

                }
                if (!nField.getText().isEmpty()) {
                    int newN = Integer.parseInt(nField.getText());
                    if (newN > 100) {
                        messageWarning = true;
                    }
                    else
                    if (newN != n) {
                        n = newN;
                        isUpdating = true;
                    }
                    if (n > 100) {
                        n = 6;

                    }
                }
                if (!kField.getText().isEmpty()) {
                    int newK = Integer.parseInt(kField.getText());
                    if (newK > 50 || newK < 6) {
                        messageWarning = true;
                    }
                    else
                    if (newK != k) {
                        k = newK;
                        isUpdating = true;
                    }
                }

                if (!tField.getText().isEmpty()) {
                    int newT = Integer.parseInt(tField.getText());
                    if (newT > 8) {
                        messageWarning = true;
                    }
                    else
                    if (newT != t) {
                        t = newT;
                        isUpdating = true;
                    }
                }

                if (!jFST.getText().isEmpty()) {
                    try {
                        FST_IMPACT = Double.parseDouble(jFST.getText().replace(",", "."));
                    } catch (NumberFormatException err) {
                        err.printStackTrace();
                    }

                }

                if (!jSND.getText().isEmpty()) {
                    try {
                        SND_IMPACT = Double.parseDouble(jSND.getText().replace(",", "."));
                    } catch (NumberFormatException err) {
                        err.printStackTrace();
                    }
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

                    } catch (NumberFormatException n) {
                        n.printStackTrace();
                    }
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
                    } catch (NumberFormatException n) {
                        n.printStackTrace();
                    }
                }
            }
            if (messageWarning) {
                JOptionPane.showMessageDialog(this, "You have set wrong parameters!,\n" +
                                "n must be [1..100]\n" +
                                "m must be [1..100]\n" +
                                "k must be [1..50]\n" +
                                "t must be [1..8]",
                        "Warning: wrong parameters", JOptionPane.WARNING_MESSAGE);
            }
            else {
                model.setParameters(LIVE_BEGIN, LIVE_END, BIRTH_BEGIN, BIRTH_END, FST_IMPACT, SND_IMPACT);
                if (isUpdating) {
                    isUpdating = false;
                    if (saveload.shouldSave(model, this)) {
                        int result = JOptionPane.showConfirmDialog(this,
                                "Field parameters have been changed.\n" +
                                        "Field will be set according to new parameters.\n" +
                                        "Do you want to save current field?",
                                "New",
                                JOptionPane.YES_NO_CANCEL_OPTION);
                        if (result != JOptionPane.CANCEL_OPTION) {
                            if (result == JOptionPane.YES_OPTION) {
                                saveload.save(this, model);
                            }
                            myPanel.stopGame();
                            isPlaying = false;
                            remove(scrollPane);
                            remove(myPanel);
                            model.resetField(n, m);
                            semichord = (int) (k * Math.sqrt(3)) + 1;
                            if (semichord * m > 800) {
                                WIDTH = semichord * m - semichord + t;
                            } else {
                                WIDTH = 800;
                            }
                            nframe = n / 2;
                            if (n % 2 != 0) {
                                nframe += 1;
                            }
                            if (3 * k * nframe + t > 600) {
                                HEIGHT = 3 * k * nframe + t;
                                if (n % 2 == 0) {
                                    HEIGHT += k / 2 + t + 3;
                                } else {
                                    HEIGHT -= k / 2 + t + 6;
                                }
                            } else {
                                HEIGHT = 600;
                            }
                            myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
                            myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                            add(myPanel);
                            scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                            add(scrollPane);
                            xorMode = false;
                            bXor.setSelected(false);
                            bReplace.setSelected(true);
                            bPlay.setSelected(false);
                            bStop.setSelected(true);
                            pack();
                        }
                    } else {
                        myPanel.stopGame();
                        isPlaying = false;
                        remove(scrollPane);
                        remove(myPanel);
                        model.resetField(n, m);
                        semichord = (int) (k * Math.sqrt(3)) + 1;
                        if (semichord * m > 800) {
                            WIDTH = semichord * m - semichord + t;
                        } else {
                            WIDTH = 800;
                        }
                        nframe = n / 2;
                        if (n % 2 != 0) {
                            nframe += 1;
                        }
                        if (3 * k * nframe + t > 600) {
                            HEIGHT = 3 * k * nframe + t;
                            if (n % 2 == 0) {
                                HEIGHT += k / 2 + t + 3;
                            } else {
                                HEIGHT -= k / 2 + t + 6;
                            }
                        } else {
                            HEIGHT = 600;
                        }
                        myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
                        myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                        add(myPanel);
                        scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                        add(scrollPane);
                        xorMode = false;
                        bXor.setSelected(false);
                        bReplace.setSelected(true);
                        bPlay.setSelected(false);
                        bStop.setSelected(true);
                        pack();
                    }
                }
            }
        };

        ActionListener lNew = l -> {
            int result = JOptionPane.showConfirmDialog(this,
                            "Field will be set according to new parameters N and M.\n" +
                            "Do you want to save current field?",
                    "New",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (result != JOptionPane.CANCEL_OPTION) {
                if (result == JOptionPane.YES_OPTION) {
                    myPanel.stopGame();
                    isPlaying = false;
                    saveload.save(this, model);
                }
                myPanel.stopGame();
                isPlaying = false;
                showNewDialog();
                remove(scrollPane);
                remove(myPanel);
                model.resetField(n, m);
                semichord = (int)(k * Math.sqrt(3)) + 1;
                if (semichord * m > 800) {
                    WIDTH = semichord * m - semichord + t;
                } else {
                    WIDTH = 800;
                }
                nframe = n / 2;
                if (n % 2 != 0) {
                    nframe += 1;
                }
                if (3 * k * nframe + t > 600) {
                    HEIGHT = 3 * k * nframe + t;
                    if (n % 2 == 0) {
                        HEIGHT += k / 2 + t + 3;
                    }
                    else {
                        HEIGHT -= k / 2 + t + 6;
                    }
                } else {
                    HEIGHT = 600;
                }
                myPanel = new MyPanel(n, m, k, t, WIDTH, HEIGHT, model);
                myPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
                add(myPanel);
                scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                add(scrollPane);
                xorMode = false;
                bXor.setSelected(false);
                bReplace.setSelected(true);
                bPlay.setSelected(false);
                bStop.setSelected(true);
                pack();
            }
        };

        ActionListener lAbout = l -> {
            JOptionPane.showMessageDialog(this, "Lab: FIT_16201_Boldyrev_Life\n" +
                            "Author:   Egor Boldyrev\n" +
                            "Made:  February 2019\n" +
                            "Thank you for playing!",
                       "About", JOptionPane.INFORMATION_MESSAGE);
        };

        ActionListener lXor = l -> {
            xorMode = true;
            bXor.setSelected(xorMode);
            bReplace.setSelected(!xorMode);
            jmXOR.setSelected(true);
            jmReplace.setSelected(false);
            myPanel.setXOR(xorMode);
        };

        ActionListener lReplace = l -> {
            xorMode = false;
            bXor.setSelected(xorMode);
            bReplace.setSelected(!xorMode);
            jmXOR.setSelected(false);
            jmReplace.setSelected(true);
            myPanel.setXOR(xorMode);
        };

        ActionListener lNext = l -> {
            if (!bPlay.isSelected()) {
                bStop.setSelected(true);
                bPlay.setSelected(false);
                myPanel.nextStep();
            }
        };

        ActionListener lImpacts = l -> {
            if (k >= 11) {
                impacts = !impacts;
                jmImpacts.setSelected(impacts);
                bImpacts.setSelected(impacts);
                myPanel.setImpacts(impacts);
                myPanel.drawImpacts(impacts);

            }
            else {
                JOptionPane.showMessageDialog(this, "Size of hexagon is too small to show impacts",
                        "Warning: small hexagon", JOptionPane.WARNING_MESSAGE);
                jmImpacts.setSelected(false);
                bImpacts.setSelected(false);
                myPanel.setImpacts(false);
            }
        };

        ActionListener lPlay = l -> {
            bPlay.setSelected(true);
            bStop.setSelected(false);
            if (!isPlaying) {
                myPanel.playGame();
                isPlaying = true;
            }
        };

        ActionListener lStop = l -> {
            bPlay.setSelected(false);
            bStop.setSelected(true);
            myPanel.stopGame();
            isPlaying = false;
        };

        bClear.addActionListener(lClear);
        jmClear.addActionListener(lClear);
        bSave.addActionListener(lSave);
        jmSave.addActionListener(lSave);
        bOpen.addActionListener(lOpen);
        jmOpen.addActionListener(lOpen);
        bOptions.addActionListener(lOptions);
        jmOptions.addActionListener(lOptions);
        bAbout.addActionListener(lAbout);
        jmAbout.addActionListener(lAbout);
        bNew.addActionListener(lNew);
        jmNew.addActionListener(lNew);
        bXor.addActionListener(lXor);
        jmXOR.addActionListener(lXor);
        bReplace.addActionListener(lReplace);
        jmReplace.addActionListener(lReplace);
        bNext.addActionListener(lNext);
        jmNext.addActionListener(lNext);
        bPlay.addActionListener(lPlay);
        jmPlay.addActionListener(lPlay);
        bStop.addActionListener(lStop);
        jmStop.addActionListener(lStop);
        bImpacts.addActionListener(lImpacts);
        jmImpacts.addActionListener(lImpacts);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (saveload.shouldSave(model, Frame.this)) {
                    int result = JOptionPane.showConfirmDialog(Frame.this,
                            "Do you want to save current field?",
                            "Save?",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result != JOptionPane.CANCEL_OPTION) {
                        if (result == JOptionPane.YES_OPTION) {
                            myPanel.stopGame();
                            isPlaying = false;
                            saveload.save(Frame.this, model);
                        }
                        System.exit(0);
                    }
                }
                else {
                    System.exit(0);
                }
            }
        });
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

    public void showNewDialog() {
        JDialog dialog = new JDialog(this, "New Field", true);
        IntegerFilter integerFilter = new IntegerFilter();

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        JPanel panelName = new JPanel(new GridLayout(3, 1));
        JPanel panelValues = new JPanel(new GridLayout(3, 1));

        JLabel nLabel = new JLabel("N", SwingConstants.CENTER);
        JTextField nField = new JTextField();
        PlainDocument docN = (PlainDocument) nField.getDocument();
        docN.setDocumentFilter(integerFilter);
        nField.setText(n.toString());
        panelName.add(nLabel);
        panelValues.add(nField);

        JLabel mLabel = new JLabel("M", SwingConstants.CENTER);
        JTextField mField = new JTextField();
        PlainDocument docM = (PlainDocument) mField.getDocument();
        docM.setDocumentFilter(integerFilter);
        mField.setText(m.toString());
        panelName.add(mLabel);
        panelValues.add(mField);

        JButton okButton = new JButton("OK");

        panelValues.add(okButton);

        mainPanel.add(panelName);
        mainPanel.add(panelValues);
        dialog.add(mainPanel);

        okButton.addActionListener(e-> {
            if (!mField.getText().isEmpty()) {
                int newM = Integer.parseInt(mField.getText());
                if (newM != m) {
                    m = newM;
                }
            }
            if (!nField.getText().isEmpty()) {
                int newN = Integer.parseInt(nField.getText());
                if (newN != n) {
                    n = newN;
                }
            }
            dialog.dispose();
        });

        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(300,100));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
