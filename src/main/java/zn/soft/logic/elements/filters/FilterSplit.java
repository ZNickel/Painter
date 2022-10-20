package zn.soft.logic.elements.filters;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import zn.soft.logic.elements.FilterSet;
import zn.soft.logic.elements.Updatable;
import zn.soft.logic.elements.properties.ColorProperty;
import zn.soft.logic.elements.properties.NumericProperty;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilterSplit extends Filter {

    private NumericProperty splitter;
    private ColorProperty colorUnder, colorAfter;

    public FilterSplit(FilterSet filterSet, Updatable updatable) {
        super(filterSet, updatable, FilterType.SPLIT);
        splitter = new NumericProperty("Splitter:", updatable, false, 0, 127, 255);
        colorUnder = new ColorProperty("Color under splitter:", updatable, false, 255, 255, 255, 255);
        colorAfter = new ColorProperty("Color after splitter:", updatable, false);
    }
    public FilterSplit(FilterSet filterSet, Updatable updatable, Node node) {
        super(filterSet, updatable, FilterType.SPLIT);
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if (nodeList.item(i).getNodeName().equals("properties")){
                NodeList properties = nodeList.item(i).getChildNodes();
                for(int j = 0; j < properties.getLength(); j++){
                    switch (properties.item(j).getNodeName()) {
                        case "splitter" -> splitter = new NumericProperty(updatable, false, properties.item(j));
                        case "colorUnder" -> colorUnder = new ColorProperty(updatable, false, properties.item(j));
                        case "colorAfter" -> colorAfter = new ColorProperty(updatable, false, properties.item(j));
                    }
                }
            }
        }
    }

    @Override
    public BufferedImage apply(BufferedImage img) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < img.getWidth(); x++){
            for (int y = 0; y < img.getHeight(); y++){
                Color color = new Color(img.getRGB(x, y), true);
                int mid = (color.getRed() + color.getBlue() + color.getGreen()) / 3;
                result.setRGB(x, y, mid < splitter.value() ? colorUnder.RGB() : colorAfter.RGB());
            }
        }
        return result;
    }

    @Override
    public VBox createSettingsPane() {
        VBox box = new VBox(
                createNameLabel(),
                splitter.createRepresentFX(),
                colorUnder.createRepresentFX(),
                colorAfter.createRepresentFX()
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
        splitter.toXML(document, props, "splitter");
        colorUnder.toXML(document, props, "colorUnder");
        colorAfter.toXML(document, props, "colorAfter");
    }
}