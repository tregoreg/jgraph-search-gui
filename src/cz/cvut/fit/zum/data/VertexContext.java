package cz.cvut.fit.zum.data;

import cz.cvut.fit.zum.VisInfo;
import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.gui.HighlightEdge;
import cz.cvut.fit.zum.gui.HighlightPoint;
import cz.cvut.fit.zum.gui.HighlightTask;
import cz.cvut.fit.zum.gui.SearchLayer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Tomas Barton
 */
public class VertexContext extends SwingWorker<Void, HighlightTask> {

    private AbstractEvolution evolution;
    private long startTime, endTime;
    private SearchLayer layer;
    private BufferedImage notCovered;
    private BufferedImage coveredPoint;
    private BufferedImage reachablePoint;
    private Color edge = Color.BLUE;
    private HashSet<NodeImpl> cover;
    private HashSet<NodeImpl> reachable;

    public VertexContext(SearchLayer layer, AbstractEvolution evolution) {
        this.evolution = evolution;
        this.layer = layer;
        cover = new HashSet<NodeImpl>(StateSpace.nodesCount());
        reachable = new HashSet<NodeImpl>(StateSpace.nodesCount() / 2);
        VisInfo visInfo = VisInfo.getInstance();
        notCovered = visInfo.createCircle(Color.RED);
        coveredPoint = visInfo.createCircle(Color.GREEN);
        reachablePoint = visInfo.createCircle(new Color(215, 153, 3));
    }

    @Override
    protected Void doInBackground() throws Exception {
        startTime = System.currentTimeMillis();
        endTime = startTime;
        evolution.run();

        endTime = System.currentTimeMillis();
        return null;
    }

    @Override
    protected void process(List<HighlightTask> pairs) {
        while (!pairs.isEmpty()) {
            HighlightTask task = pairs.remove(0);
            task.process();
        }
        layer.repaint(); //after exploring some node we repaint the layer
        //  layer.updateStats(this);
    }

    @Override
    protected void done() {
        long time = endTime - startTime;
        System.out.println("Evolution time = " + time + " ms");
    }

    public long getTime() {
        if (endTime > 0) {
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    public void markNode(NodeImpl from, boolean covered) {
        if (covered) {
            publish(new HighlightPoint(layer, from, coveredPoint));
            cover.add(from);
            reachable.remove(from); //remove if present
            List<NodeImpl> nn = from.fastExpand(StateSpace.getNodes());
            for (NodeImpl to : nn) {
                //highlight edge
                publish(new HighlightEdge(layer, from, to, edge));
                //reachable nodes
                if (!cover.contains(to)) {
                    publish(new HighlightPoint(layer, to, reachablePoint));
                    reachable.add(to);
                }
            }

        } else {
            if (!reachable.contains(from)) {
                publish(new HighlightPoint(layer, from, notCovered));
            }
        }

    }
}
