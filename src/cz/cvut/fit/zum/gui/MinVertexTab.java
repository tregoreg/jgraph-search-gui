package cz.cvut.fit.zum.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Tomas Barton
 */
public class MinVertexTab extends JPanel {

    private JButton btnStart;
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
        btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // stopButton.setEnabled(false);
                mapPanel.stopSearch();
            }
        });
        add(btnStart, c);

    }
}
