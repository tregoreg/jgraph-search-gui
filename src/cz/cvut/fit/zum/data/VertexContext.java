package cz.cvut.fit.zum.data;

import cz.cvut.fit.zum.VisInfo;
import cz.cvut.fit.zum.api.ga.AbstractEvolution;
import cz.cvut.fit.zum.gui.HighlightPoint;
import cz.cvut.fit.zum.gui.HighlightTask;
import cz.cvut.fit.zum.gui.SearchLayer;
import java.awt.Color;
import java.awt.image.BufferedImage;
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
    private BufferedImage visited;

    public VertexContext(SearchLayer layer, AbstractEvolution evolution) {
        this.evolution = evolution;
        this.layer = layer;
        
        VisInfo visInfo = VisInfo.getInstance();
        notCovered = visInfo.createCircle(Color.RED);
        coveredPoint = visInfo.createCircle(Color.GREEN);
        visited = visInfo.createCircle(new Color(215, 153, 3));
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

    public void markNode(NodeImpl node, boolean covered) {
        if(covered){
            publish(new HighlightPoint(layer, node, coveredPoint));
            
            List<NodeImpl> nn = node.fastExpand(StateSpace.getNodes());
           
            
        }else{
            publish(new HighlightPoint(layer, node, notCovered));
        }
        
    }
}
