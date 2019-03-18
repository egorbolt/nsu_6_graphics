package ru.nsu.fit.g16201.boldyrev.view.frame;

import ru.nsu.fit.g16201.boldyrev.filters.*;
import ru.nsu.fit.g16201.boldyrev.view.panels.MyImage;
import ru.nsu.fit.g16201.boldyrev.view.panels.MyPanel;
import ru.nsu.fit.g16201.boldyrev.view.utils.SaveLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    private final int HEIGHT = 800;
    private final int WIDTH = 1200;
    private final int MIN_HEIGHT = 600;
    private final int MIN_WIDTH = 1000;

    private MyPanel myPanel;
    private JScrollPane scrollPane;
    private JToolBar toolBar;

    public MainFrame() {
        SaveLoad saveLoad = new SaveLoad();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setLayout(new BorderLayout());
        setTitle("Filters");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*create toolbar*/
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton bNew = createJButton("New");
        JButton bOpen = createJButton("Open");
        JButton bSave = createJButton("Save");
        JButton bNegative = createJButton("Negative");
        JButton bMonochrome = createJButton("Monochrome");
        JButton bWatercolor = createJButton("Watercolor");
        JButton bGamma = createJButton("Gamma");
        JButton bSharper = createJButton("Sharper");
        JButton bStamping = createJButton("Stamping");
        JButton bMagnifier = createJButton("Magnifier");
        JButton bBlur = createJButton("Blur");

        toolBar.add(bNew);
        toolBar.add(bOpen);
        toolBar.add(bSave);
        toolBar.add(bNegative);
        toolBar.add(bMonochrome);
        toolBar.add(bWatercolor);
        toolBar.add(bGamma);
        toolBar.add(bSharper);
        toolBar.add(bStamping);
        toolBar.add(bMagnifier);
        toolBar.add(bBlur);
        add(toolBar, BorderLayout.NORTH);
        /*end of creating toolbar*/

        myPanel = new MyPanel();
        scrollPane = new JScrollPane(myPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
        add(myPanel);

        pack();
        setVisible(true);
        setResizable(true);
        setLocationRelativeTo(null);

        ActionListener lOpen = l -> {
            saveLoad.load(this, myPanel);
        };

        ActionListener lNegative = l -> {
            MyImage source = myPanel.getZoneA();
            NegativeFilter f = new NegativeFilter();
            BufferedImage result = f.filterNegative(source);
            myPanel.setZoneC(result);
        };

        ActionListener lMonochrome = l -> {
            MyImage source = myPanel.getZoneA();
            MonochromeFilter f = new MonochromeFilter();
            BufferedImage result = f.filterMonochrome(source);
            myPanel.setZoneC(result);
        };

        ActionListener lWatercolor = l -> {
            MyImage source = myPanel.getZoneA();
            WatercoloringFilter f = new WatercoloringFilter();
            BufferedImage result = f.filterWatercolor(source);
            myPanel.setZoneC(result);
        };

        ActionListener lGamma = l -> {
            MyImage source = myPanel.getZoneA();
            GammaFilter f = new GammaFilter(50);
            BufferedImage result = f.filterGamma(source);
            myPanel.setZoneC(result);
        };

        ActionListener lSharp = l -> {
            MyImage source = myPanel.getZoneA();
            SharpingFilter f = new SharpingFilter();
            BufferedImage result = f.filterSharpering(source);
            myPanel.setZoneC(result);
        };

        ActionListener lStamp = l -> {
            MyImage source = myPanel.getZoneA();
            StampingFilter f = new StampingFilter();
            BufferedImage result = f.filterStamping(source);
            myPanel.setZoneC(result);
        };

        ActionListener lMagnifier = l -> {
            MyImage source = myPanel.getZoneA();
            MagnifierFilter f = new MagnifierFilter();
            BufferedImage result = f.filterMagnify(source);
            myPanel.setZoneC(result);
        };

        ActionListener lBlur = l -> {
            MyImage source = myPanel.getZoneA();
            BlurFilter f = new BlurFilter();
            BufferedImage result = f.filterBlur(source);
            myPanel.setZoneC(result);
        };

        bOpen.addActionListener(lOpen);
        bNegative.addActionListener(lNegative);
        bMonochrome.addActionListener(lMonochrome);
        bWatercolor.addActionListener(lWatercolor);
        bGamma.addActionListener(lGamma);
        bSharper.addActionListener(lSharp);
        bStamping.addActionListener(lStamp);
        bMagnifier.addActionListener(lMagnifier);
        bBlur.addActionListener(lBlur);
    }

    private JButton createJButton(String iconName) {
        JButton button = new JButton(new ImageIcon("src/resources/" + iconName + ".png"));
        button.setToolTipText(iconName);
        return button;
    }
}
