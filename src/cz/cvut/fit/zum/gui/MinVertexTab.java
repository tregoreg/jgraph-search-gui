package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.EvolutionFactory;
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

/**
 *
 * @author Tomas Barton
 */
public class MinVertexTab extends JPanel implements EvolutionListener {

    private static final long serialVersionUID = -4912185360110694849L;
    private JButton btnStart;
    private JComboBox evolutionBox;
    private MapPanel mapPanel;
    private JPanel statsPanel;
    private JLabel lbNodes;
    private JLabel lbReached;
    private JLabel lbFit;
    private JLabel lbTime;
    private String frmNodes;
    private String frmReached;
    private String frmFit;
    private String frmTime;

    public MinVertexTab(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0D;
        c.weighty = 1.0D;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;

        //start button
        btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.vertexCoverAlgorithmChanged(evolutionBox.getSelectedItem().toString());
            }
        });
        add(btnStart, c);

        //evolution alg. box
        evolutionBox = new JComboBox();
        EvolutionFactory ef = EvolutionFactory.getDefault();
        List<String> providers = ef.getProviders();
        for (String p : providers) {
            evolutionBox.addItem(p);
        }
        c.gridy = 1;
        this.add(evolutionBox, c);
        evolutionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.vertexCoverAlgorithmChanged(evolutionBox.getSelectedItem().toString());
            }
        });

        //set current algorithm
        //mapPanel.vertexCoverAlgorithmChanged(evolutionBox.getSelectedItem().toString());


        frmNodes = "Vertex cover:  %d (%4.1f%%)";
        lbNodes = new JLabel(String.format(frmNodes, 0, 0.0));
        frmReached = "Reached nodes: %d";
        lbReached = new JLabel(String.format(frmReached, 0, 0.0));
        frmFit = "Fitness: %10.2f";
        lbFit = new JLabel(String.format(frmFit, 0.0));
        frmTime = "Time: %10.0f ms";
        lbTime = new JLabel(String.format(frmTime, 0.0));
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.PAGE_AXIS));
        statsPanel.add(lbNodes);
        statsPanel.add(lbReached);
        statsPanel.add(lbFit);
        statsPanel.add(lbTime);
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);
        this.add(statsPanel, c);
        mapPanel.addEvolutionListener(this);
        validate();

    }

    @Override
    public void statsChanged(HashMap<String, Double> stats) {
        double v = stats.get("cover");
        lbNodes.setText(String.format(frmNodes, (int) v, stats.get("coverage")));
        v = stats.get("reached");
        lbReached.setText(String.format(frmReached, (int) v, stats.get("coverage")));
        v = stats.get("fitness");
        lbFit.setText(String.format(frmFit, v));
        v = stats.get("time");
        lbTime.setText(String.format(frmTime, v));
    }
}
