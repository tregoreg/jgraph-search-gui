package cz.cvut.fit.zum.gui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Tomas Barton
 */
public class MapLayer extends BufferedPanel {

    private static final long serialVersionUID = 1L;
    private BufferedImage image;
    
    public MapLayer(Dimension minSize) {
        setSize(minSize);
        initComponents();
    }
    
    private void initComponents() {
        loadTexture();
        setOpaque(false);
        setMinimumSize(this.getSize());
    }
    
    private void loadTexture() {
        String imgFilename = "/cz/cvut/fit/resources/island_texture.png";
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imgFilename));
            
        } catch (IOException ex) {
            Logger.getLogger(MapLayer.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("unable to load file " + imgFilename);
        }
    }
    
    @Override
    protected void drawComponent(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, getSize().width, getSize().height, null);
        g.dispose();
    }
}
