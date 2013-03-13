package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.AlgorithmFactory;
import cz.cvut.fit.zum.VisInfo;
import cz.cvut.fit.zum.api.AbstractAlgorithm;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import javax.swing.JLayeredPane;

/**
 *
 * @author Tomas Barton
 */
public class MapPanel extends JLayeredPane {

    private static final long serialVersionUID = 1L;
    private MapLayer mapLayer;
    private GridLayer gridLayer;
    private SearchLayer searchLayer;
    private Dimension size = new Dimension(0, 0);
    private VisInfo visInfo;

    public MapPanel() {
        initializeComponents();
    }

    private void initializeComponents() {
        Dimension minSize = new Dimension(800, 800);
        visInfo = new VisInfo();
        visInfo.computePositions(minSize);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = 1;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 1.0D;
        c.weighty = 1.0D;
        //layer with map texture
        mapLayer = new MapLayer(minSize);
        add(mapLayer, c, 10); //back layer
        //layer with nodes and edges
        gridLayer = new GridLayer(minSize, visInfo);
        add(gridLayer, c, 0);
        searchLayer = new SearchLayer(minSize, visInfo);
        add(searchLayer, c, 0);
        setPreferredSize(minSize);
        Rectangle bounds = getBounds();
        Dimension dim = new Dimension(bounds.width, bounds.height);
        mapLayer.setPreferredSize(dim);
        gridLayer.setPreferredSize(dim);
    }

    public void updateSize(Dimension dim) {
        int min = Math.min(dim.width, dim.height);
        size.width = min;
        size.height = min;
        //when size changes, we need to rescale points
        visInfo.computePositions(size);
        mapLayer.rescale(size);
        gridLayer.rescale(size);
        searchLayer.rescale(size);
        setPreferredSize(size);
    }

    protected void updateLayer() {
        searchLayer.updateLayer();
        gridLayer.updateLayer();
        mapLayer.updateLayer();
    }

    public void algorithmChanged(String algName) {
        AbstractAlgorithm alg = AlgorithmFactory.getDefault().getProvider(algName);
        if (alg != null) {
            searchLayer.algorithmChanged(alg);
        }
    }

    public void resetPath() {
        searchLayer.clearSelection();
        searchLayer.updateLayer();
        gridLayer.updateLayer();
        mapLayer.updateLayer();
    }

    public void stopSearch() {
        searchLayer.stopSearch();
    }

    public void addStatsListener(AlgorithmListener listener) {
        searchLayer.addStatsListener(listener);
    }

    void test1Search() {
        searchLayer.search(visInfo.getNode(1118), visInfo.getNode(636));
    }

    void test2Search() {
        searchLayer.search(visInfo.getNode(5), visInfo.getNode(1863));
    }

    void test3Search() {
        searchLayer.search(visInfo.getNode(163), visInfo.getNode(315));
    }

    void test4Search() {
        searchLayer.search(visInfo.getNode(287), visInfo.getNode(338));
    }

    public void setDelay(long delay) {
        searchLayer.setDelay(delay);
    }
    
    public void startVertexCover(){
        searchLayer.vertexCover();
    }
}
