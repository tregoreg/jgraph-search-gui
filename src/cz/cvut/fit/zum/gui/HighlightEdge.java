package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.data.NodeImpl;
import java.awt.Color;

/**
 *
 * @author Tomas Barton
 */
public class HighlightEdge extends HighlightTask {

    protected NodeImpl start;
    protected NodeImpl end;
    private SearchLayer layer;
    private Color color;

    HighlightEdge(SearchLayer layer, NodeImpl start, NodeImpl end, Color c) {
        this.layer = layer;
        this.start = start;
        this.end = end;
        this.color = c;
    }

    @Override
    public void process() {
        layer.higlightEdge(start, end, color);
    }
}
