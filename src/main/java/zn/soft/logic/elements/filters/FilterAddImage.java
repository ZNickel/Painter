package zn.soft.logic.elements.filters;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import zn.soft.logic.elements.FilterSet;
import zn.soft.logic.elements.Updatable;
import zn.soft.logic.elements.properties.ImageProperty;
import zn.soft.logic.elements.properties.NumericProperty;
import zn.soft.logic.elements.properties.PercentPointProperty;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FilterAddImage extends Filter {

    private ImageProperty image;
    private PercentPointProperty position;
    private NumericProperty width, height;

    public FilterAddImage(FilterSet filterSet, Updatable updatable) {
        super(filterSet, updatable, FilterType.ADD_IMAGE);
        image = new ImageProperty("Image file:", updatable, false);
        position = new PercentPointProperty("Image Position:", updatable, false);
        width = new NumericProperty("Width:", updatable, false, 1, 128, 512);
        height = new NumericProperty("Height:", updatable, false, 1, 128, 512);
    }
    public FilterAddImage(FilterSet filterSet, Updatable updatable, Node node) {
        super(filterSet, updatable, FilterType.ADD_IMAGE);
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if (nodeList.item(i).getNodeName().equals("properties")){
                NodeList properties = nodeList.item(i).getChildNodes();
                for(int j = 0; j < properties.getLength(); j++){
                    switch (properties.item(j).getNodeName()) {
                        case "image" -> image = new ImageProperty(updatable, false, properties.item(j));
                        case "position" -> position = new PercentPointProperty(updatable, false, properties.item(j));
                        case "width" -> width = new NumericProperty(updatable, false, properties.item(j));
                        case "height" -> height = new NumericProperty(updatable, false, properties.item(j));
                    }
                }
            }
        }
    }

    @Override
    public BufferedImage apply(BufferedImage img) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = result.getGraphics();
        graphics.drawImage(img, 0, 0, null);
        if (image.file() != null){BufferedImage paste = null;
            try {
                paste = ImageIO.read(image.file());
            } catch (IOException e) {
                e.printStackTrace();
                //TODO IOException
            }
            if (paste != null){
                int x = (int) ((result.getWidth() / 100d) * position.x());
                int y = (int) ((result.getHeight() / 100d) * position.y());
                graphics.drawImage(paste, x, y, width.value(), height.value(), null);
            }
        }
        return result;
    }

    @Override
    public VBox createSettingsPane() {
        VBox box = new VBox(
                createNameLabel(),
                image.createRepresentFX(),
                position.createRepresentFX(),
                width.createRepresentFX(),
                height.createRepresentFX()
        );
        box.setAlignment(Pos.CENTER);
        return box;
    }

    @Override
    public void toXML(Document document, Element parent) {
        Element root = document.createElement(type.tagName());
        parent.appendChild(root);

        Element props = document.createElement("properties");
        root.appendChild(props);
        image.toXML(document, props, "image");
        position.toXML(document, props, "position");
        width.toXML(document, props, "width");
        height.toXML(document, props, "height");
    }
}