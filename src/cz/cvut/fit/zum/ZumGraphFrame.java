package cz.cvut.fit.zum;

import cz.cvut.fit.zum.data.StateSpace;
import cz.cvut.fit.zum.gui.AppPanel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import cz.cvut.fit.zum.data.Nodes;
import cz.cvut.fit.zum.data.XmlLoader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import org.openide.util.Exceptions;

public class ZumGraphFrame extends JFrame {

    private static final long serialVersionUID = 2574331061240714509L;
    Thread thread;
    private AppPanel appPanel;

    public ZumGraphFrame() {
        super("Graph Search Visualization");
        File nodesFile = loadXmlResource();
        final Nodes nodes = XmlLoader.loadGraphFromFile(nodesFile);
        if (nodes == null) {
            System.out.println("Problem with loadGraphFromFile");
            throw new RuntimeException("Problem with loadGraphFromFile");
        }
        StateSpace.setNodes(nodes.getNodes());
        System.out.println("Loaded " + StateSpace.nodesCount() + " nodes");

        appPanel = new AppPanel();
        add(appPanel);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // This is only called when the user releases the mouse button.
                appPanel.updateSize(getSize());
            }
        });
    }

    private File loadXmlResource() {
        File nodesFile = null;
        String resource = "/cz/cvut/fit/resources/nodes.xml";
        URL res = getClass().getResource(resource);
        if (res.toString().startsWith("jar:")) {
            try {
                InputStream input = getClass().getResourceAsStream(resource);
                nodesFile = File.createTempFile("nodesfile", ".tmp");
                OutputStream out = new FileOutputStream(nodesFile);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                nodesFile.deleteOnExit();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            nodesFile = new File(res.getFile());
        }

        if (nodesFile != null && !nodesFile.exists()) {
            throw new RuntimeException("Error: File " + nodesFile + " not found!");
        }
        return nodesFile;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Exceptions.printStackTrace(e);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ZumGraphFrame app = new ZumGraphFrame();
                    app.setSize(800, 860);
                    app.setVisible(true);
                    app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                } catch (Exception e) {
                    Exceptions.printStackTrace(e);
                }
            }
        });
    }
}