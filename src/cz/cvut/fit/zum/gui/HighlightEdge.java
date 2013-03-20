package cz.cvut.fit.zum.gui;

import cz.cvut.fit.zum.api.Node;
import java.awt.Color;

/**
 *
 * @author Tomas Barton
 */
public class HighlightEdge extends HighlightTask {

    protected Node start;
    protected Node end;
    private SearchLayer layer;
    private Color color;

    public HighlightEdge(SearchLayer layer, Node start, Node end, Color c) {
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
