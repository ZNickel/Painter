package zn.soft.logic.elements.filters;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import zn.soft.logic.elements.FilterSet;
import zn.soft.logic.elements.Updatable;
import zn.soft.logic.elements.properties.NumericProperty;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilterIndents extends Filter {

    private NumericProperty left, right, top, bottom;

    public FilterIndents(FilterSet filterSet, Updatable updatable) {
        super(filterSet, updatable, FilterType.INDENTS);
        left = new NumericProperty("Left:", updatable, false, 0, 50, 128);
        right = new NumericProperty("Right:", updatable, false, 0, 50, 128);
        top = new NumericProperty("Top:", updatable, false, 0, 50, 128);
        bottom = new NumericProperty("Bottom:", updatable, false, 0, 50, 128);
    }
    public FilterIndents(FilterSet filterSet, Updatable updatable, Node node) {
        super(filterSet, updatable, FilterType.INDENTS);
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if (nodeList.item(i).getNodeName().equals("properties")){
                NodeList properties = nodeList.item(i).getChildNodes();
                for(int j = 0; j < properties.getLength(); j++){
                    switch (properties.item(j).getNodeName()) {
                        case "left" -> left = new NumericProperty(updatable, false, properties.item(j));
                        case "right" -> right = new NumericProperty(updatable, false, properties.item(j));
                        case "top" -> top = new NumericProperty(updatable, false, properties.item(j));
                        case "bottom" -> bottom = new NumericProperty(updatable, false, properties.item(j));
                    }
                }
            }
        }
    }

    @Override
    public BufferedImage apply(BufferedImage img) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = result.getGraphics();
        Color color1 = new Color(img.getRGB(0, 0), true);
        Color color2 = new Color(img.getRGB(img.getWidth() - 1, 0), true);
        Color color3 = new Color(img.getRGB(0, img.getHeight() - 1), true);
        Color color4 = new Color(img.getRGB(img.getWidth() - 1, img.getHeight() - 1), true);
        Color midColor = new Color(
                (color1.getRed()+color2.getRed()+color3.getRed()+color4.getRed()) / 4,
                (color1.getGreen()+color2.getGreen()+color3.getGreen()+color4.getGreen()) / 4,
                (color1.getBlue()+color2.getBlue()+color3.getBlue()+color4.getBlue()) / 4,
                (color1.getAlpha()+color2.getAlpha()+color3.getAlpha()+color4.getAlpha()) / 4
        );
        graphics.setColor(midColor);
        graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
        graphics.drawImage(
                img,
                left.value(),
                top.value(),
                result.getWidth() - left.value() - right.value(),
                result.getHeight() - top.value() - bottom.value(),
                null
        );
        return result;
    }

    @Override
    public VBox createSettingsPane() {
        VBox box = new VBox(
                createNameLabel(),
                left.createRepresentFX(),
                right.createRepresentFX(),
                top.createRepresentFX(),
                bottom.createRepresentFX()
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
        left.toXML(document, props, "left");
        right.toXML(document, props, "right");
        top.toXML(document, props, "top");
        bottom.toXML(document, props, "bottom");
    }
}