package ru.nsu.fit.g16201.boldyrev.view.frame;

import ru.nsu.fit.g16201.boldyrev.filters.MonochromeFilter;
import ru.nsu.fit.g16201.boldyrev.filters.NegativeFilter;
import ru.nsu.fit.g16201.boldyrev.filters.WatercoloringFilter;
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
        JButton bReplace = createJButton("Replace");
        JButton bXor = createJButton("Xor");
        JButton bImpacts = createJButton("Impacts");

        toolBar.add(bNew);
        toolBar.add(bOpen);
        toolBar.add(bSave);
        toolBar.add(new JToolBar.Separator());
        toolBar.add(bReplace);
        toolBar.add(bXor);
        toolBar.add(bImpacts);
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
            BufferedImage result = NegativeFilter.filterNegative(source);
            myPanel.setZoneC(result);
        };

        ActionListener lMonochrome = l -> {
            MyImage source = myPanel.getZoneA();
            BufferedImage result = MonochromeFilter.filterMonochrome(source);
            myPanel.setZoneC(result);
        };

        ActionListener lWatercolor = l -> {
            MyImage source = myPanel.getZoneA();
            BufferedImage result = WatercoloringFilter.filterWatercolor(source);
            myPanel.setZoneC(result);
        };

        bOpen.addActionListener(lOpen);
        bReplace.addActionListener(lNegative);
        bXor.addActionListener(lMonochrome);
        bImpacts.addActionListener(lWatercolor);
    }

    private JButton createJButton(String iconName) {
        JButton button = new JButton(new ImageIcon("src/resources/" + iconName + ".png"));
        button.setToolTipText(iconName);
        return button;
    }
}
