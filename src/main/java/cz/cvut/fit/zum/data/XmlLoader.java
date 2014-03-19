package cz.cvut.fit.zum.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Tomas Barton
 */
public class XmlLoader {

    public static Nodes loadGraphFromFile(File file) {
        FileInputStream input;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.toString());
            return null;
        }
        Class defClass = NodeImpl.class;
        Nodes xmlNodes;
        try {
            JAXBContext context = JAXBContext.newInstance(defClass.getPackage().getName(), defClass.getClassLoader());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            xmlNodes = (Nodes) unmarshaller.unmarshal(input);
        } catch (JAXBException ex) {
            System.out.println("Error: Parsing file " + file + " failed! " + ex);
            return null;
        }
        try {
            input.close();
        } catch (IOException e) {
            System.out.println("Error: Can not close input file!");
        }

        for (NodeImpl n : xmlNodes.getNodes()) {
            n.setY((1 - n.getY()));
        }

        return xmlNodes;
    }
}
