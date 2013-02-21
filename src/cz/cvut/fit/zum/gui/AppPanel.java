package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.AlgorithmFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import cz.cvut.fit.zum.data.Nodes;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Tomas Barton
 */
public class AppPanel extends JPanel implements AlgorithmListener {

    private static final long serialVersionUID = -8338182648645369875L;
    private JButton stopButton;
    private JButton resetButton;
    private JPanel buttonPanel;
    private JComboBox algBox;
    private MapPanel mapPanel;
    private JPanel statsPanel;
    private JLabel lbNodes;
    private JLabel lbExpand;
    private JLabel lbDist;
    private String frmNodes;
    private String frmExpand;
    private String frmDist;
    private String frmDelay;
    private JButton test1;
    private JSlider delaySlider;
    private JLabel lbAlg;

    public AppPanel(Nodes nodes) {
        initComponents(nodes);
    }

    private void initComponents(Nodes nodes) {
        buttonPanel = new JPanel();

        setLayout(new GridBagLayout());
        GridBagConstraints mapConstraint = new GridBagConstraints();
        mapConstraint.gridx = 0;
        mapConstraint.gridy = 1;
        mapConstraint.fill = GridBagConstraints.BOTH;
        mapConstraint.anchor = GridBagConstraints.LINE_START;
        mapConstraint.weightx = 1.0D;
        mapConstraint.weighty = 1.0D;
        mapPanel = new MapPanel(nodes.getNodes());
        add(mapPanel, mapConstraint);

        GridBagConstraints buttonPanelConstraint = new GridBagConstraints();
        buttonPanelConstraint.gridx = 0;
        buttonPanelConstraint.gridy = 0;
        buttonPanelConstraint.gridwidth = 2;
        buttonPanelConstraint.fill = GridBagConstraints.BOTH;
        buttonPanelConstraint.weightx = 1.0D;
        buttonPanelConstraint.weighty = 0.0D;
        buttonPanelConstraint.anchor = GridBagConstraints.LAST_LINE_END;

        initButtonPanel();
        add(buttonPanel, buttonPanelConstraint);
    }

    private void initButtonPanel() {
        buttonPanel.setLayout(new GridBagLayout());
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
        buttonPanel.add(stopButton, c);
        //reset button
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.resetPath();
            }
        });
        c.gridy = 1;
        buttonPanel.add(resetButton, c);

        lbAlg = new JLabel("Algorithm:");
        c.gridx = 1;
        c.gridy = 0;
        buttonPanel.add(lbAlg, c);
        algBox = new JComboBox();
        AlgorithmFactory af = AlgorithmFactory.getDefault();
        List<String> providers = af.getProviders();
        for (String p : providers) {
            algBox.addItem(p);
        }
        c.gridy = 1;
        buttonPanel.add(algBox, c);
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
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.PAGE_AXIS));
        statsPanel.add(lbNodes);
        statsPanel.add(lbExpand);
        statsPanel.add(lbDist);
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        buttonPanel.add(statsPanel, c);
        mapPanel.addStatsListener(this);
        validate();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                mapPanel.updateSize(getSize());

            }
        });

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
        buttonPanel.add(delaySlider, c);
        mapPanel.setDelay((long) delaySlider.getValue());

        frmDelay = "Delay: %4d ms";
        lbAlg = new JLabel(String.format(frmDelay, (int) delaySlider.getValue()));
        c.fill = GridBagConstraints.NONE;
        c.gridx = 3;
        c.gridy = 0;
        buttonPanel.add(lbAlg, c);

        test1 = new JButton("Test 1");
        test1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.test1Search();
            }
        });
        c.gridx = 4;
        c.gridy = 1;
        buttonPanel.add(test1, c);
    }

    @Override
    public void statsChanged(HashMap<String, Double> stats) {
        double v = stats.get("explored");
        lbNodes.setText(String.format(frmNodes, (int) v));
        v = stats.get("expanded");
        lbExpand.setText(String.format(frmExpand, (int) v, stats.get("coverage")));
        v = stats.get("distance");
        lbDist.setText(String.format(frmDist, v));
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
