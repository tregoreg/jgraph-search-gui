package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.api.Node;
import java.awt.image.BufferedImage;

/**
 *
 * @author Tomas Barton
 */
public class HighlightPoint extends HighlightTask {

    private SearchLayer layer;
    private Node node;
    private BufferedImage shape;

    public HighlightPoint(SearchLayer layer, Node node, BufferedImage shape) {
        this.layer = layer;
        this.node = node;
        this.shape = shape;
    }

    @Override
    public void process() {
        layer.highlightPoint(node, shape);
    }
}
