package cz.cvut.fit.zum;

import cz.cvut.fit.zum.api.Node;
import cz.cvut.fit.zum.data.StateSpace;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.List;
import cz.cvut.fit.zum.data.NodeImpl;

/**
 *
 * @author Tomas Barton
 */
public class VisInfo {

    private AffineTransform tranformation;
    private int circleWidth = 4;
    private double circleDiam;
    private Color nodeColor = new Color(0.0f, 0.0f, 0.0f, 0.3f);  // semi-transparent
    private static double MIN_DIST = 0.2;
    private double mx, my, dx, dy;
    private double minDistance, distance;
    private int nodeId;
    private static VisInfo instance;

    protected VisInfo() {
        tranformation = new AffineTransform();
        tranformation.scale(1, 1);
        circleDiam = circleWidth / 2;
    }
    
    public static VisInfo getInstance(){
        if(instance == null){
            instance = new VisInfo();
        }
        return instance;
    }

    /**
     * Create buffered image with shape of circle (speeds-up visualizations)
     *
     * @param color
     * @return
     */
    public BufferedImage createCircle(Color color) {
        BufferedImage circ = new BufferedImage(2 * circleWidth, 2 * circleWidth, BufferedImage.TYPE_INT_ARGB);
        Graphics2D buff = circ.createGraphics();
        Ellipse2D.Double ellipse = new Ellipse2D.Double(0, 0, circleWidth, circleWidth);
        buff.setPaint(color);
        buff.draw(ellipse);
        buff.fill(ellipse);
        return circ;
    }

    public Node findNode(int x, int y, Dimension mapSize) {
        mx = ((double) x / mapSize.width);
        my = ((double) y / mapSize.height);

        nodeId = -1;
        minDistance = MIN_DIST;
        for (Node node : getNodes()) {
            dx = node.getX() - mx;
            dy = node.getY() - my;

            distance = dx * dx + dy * dy;
            if (distance < minDistance) {
                minDistance = distance;
                nodeId = node.getId();
            }

        }
        //System.out.println("node id = " + nodeId);
        return nodeId > -1 ? getNode(nodeId) : null;
    }

    public int getNodesCount() {
        return StateSpace.nodesCount();
    }

    public void computePositions(Dimension size) {
        double x, y;
        for (Node node : getNodes()) {
            x = node.getX() * size.width;
            y = node.getY() * size.height;
            ((NodeImpl) node).setPoint(new Point2D.Double(x, y));
        }
    }

    public AffineTransform getTranformation() {
        return tranformation;
    }

    public void setTranformation(AffineTransform tranformation) {
        this.tranformation = tranformation;
    }

    public int getCircleWidth() {
        return circleWidth;
    }

    public void setCircleWidth(int circleWidth) {
        this.circleWidth = circleWidth;
    }

    public double getCircleDiam() {
        return circleDiam;
    }

    public void setCircleDiam(double circleDiam) {
        this.circleDiam = circleDiam;
    }

    public Color getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(Color nodeColor) {
        this.nodeColor = nodeColor;
    }

    public List<Node> getNodes() {
        return StateSpace.getNodes();
    }

    public Node getNode(int id) {
        return StateSpace.getNode(id);
    }

    public boolean hasNodes() {
        return StateSpace.nodesCount() > 0;
    }
}
