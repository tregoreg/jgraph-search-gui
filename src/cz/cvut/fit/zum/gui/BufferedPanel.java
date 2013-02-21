package cz.cvut.fit.zum.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Tomas Barton
 */
public abstract class BufferedPanel extends JPanel {

    private static final long serialVersionUID = -8306522684755723013L;
    protected BufferedImage bufferedImage;
    protected Graphics2D graphics;
    private Dimension size;
    protected Insets insets = new Insets(0, 0, 0, 0);

    @Override
    public void paintComponent(Graphics g) {
        if (bufferedImage == null) {
            if (getSize().width <= 0 || getSize().height <= 0) {
                g.dispose();
                return;
            }
            bufferedImage = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
            graphics = bufferedImage.createGraphics();
            drawComponent(graphics);
        }

        g.drawImage(bufferedImage,
                insets.top, insets.left,
                getSize().width, getSize().height,
                null);
        g.dispose();
    }

    protected abstract void drawComponent(Graphics2D g);

    public void rescale(Dimension dim) {
        if (!getSize().equals(dim)) {
            //System.out.println(getSize() +" vs "+dim);
            this.setSize(dim);
            setPreferredSize(getSize());
            setMinimumSize(getSize());
            updateLayer();
        }

    }
    
    public void updateLayer() {
        //System.out.println(this.getClass().getName() + " layer update");
        bufferedImage = null;
        repaint();
    }

    /**
     * @return the size
     */
    @Override
    public Dimension getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    @Override
    public void setSize(Dimension size) {
        this.size = size;
        updateLayer();
    }
}
