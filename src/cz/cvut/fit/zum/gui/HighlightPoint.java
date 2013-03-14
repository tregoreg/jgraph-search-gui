package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.data.NodeImpl;
import java.awt.image.BufferedImage;

/**
 *
 * @author Tomas Barton
 */
public class HighlightPoint extends HighlightTask {

    private SearchLayer layer;
    private NodeImpl node;
    private BufferedImage shape;

    public HighlightPoint(SearchLayer layer, NodeImpl node, BufferedImage shape) {
        this.layer = layer;
        this.node = node;
        this.shape = shape;
    }

    @Override
    public void process() {
        layer.highlightPoint(node, shape);
    }
}
