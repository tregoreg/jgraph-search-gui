package cz.cvut.fit.zum.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JTabbedPane;

/**
 *
 * @author Tomas Barton
 */
public class AppPanel extends JPanel {

    private static final long serialVersionUID = -8338182648645369875L;
    private JTabbedPane jTabs;
    private MapPanel mapPanel;
    //private SearchTab searchTab;
    private MinVertexTab vertexTab;
    private Dimension mapSize;

    public AppPanel() {
        initComponents();
    }

    private void initComponents() {
        jTabs = new javax.swing.JTabbedPane();

        setLayout(new GridBagLayout());

        GridBagConstraints tabsPanelConstraint = new GridBagConstraints();
        tabsPanelConstraint.gridx = 0;
        tabsPanelConstraint.gridy = 0;
        tabsPanelConstraint.gridwidth = 2;
        tabsPanelConstraint.fill = GridBagConstraints.HORIZONTAL;
        tabsPanelConstraint.weightx = 1.0D;
        tabsPanelConstraint.weighty = 0.0D;
        tabsPanelConstraint.anchor = GridBagConstraints.NORTHWEST;

        GridBagConstraints mapConstraint = new GridBagConstraints();
        mapConstraint.gridx = 0;
        mapConstraint.gridy = 1;
        mapConstraint.fill = GridBagConstraints.BOTH;
        mapConstraint.anchor = GridBagConstraints.LINE_START;
        mapConstraint.weightx = 1.0D;
        mapConstraint.weighty = 1.0D;
        mapPanel = new MapPanel();

        /*searchTab = new SearchTab(mapPanel);
        jTabs.add("Graph Search", searchTab);*/
        vertexTab = new MinVertexTab(mapPanel);
        jTabs.add("Min-Vertex Cover", vertexTab);
        jTabs.setSelectedIndex(0); //vertex cover problem
        
        add(jTabs, tabsPanelConstraint);
        add(mapPanel, mapConstraint);


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                mapPanel.updateSize(mapSize);
                mapPanel.updateLayer();
            }
        });
    }

    public void updateSize(Dimension dim) {
        mapSize = new Dimension(dim.width, dim.height - 150); // height of tabs
        mapPanel.setSize(mapSize);
    }
}
