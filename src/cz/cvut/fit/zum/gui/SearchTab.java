package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.AlgorithmFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Tomas Barton
 */
public class SearchTab extends JPanel implements AlgorithmListener{

    private JButton stopButton;
    private JButton resetButton;
    private JComboBox algBox;
    private JPanel statsPanel;
    private JLabel lbNodes;
    private JLabel lbExpand;
    private JLabel lbDist;
    private JLabel lbTime;
    private String frmNodes;
    private String frmExpand;
    private String frmDist;
    private String frmDelay;
    private String frmTime;
    private JButton test1;
    private JButton test2;
    private JButton test3;
    private JButton test4;
    private JSlider delaySlider;
    private JLabel lbAlg;
    private MapPanel mapPanel;

    public SearchTab(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0D;
        c.weighty = 1.0D;
        c.insets = new Insets(5, 5, 5, 5);
        //stop button
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopButton.setEnabled(false);
                mapPanel.stopSearch();
            }
        });
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;
        this.add(stopButton, c);
        //reset button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.resetPath();
            }
        });
        c.gridy = 1;
        this.add(resetButton, c);

        lbAlg = new JLabel("Algorithm:");
        c.gridx = 1;
        c.gridy = 0;
        this.add(lbAlg, c);
        algBox = new JComboBox();
        AlgorithmFactory af = AlgorithmFactory.getDefault();
        List<String> providers = af.getProviders();
        for (String p : providers) {
            algBox.addItem(p);
        }
        c.gridy = 1;
        this.add(algBox, c);
        algBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.algorithmChanged(algBox.getSelectedItem().toString());
            }
        });
        
          //set current algorithm
        mapPanel.algorithmChanged(algBox.getSelectedItem().toString());

        frmNodes = "Explored nodes: %4d";
        lbNodes = new JLabel(String.format(frmNodes, 0));
        frmExpand = "Expanded nodes: %d (%4.1f%%)";
        lbExpand = new JLabel(String.format(frmExpand, 0, 0.0));
        frmDist = "Distance: %10.2f";
        lbDist = new JLabel(String.format(frmDist, 0.0));
        frmTime = "Time: %10.0f ms";
        lbTime = new JLabel(String.format(frmTime, 0.0));
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.PAGE_AXIS));
        statsPanel.add(lbNodes);
        statsPanel.add(lbExpand);
        statsPanel.add(lbDist);
        statsPanel.add(lbTime);
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        this.add(statsPanel, c);
        mapPanel.addStatsListener(this);
        validate();

        delaySlider = new JSlider(SwingConstants.HORIZONTAL, 0, 200, 3);
        delaySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!delaySlider.getValueIsAdjusting()) {
                    mapPanel.setDelay((long) delaySlider.getValue());
                }
                lbAlg.setText(String.format(frmDelay, (int) delaySlider.getValue()));
            }
        });
        c.gridx = 3;
        c.gridy = 1;
        this.add(delaySlider, c);
        mapPanel.setDelay((long) delaySlider.getValue());

        frmDelay = "Delay: %4d ms";
        lbAlg = new JLabel(String.format(frmDelay, (int) delaySlider.getValue()));
        c.fill = GridBagConstraints.NONE;
        c.gridx = 3;
        c.gridy = 0;
        this.add(lbAlg, c);

        test1 = new JButton("Test 1");
        test1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.test1Search();
            }
        });
        c.gridx = 4;
        c.gridy = 0;
        this.add(test1, c);

        test2 = new JButton("Test 2");
        test2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.test2Search();
            }
        });
        c.gridx = 4;
        c.gridy = 1;
        this.add(test2, c);

        test3 = new JButton("Test 3");
        test3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.test3Search();
            }
        });
        c.gridx = 5;
        c.gridy = 0;
        this.add(test3, c);


        test4 = new JButton("Test 4");
        test4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.test4Search();
            }
        });
        c.gridx = 5;
        c.gridy = 1;
        this.add(test4, c);
        
    }
    
    
    @Override
    public void statsChanged(HashMap<String, Double> stats) {
        double v = stats.get("explored");
        lbNodes.setText(String.format(frmNodes, (int) v));
        v = stats.get("expanded");
        lbExpand.setText(String.format(frmExpand, (int) v, stats.get("coverage")));
        v = stats.get("distance");
        lbDist.setText(String.format(frmDist, v));
        v = stats.get("time");
        lbTime.setText(String.format(frmTime, v));
    }

    @Override
    public void searchStarted() {
        stopButton.setEnabled(true);
    }

    @Override
    public void searchFinished() {
        stopButton.setEnabled(false);
    }
}
