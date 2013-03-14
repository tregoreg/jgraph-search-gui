package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.EvolutionFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author Tomas Barton
 */
public class MinVertexTab extends JPanel {

    private static final long serialVersionUID = -4912185360110694849L;
    private JButton btnStart;
    private JComboBox evolutionBox;
    private MapPanel mapPanel;

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

    }
}
