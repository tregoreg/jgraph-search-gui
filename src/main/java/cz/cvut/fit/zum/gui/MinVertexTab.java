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
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
    private JSlider sliderMutation;
    private JSlider sliderCrossover;
    private JLabel lbMutation;
    private String frmMutation;
    private JLabel lbCrossover;
    private String frmCrossover;
    private JLabel lbUnreachable;
    private String frmUnreachable;
    private JLabel lbGeneration;
    private String frmGeneration;
    private JPanel settings;
    private JSlider sliderPopulation;
    private JLabel lbPopulation;
    private String frmPopulation;
    private JSlider sliderGenerations;
    private JLabel lbGenerations;
    private String frmGenerations;
    private JPanel algPanel;
    private boolean started = false;

    public MinVertexTab(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
        initComponents();
    }

    private void initComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        algPanel = new JPanel();
        algPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0D;
        c.weighty = 1.0D;
        c.insets = new Insets(5, 15, 5, 5);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.LINE_START;

        //start button
        btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!started) {
                    mapPanel.vertexCoverAlgorithmChanged(evolutionBox.getSelectedItem().toString(), true);
                    started = true;
                    btnStart.setText("Stop");
                } else {
                    mapPanel.getVertexCoverTask().setFinish(true);
                    btnStart.setEnabled(false);
                }
            }
        });
        algPanel.add(btnStart, c);

        //evolution alg. box
        evolutionBox = new JComboBox();
        EvolutionFactory ef = EvolutionFactory.getDefault();
        List<String> providers = ef.getProviders();
        for (String p : providers) {
            evolutionBox.addItem(p);
        }
        c.gridy = 1;

        evolutionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapPanel.vertexCoverAlgorithmChanged(evolutionBox.getSelectedItem().toString(), true);
            }
        });
        if (evolutionBox.getSelectedItem() != null) {
            mapPanel.vertexCoverAlgorithmChanged(evolutionBox.getSelectedItem().toString(), false);
        }
        algPanel.add(evolutionBox, c);
        this.add(algPanel);
        //set current algorithm
        //mapPanel.vertexCoverAlgorithmChanged(evolutionBox.getSelectedItem().toString());

        createSettingsPanel();
        this.add(settings);

        frmNodes = "Vertex cover:  %d (%4.1f%%)";
        lbNodes = new JLabel(String.format(frmNodes, 0, 0.0));
        frmReached = "Reached nodes: %d";
        lbReached = new JLabel(String.format(frmReached, 0, 0.0));
        frmUnreachable = "Unreachable: %d";
        lbUnreachable = new JLabel(String.format(frmUnreachable, 0, 0.0));
        frmGeneration = "Generation: %d";
        lbGeneration = new JLabel(String.format(frmGeneration, 0, 0.0));
        frmFit = "Fitness: %10.2f";
        lbFit = new JLabel(String.format(frmFit, 0.0));
        frmTime = "Time: %10.0f s";
        lbTime = new JLabel(String.format(frmTime, 0.0));
        statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.add(lbNodes);
        statsPanel.add(lbReached);
        statsPanel.add(lbUnreachable);
        statsPanel.add(lbFit);
        statsPanel.add(lbGeneration);
        statsPanel.add(lbTime);
        this.add(statsPanel);
        mapPanel.addEvolutionListener(this);

        validate();
    }

    private void createSettingsPanel() {

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0D;
        c.weighty = 1.0D;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;

        settings = new JPanel();
        settings.setLayout(new GridBagLayout());

        sliderMutation = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 30);
        sliderMutation.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!sliderMutation.getValueIsAdjusting()) {
                    mapPanel.getVertexCoverTask().setMutationProbability(sliderMutation.getValue());
                }
                lbMutation.setText(String.format(frmMutation, (int) sliderMutation.getValue()));
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        settings.add(sliderMutation, c);
        mapPanel.getVertexCoverTask().setMutationProbability(sliderMutation.getValue());

        frmMutation = "Mutation prob.: %2d%%";
        lbMutation = new JLabel(String.format(frmMutation, (int) sliderMutation.getValue()));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        settings.add(lbMutation, c);

        //crossover
        sliderCrossover = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 30);
        sliderCrossover.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!sliderCrossover.getValueIsAdjusting()) {
                    mapPanel.getVertexCoverTask().setCrossoverProbability(sliderCrossover.getValue());
                }
                lbCrossover.setText(String.format(frmCrossover, (int) sliderCrossover.getValue()));
            }
        });
        c.gridx = 0;
        c.gridy = 3;
        settings.add(sliderCrossover, c);
        mapPanel.getVertexCoverTask().setCrossoverProbability(sliderCrossover.getValue());

        frmCrossover = "Crossover prob.: %2d%%";
        lbCrossover = new JLabel(String.format(frmCrossover, (int) sliderCrossover.getValue()));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        settings.add(lbCrossover, c);

        //population
        sliderPopulation = new JSlider(SwingConstants.HORIZONTAL, 1, 1000, 100);
        sliderPopulation.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!sliderPopulation.getValueIsAdjusting()) {
                    mapPanel.getVertexCoverTask().setPopulationSize(sliderPopulation.getValue());
                }
                lbPopulation.setText(String.format(frmPopulation, (int) sliderPopulation.getValue()));
            }
        });
        c.gridx = 0;
        c.gridy = 5;
        settings.add(sliderPopulation, c);
        mapPanel.getVertexCoverTask().setPopulationSize(sliderPopulation.getValue());

        frmPopulation = "Population: %3d";
        lbPopulation = new JLabel(String.format(frmPopulation, (int) sliderPopulation.getValue()));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        settings.add(lbPopulation, c);

        //generations
        sliderGenerations = new JSlider(SwingConstants.HORIZONTAL, 10, 10000, 1000);
        sliderGenerations.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!sliderGenerations.getValueIsAdjusting()) {
                    mapPanel.getVertexCoverTask().setGenerations(sliderGenerations.getValue());
                }
                lbGenerations.setText(String.format(frmGenerations, (int) sliderGenerations.getValue()));
            }
        });
        c.gridx = 0;
        c.gridy = 7;
        settings.add(sliderGenerations, c);
        mapPanel.getVertexCoverTask().setGenerations(sliderGenerations.getValue());

        frmGenerations = "Generation: %3d";
        lbGenerations = new JLabel(String.format(frmGenerations, (int) sliderGenerations.getValue()));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        settings.add(lbGenerations, c);
    }

    @Override
    public void statsChanged(HashMap<String, Double> stats) {
        double v = stats.get("cover");
        lbNodes.setText(String.format(frmNodes, (int) v, stats.get("coverage")));
        v = stats.get("reached");
        lbReached.setText(String.format(frmReached, (int) v, stats.get("coverage")));
        v = stats.get("unreachable");
        lbUnreachable.setText(String.format(frmUnreachable, (int) v));
        v = stats.get("generation");
        lbGeneration.setText(String.format(frmGeneration, (int) v));
        v = stats.get("fitness");
        lbFit.setText(String.format(frmFit, v));
        v = stats.get("time");
        lbTime.setText(String.format(frmTime, v));
    }

    @Override
    public void evolutionFinished() {
        btnStart.setText("Start");
        started = false;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                btnStart.setEnabled(true);
                //mapPanel.resetMap();
            }
        });

    }
}
