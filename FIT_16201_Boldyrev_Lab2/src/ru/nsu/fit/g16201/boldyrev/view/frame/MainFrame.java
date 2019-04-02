package ru.nsu.fit.g16201.boldyrev.view.frame;

import ru.nsu.fit.g16201.boldyrev.filters.*;
import ru.nsu.fit.g16201.boldyrev.view.IntegerFilter;
import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;
import ru.nsu.fit.g16201.boldyrev.view.panels.MyPanel;
import ru.nsu.fit.g16201.boldyrev.view.utils.SaveLoad;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    private final int HEIGHT = 600;
    private final int WIDTH = 1200;
    private final int MIN_HEIGHT = 600;
    private final int MIN_WIDTH = 1000;

    private MyPanel myPanel;
    private JMenuBar menuBar;
    private JScrollPane scrollPane;
    private JToolBar toolBar;

    private boolean isSelected = false;
    private boolean shouldSave = false;

    public MainFrame() {
        SaveLoad saveLoad = new SaveLoad();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        setTitle("Filters");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        /*create toolbar*/
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton bNew = createJButton("New");
        JButton bOpen = createJButton("Open");
        JButton bSave = createJButton("Save");
        JButton bBC = createJButton("B->C");
        JButton bCB = createJButton("C->B");

        JToggleButton bSelect = new JToggleButton(new ImageIcon("src/resources/Select.png"));
        bSelect.setToolTipText("Select");
        bSelect.setSelected(isSelected);

        JButton bNegative = createJButton("Negative");
        JButton bMonochrome = createJButton("Monochrome");
        JButton bWatercolor = createJButton("Watercolor");
        JButton bGamma = createJButton("Gamma");
        JButton bSharper = createJButton("Sharper");
        JButton bStamping = createJButton("Stamping");
        JButton bMagnifier = createJButton("Magnifier");
        JButton bBlur = createJButton("Blur");
        JButton bRotate = createJButton("Rotate");
        JButton bAbout = createJButton("About");
        JButton bRobert = createJButton("Robert");
        JButton bSobel = createJButton("Sobel");
        JButton bFS = createJButton("FS");
        JButton bOD = createJButton("ODF");

        toolBar.add(bNew);
        toolBar.add(bOpen);
        toolBar.add(bSave);
        toolBar.add(bBC);
        toolBar.add(bCB);
        toolBar.add(bSelect);
        toolBar.add(bNegative);
        toolBar.add(bMonochrome);
        toolBar.add(bWatercolor);
        toolBar.add(bGamma);
        toolBar.add(bSharper);
        toolBar.add(bStamping);
        toolBar.add(bMagnifier);
        toolBar.add(bBlur);
        toolBar.add(bRotate);
        toolBar.add(bRobert);
        toolBar.add(bSobel);
        toolBar.add(bFS);
        toolBar.add(bOD);
        toolBar.add(bAbout);
        add(toolBar, BorderLayout.NORTH);
        /*end of creating toolbar*/

        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem jmNew = new JMenuItem("New");
        fileMenu.add(jmNew);
        JMenuItem jmOpen = new JMenuItem("Open");
        fileMenu.add(jmOpen);
        JMenuItem jmSave = new JMenuItem("Save");
        fileMenu.add(jmSave);
        fileMenu.addSeparator();
        JMenuItem jmExit= new JMenuItem("Exit");
        fileMenu.add(jmExit);

        JMenu manipulationMenu = new JMenu("Picture manipulation");
        JMenuItem jmBC = new JMenuItem("Move from B to C");
        manipulationMenu.add(jmBC);
        JMenuItem jmCB = new JMenuItem("Move from C to B");
        manipulationMenu.add(jmCB);
        JMenuItem jmSelect = new JMenuItem("Select");
        manipulationMenu.add(jmSelect);

        JMenu filterMenu = new JMenu("Filters");
        JMenuItem jmNegative = new JMenuItem("Negative");
        filterMenu.add(jmNegative);
        JMenuItem jmMonochrome = new JMenuItem("Monochrome");
        filterMenu.add(jmMonochrome);
        JMenuItem jmWaterColor = new JMenuItem("Watercolor");
        filterMenu.add(jmWaterColor);
        JMenuItem jmGamma = new JMenuItem("Gamma");
        filterMenu.add(jmGamma);
        JMenuItem jmSharp = new JMenuItem("Sharp");
        filterMenu.add(jmSharp);
        JMenuItem jmStamp = new JMenuItem("Stamp");
        filterMenu.add(jmStamp);
        JMenuItem jmMaginy = new JMenuItem("Magnify");
        filterMenu.add(jmMaginy);
        JMenuItem jmBlur = new JMenuItem("Blur");
        filterMenu.add(jmBlur);
        JMenuItem jmRotate = new JMenuItem("Rotate");
        filterMenu.add(jmRotate);
        JMenuItem jmRobert = new JMenuItem("Robert");
        filterMenu.add(jmRobert);
        JMenuItem jmSobel = new JMenuItem("Sobel");
        filterMenu.add(jmSobel);
        JMenuItem jmFS = new JMenuItem("FS");
        filterMenu.add(jmFS);
        JMenuItem jmODF = new JMenuItem("ODF");
        filterMenu.add(jmODF);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem jmAbout = new JMenuItem("About");
        helpMenu.add(jmAbout);

        menuBar.add(fileMenu);
        menuBar.add(manipulationMenu);
        menuBar.add(filterMenu);
        menuBar.add(helpMenu);

        myPanel = new MyPanel();
        scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        add(myPanel);
        setJMenuBar(menuBar);

        pack();
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);

        ActionListener lOpen = l -> {
            myPanel.clearZoneB();
            myPanel.clearZoneC();
            boolean flag = saveLoad.load(this, myPanel);
            if (flag) {
                myPanel.resetSelectZone();
            }
        };

        ActionListener lSelect = l -> {
            isSelected = !isSelected;
            bSelect.setSelected(!isSelected);
            myPanel.setIsSelected(!isSelected);
            if (!isSelected) {
                myPanel.resetSelectZone();
            }
        };

        ActionListener lOD = l -> {
            if (myPanel.getZoneB().getImage() != null) {
                MyImage source = myPanel.getZoneB();
                ODFilter f = new ODFilter(2, 2, 2);
                BufferedImage result = f.filterOD(source);
                myPanel.setZoneC(result);
                shouldSave = true;
            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lSave = l -> {
            saveLoad.save(this, myPanel);
        };

        ActionListener lNew = l -> {
            myPanel.clearAll();
        };

        ActionListener lBC = l -> {
            if (myPanel.getZoneB().getImage() != null) {
                myPanel.BC();
            }
        };

        ActionListener lCB = l -> {
            if (myPanel.getZoneC().getImage() != null) {
                myPanel.CB();
            }
        };

        ActionListener lFS = l -> {
            Integer coeffRed = 7;
            Integer coeffGreen = 7;
            Integer coeffBlue = 7;

            if (myPanel.getZoneB().getImage() != null) {
                MyImage source = myPanel.getZoneB();
                FSFilter f = new FSFilter(coeffRed, coeffGreen, coeffBlue);
                BufferedImage result = f.filterFS(source, coeffRed, coeffGreen, coeffBlue);
                myPanel.setZoneC(result);

                JPanel dialog = new JPanel();
                JPanel panel = new JPanel(new GridLayout(1, 3));
                JPanel panelColors = new JPanel(new GridLayout(3,1));
                JPanel panelValues = new JPanel(new GridLayout(3,1));
                JPanel panelSliders = new JPanel(new GridLayout(3,1));
                IntegerFilter integerFilter = new IntegerFilter();

                JLabel redName = new JLabel("Red", SwingConstants.CENTER);
                JLabel labelRed = new JLabel(String.valueOf(coeffRed), SwingConstants.CENTER);
                JTextField fieldRed = new JTextField();
                PlainDocument docRed = (PlainDocument) fieldRed.getDocument();
                docRed.setDocumentFilter(integerFilter);
                fieldRed.setText(coeffRed.toString());
                panelColors.add(redName);
                panelValues.add(labelRed);

                JLabel greenName = new JLabel("Red", SwingConstants.CENTER);
                JLabel labelGreen = new JLabel(String.valueOf(coeffGreen), SwingConstants.CENTER);
                JTextField fieldGreen = new JTextField();
                PlainDocument docN = (PlainDocument) fieldGreen.getDocument();
                docN.setDocumentFilter(integerFilter);
                fieldGreen.setText(coeffGreen.toString());
                panelColors.add(greenName);
                panelValues.add(labelGreen);

                JLabel blueName = new JLabel("Red", SwingConstants.CENTER);
                JLabel labelBlue = new JLabel(String.valueOf(coeffBlue), SwingConstants.CENTER);
                JTextField fieldBlue = new JTextField();
                PlainDocument docBlue = (PlainDocument) fieldBlue.getDocument();
                docN.setDocumentFilter(integerFilter);
                fieldBlue.setText(coeffBlue.toString());
                panelColors.add(blueName);
                panelValues.add(labelBlue);

                JSlider sliderRed = new JSlider(JSlider.HORIZONTAL, 1, 7, coeffRed);
                sliderRed.setMajorTickSpacing(1);
                sliderRed.setMinorTickSpacing(5);
                sliderRed.setPaintTicks(true);
                sliderRed.setPaintLabels(true);
                panelSliders.add(sliderRed);

                JSlider sliderGreen = new JSlider(JSlider.HORIZONTAL, 1, 7, coeffGreen);
                sliderGreen.setMajorTickSpacing(1);
                sliderGreen.setMinorTickSpacing(5);
                sliderGreen.setPaintTicks(true);
                sliderGreen.setPaintLabels(true);
                panelSliders.add(sliderGreen);

                JSlider sliderBlue = new JSlider(JSlider.HORIZONTAL, 1, 7, coeffBlue);
                sliderBlue.setMajorTickSpacing(1);
                sliderBlue.setMinorTickSpacing(5);
                sliderBlue.setPaintTicks(true);
                sliderBlue.setPaintLabels(true);
                panelSliders.add(sliderBlue);

                panel.add(panelColors);
                panel.add(panelValues);
                panel.add(panelSliders);

                sliderRed.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent changeEvent) {
                        new Thread(() -> {
                            if (myPanel.getZoneB().getImage() != null) {
                                MyImage source = myPanel.getZoneB();
                                FSFilter f = new FSFilter(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue());
                                BufferedImage result = f.filterFS(source, sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue());
                                myPanel.setZoneC(result);
                                labelRed.setText(String.valueOf(sliderRed.getValue()));
                            } else {
                                JOptionPane.showMessageDialog(MainFrame.this, "Warning: zone B is empty.\n" +
                                                "Please select something from zone A.",
                                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }).start();
                    }
                });

                ChangeListener changeListener = new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent changeEvent) {
                        new Thread(() -> {
                            if (myPanel.getZoneB().getImage() != null) {
                                MyImage source = myPanel.getZoneB();
                                FSFilter f = new FSFilter(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue());
                                BufferedImage result = f.filterFS(source, sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue());
                                myPanel.setZoneC(result);
                                labelRed.setText(String.valueOf(sliderRed.getValue()));
                                labelGreen.setText(String.valueOf(sliderGreen.getValue()));
                                labelBlue.setText(String.valueOf(sliderBlue.getValue()));
                            }
                        }).start();
                    }
                };

                sliderRed.addChangeListener(changeListener);
                sliderGreen.addChangeListener(changeListener);
                sliderBlue.addChangeListener(changeListener);

                dialog.add(panel);

                int res = JOptionPane.showConfirmDialog(null, dialog, "Settings",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    shouldSave = true;
                    JOptionPane.getRootFrame().dispose();
                }
                else {
                    myPanel.BC();
                    JOptionPane.getRootFrame().dispose();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lNegative = l -> {
            if (myPanel.getZoneB().getImage() != null) {
                MyImage source = myPanel.getZoneB();
                NegativeFilter f = new NegativeFilter();
                BufferedImage result = f.filterNegative(source);
                myPanel.setZoneC(result);
                shouldSave = true;

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                        "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lMonochrome = l -> {
            if (myPanel.getZoneB().getImage() != null) {
            MyImage source = myPanel.getZoneB();
            MonochromeFilter f = new MonochromeFilter();
            BufferedImage result = f.filterMonochrome(source);
            myPanel.setZoneC(result);
                shouldSave = true;

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lWatercolor = l -> {
            if (myPanel.getZoneB().getImage() != null) {
            MyImage source = myPanel.getZoneB();
            WatercoloringFilter f = new WatercoloringFilter();
            BufferedImage result = f.filterWatercolor(source);
            myPanel.setZoneC(result);
            shouldSave = true;

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lGamma = l -> {
            Integer coeff = 30;

            if (myPanel.getZoneB().getImage() != null) {
                MyImage source = myPanel.getZoneB();
                GammaFilter f = new GammaFilter(coeff);
                BufferedImage result = f.filterGamma(source);
                myPanel.setZoneC(result);

                JPanel dialog = new JPanel();
                JPanel panel = new JPanel(new GridLayout(1, 2));
                IntegerFilter integerFilter = new IntegerFilter();

                JLabel label = new JLabel(String.valueOf(coeff), SwingConstants.CENTER);
                JTextField field = new JTextField();
                PlainDocument docN = (PlainDocument) field.getDocument();
                docN.setDocumentFilter(integerFilter);
                field.setText(coeff.toString());
                panel.add(label);

                JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, coeff);
                slider.setMajorTickSpacing(100);
                slider.setMinorTickSpacing(20);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                panel.add(slider);

                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent changeEvent) {
                        new Thread(() -> {
                            if (myPanel.getZoneB().getImage() != null) {
                                MyImage source = myPanel.getZoneB();
                                GammaFilter f = new GammaFilter(slider.getValue());
                                BufferedImage result = f.filterGamma(source);
                                myPanel.setZoneC(result);
                                label.setText(String.valueOf(slider.getValue()));
                            } else {
                                JOptionPane.showMessageDialog(MainFrame.this, "Warning: zone B is empty.\n" +
                                                "Please select something from zone A.",
                                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }).start();
                    }
                });

                dialog.add(panel);

                int res = JOptionPane.showConfirmDialog(null, dialog, "Settings",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    shouldSave = true;

                    JOptionPane.getRootFrame().dispose();
                }
                else {
                    myPanel.BC();
                    JOptionPane.getRootFrame().dispose();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lAbout = l -> {
            JOptionPane.showMessageDialog(this, "Lab: FIT_16201_Boldyrev_Filter\n" +
                            "Author:   Egor Boldyrev\n" +
                            "Made:  March 2019\n" +
                            "HONK HONK :)",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        };

        ActionListener lSharp = l -> {
            if (myPanel.getZoneB().getImage() != null) {
            MyImage source = myPanel.getZoneB();
            SharpingFilter f = new SharpingFilter();
            BufferedImage result = f.filterSharpering(source);
            myPanel.setZoneC(result);
                shouldSave = true;

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lStamp = l -> {
            if (myPanel.getZoneB().getImage() != null) {
            MyImage source = myPanel.getZoneB();
            StampingFilter f = new StampingFilter();
            BufferedImage result = f.filterStamping(source);
            myPanel.setZoneC(result);
                shouldSave = true;

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lMagnifier = l -> {
            if (myPanel.getZoneB().getImage() != null) {
            MyImage source = myPanel.getZoneB();
            MagnifierFilter f = new MagnifierFilter();
            BufferedImage result = f.filterMagnify(source);
            myPanel.setZoneC(result);
                shouldSave = true;

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lBlur = l -> {
            if (myPanel.getZoneB().getImage() != null) {
            MyImage source = myPanel.getZoneB();
            BlurFilter f = new BlurFilter();
            BufferedImage result = f.filterBlur(source);
            myPanel.setZoneC(result);
                shouldSave = true;

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lRotate = l -> {
            Integer coeff = 0;

            if (myPanel.getZoneB().getImage() != null) {
                MyImage source = myPanel.getZoneB();
                RotateFilter f = new RotateFilter(coeff);
                BufferedImage result = f.filterRotate(source);
                myPanel.setZoneC(result);

                JPanel dialog = new JPanel();
                JPanel panel = new JPanel(new GridLayout(1, 2));
                IntegerFilter integerFilter = new IntegerFilter();

                JLabel label = new JLabel(String.valueOf(coeff), SwingConstants.CENTER);
                JTextField field = new JTextField();
                PlainDocument docN = (PlainDocument) field.getDocument();
                docN.setDocumentFilter(integerFilter);
                field.setText(coeff.toString());
                panel.add(label);

                JSlider slider = new JSlider(JSlider.HORIZONTAL, -180, 180, coeff);
                slider.setMajorTickSpacing(100);
                slider.setMinorTickSpacing(20);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                panel.add(slider);

                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent changeEvent) {
                        new Thread(() -> {
                            if (myPanel.getZoneB().getImage() != null) {
                                MyImage source = myPanel.getZoneB();
                                RotateFilter f = new RotateFilter(slider.getValue());
                                BufferedImage result = f.filterRotate(source);
                                myPanel.setZoneC(result);
                                label.setText(String.valueOf(slider.getValue()));
                            } else {
                                JOptionPane.showMessageDialog(MainFrame.this, "Warning: zone B is empty.\n" +
                                                "Please select something from zone A.",
                                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }).start();
                    }
                });

                dialog.add(panel);

                int res = JOptionPane.showConfirmDialog(null, dialog, "Settings",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    shouldSave = true;

                    JOptionPane.getRootFrame().dispose();
                }
                else {
                    myPanel.BC();
                    JOptionPane.getRootFrame().dispose();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lRobert = l -> {
            Integer coeff = 100;

            if (myPanel.getZoneB().getImage() != null) {
                MyImage source = myPanel.getZoneB();
                RobertsFilter f = new RobertsFilter(coeff);
                BufferedImage result = f.filterRobert(source);
                myPanel.setZoneC(result);

                JPanel dialog = new JPanel();
                JPanel panel = new JPanel(new GridLayout(1, 2));
                IntegerFilter integerFilter = new IntegerFilter();

                JLabel label = new JLabel(String.valueOf(coeff), SwingConstants.CENTER);
                JTextField field = new JTextField();
                PlainDocument docN = (PlainDocument) field.getDocument();
                docN.setDocumentFilter(integerFilter);
                field.setText(coeff.toString());
                panel.add(label);

                JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, coeff);
                slider.setMajorTickSpacing(100);
                slider.setMinorTickSpacing(20);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                panel.add(slider);

                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent changeEvent) {
                        new Thread(() -> {
                            if (myPanel.getZoneB().getImage() != null) {
                                MyImage source = myPanel.getZoneB();
                                RobertsFilter f = new RobertsFilter(slider.getValue());
                                BufferedImage result = f.filterRobert(source);
                                myPanel.setZoneC(result);
                                label.setText(String.valueOf(slider.getValue()));
                            } else {
                                JOptionPane.showMessageDialog(MainFrame.this, "Warning: zone B is empty.\n" +
                                                "Please select something from zone A.",
                                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }).start();
                    }
                });

                dialog.add(panel);

                int res = JOptionPane.showConfirmDialog(null, dialog, "Settings",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    shouldSave = true;

                    JOptionPane.getRootFrame().dispose();
                }
                else {
                    myPanel.BC();
                    JOptionPane.getRootFrame().dispose();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lSobel = l -> {
            Integer coeff = 100;

            if (myPanel.getZoneB().getImage() != null) {
                MyImage source = myPanel.getZoneB();
                SobelFilter f = new SobelFilter(coeff);
                BufferedImage result = f.filterSobel(source);
                myPanel.setZoneC(result);

                JPanel dialog = new JPanel();
                JPanel panel = new JPanel(new GridLayout(1, 2));
                IntegerFilter integerFilter = new IntegerFilter();

                JLabel label = new JLabel(String.valueOf(coeff), SwingConstants.CENTER);
                JTextField field = new JTextField();
                PlainDocument docN = (PlainDocument) field.getDocument();
                docN.setDocumentFilter(integerFilter);
                field.setText(coeff.toString());
                panel.add(label);

                JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, coeff);
                slider.setMajorTickSpacing(100);
                slider.setMinorTickSpacing(20);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                panel.add(slider);

                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent changeEvent) {
                        new Thread(() -> {
                            if (myPanel.getZoneB().getImage() != null) {
                                MyImage source = myPanel.getZoneB();
                                SobelFilter f = new SobelFilter(slider.getValue());
                                BufferedImage result = f.filterSobel(source);
                                myPanel.setZoneC(result);
                                label.setText(String.valueOf(slider.getValue()));
                            } else {
                                JOptionPane.showMessageDialog(MainFrame.this, "Warning: zone B is empty.\n" +
                                                "Please select something from zone A.",
                                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }).start();
                    }
                });

                dialog.add(panel);

                int res = JOptionPane.showConfirmDialog(null, dialog, "Settings",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    shouldSave = true;

                    JOptionPane.getRootFrame().dispose();
                }
                else {
                    myPanel.BC();
                    JOptionPane.getRootFrame().dispose();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Warning: zone B is empty.\n" +
                                "Please select something from zone A.",
                        "Empty ZoneB", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        ActionListener lExit = l -> {
            if (shouldSave) {
                int result = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Do you want to save changed picture?",
                        "Save?",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (result != JOptionPane.CANCEL_OPTION) {
                    if (result == JOptionPane.YES_OPTION) {

                        saveLoad.save(MainFrame.this, myPanel);
                    }
                    System.exit(0);
                }
            }
            else {
                System.exit(0);
            }
        };

        bOpen.addActionListener(lOpen);
        bSelect.addActionListener(lSelect);
        bNew.addActionListener(lNew);
        bSave.addActionListener(lSave);
        bNegative.addActionListener(lNegative);
        bMonochrome.addActionListener(lMonochrome);
        bWatercolor.addActionListener(lWatercolor);
        bGamma.addActionListener(lGamma);
        bSharper.addActionListener(lSharp);
        bStamping.addActionListener(lStamp);
        bMagnifier.addActionListener(lMagnifier);
        bBlur.addActionListener(lBlur);
        bRotate.addActionListener(lRotate);
        bAbout.addActionListener(lAbout);
        bBC.addActionListener(lBC);
        bCB.addActionListener(lCB);
        bRobert.addActionListener(lRobert);
        bSobel.addActionListener(lSobel);
        bFS.addActionListener(lFS);
        bOD.addActionListener(lOD);

        jmOpen.addActionListener(lOpen);
        jmSelect.addActionListener(lSelect);
        jmNew.addActionListener(lNew);
        jmSave.addActionListener(lSave);
        jmNegative.addActionListener(lNegative);
        jmMonochrome.addActionListener(lMonochrome);
        jmWaterColor.addActionListener(lWatercolor);
        jmGamma.addActionListener(lGamma);
        jmSharp.addActionListener(lSharp);
        jmStamp.addActionListener(lStamp);
        jmMaginy.addActionListener(lMagnifier);
        jmBlur.addActionListener(lBlur);
        jmRotate.addActionListener(lRotate);
        jmAbout.addActionListener(lAbout);
        jmBC.addActionListener(lBC);
        jmCB.addActionListener(lCB);
        jmRobert.addActionListener(lRobert);
        jmSobel.addActionListener(lSobel);
        jmFS.addActionListener(lFS);
        jmODF.addActionListener(lOD);
        jmExit.addActionListener(lExit);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (shouldSave) {
                    int result = JOptionPane.showConfirmDialog(MainFrame.this,
                            "Do you want to save changed picture?",
                            "Save?",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result != JOptionPane.CANCEL_OPTION) {
                        if (result == JOptionPane.YES_OPTION) {

                            saveLoad.save(MainFrame.this, myPanel);
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

    private JButton createJButton(String iconName) {
        JButton button = new JButton(new ImageIcon("src/resources/" + iconName + ".png"));
        button.setToolTipText(iconName);
        return button;
    }
}
