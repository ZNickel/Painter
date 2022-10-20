package zn.soft.logic.elements.filters;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import zn.soft.logic.elements.FilterSet;
import zn.soft.logic.elements.Updatable;
import zn.soft.logic.elements.properties.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilterAddText extends Filter {

    private TextProperty text;
    private ColorProperty color;
    private NumericProperty size;
    private PercentPointProperty position;

    public FilterAddText(FilterSet filterSet, Updatable updatable) {
        super(filterSet, updatable, FilterType.ADD_TEXT);
        text = new TextProperty("Text:", updatable, false, "Default");
        color = new ColorProperty("Text color:", updatable, false, 255, 255, 255, 255);
        size = new NumericProperty("Font size:", updatable, false, 1, 36, 120);
        position = new PercentPointProperty("Text position:", updatable, false);
    }
    public FilterAddText(FilterSet filterSet, Updatable updatable, Node node) {
        super(filterSet, updatable, FilterType.ADD_TEXT);
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if (nodeList.item(i).getNodeName().equals("properties")){
                NodeList properties = nodeList.item(i).getChildNodes();
                for(int j = 0; j < properties.getLength(); j++){
                    switch (properties.item(j).getNodeName()) {
                        case "text" -> text = new TextProperty(updatable, false, properties.item(j));
                        case "color" -> color = new ColorProperty(updatable, false, properties.item(j));
                        case "size" -> size = new NumericProperty(updatable, false, properties.item(j));
                        case "position" -> position = new PercentPointProperty(updatable, false, properties.item(j));
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

        Font font = new Font("Arial Black", Font.PLAIN, size.value());
        graphics.setFont(font);
        graphics.setColor(color.AWT());

        int x = (int) ((result.getWidth() / 100d) * position.x());
        int y = (int) ((result.getHeight() / 100d) * position.y());
        graphics.drawString(text.text(), x, y);

        return result;
    }

    @Override
    public VBox createSettingsPane() {
        var box = new VBox(
                createNameLabel(),
                text.createRepresentFX(),
                color.createRepresentFX(),
                size.createRepresentFX(),
                position.createRepresentFX()
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
        text.toXML(document, props, "text");
        color.toXML(document, props, "color");
        size.toXML(document, props, "size");
        position.toXML(document, props, "position");
    }
}