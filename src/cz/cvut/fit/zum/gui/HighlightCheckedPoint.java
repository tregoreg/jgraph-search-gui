package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.data.NodeImpl;

/**
 *
 * @author Tomas Barton
 */
public class HighlightCheckedPoint extends HighlightTask {

    private SearchLayer layer;
    private NodeImpl node;

    public HighlightCheckedPoint(SearchLayer layer, NodeImpl node) {
        this.layer = layer;
        this.node = node;
    }

    @Override
    public void process() {
        layer.markCheckedPoint(node);
    }
}
