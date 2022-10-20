package zn.soft.logic.elements.filters;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import zn.soft.logic.elements.FilterSet;
import zn.soft.logic.elements.Updatable;
import zn.soft.logic.elements.properties.BooleanProperty;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilterMonochrome extends Filter {

    private BooleanProperty active;

    public FilterMonochrome(FilterSet filterSet, Updatable updatable) {
        super(filterSet, updatable, FilterType.MONOCHROME);
        active = new BooleanProperty("Active:", updatable, false, true);
    }
    public FilterMonochrome(FilterSet filterSet, Updatable updatable, Node node) {
        super(filterSet, updatable, FilterType.MONOCHROME);
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if (nodeList.item(i).getNodeName().equals("properties")){
                NodeList properties = nodeList.item(i).getChildNodes();
                for(int j = 0; j < properties.getLength(); j++)
                    if ("active".equals(properties.item(j).getNodeName()))
                        active = new BooleanProperty(updatable, false, properties.item(j));
            }
        }
    }

    @Override
    public BufferedImage apply(BufferedImage img) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        if (active.value()){
            for (int x = 0; x < img.getWidth(); x++){
                for (int y = 0; y < img.getHeight(); y++){
                    Color color = new Color(img.getRGB(x, y), true);
                    int mid = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                    result.setRGB(x, y, new Color(mid, mid, mid, color.getAlpha()).getRGB());
                }
            }
        }
        else {
            Graphics graphics = result.getGraphics();
            graphics.drawImage(img, 0, 0, null);
        }
        return result;
    }

    @Override
    public VBox createSettingsPane() {
        VBox box = new VBox(
                createNameLabel(),
                active.createRepresentFX()
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
        active.toXML(document, props, "active");
    }
}