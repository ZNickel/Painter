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
import zn.soft.logic.elements.properties.PercentPointProperty;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FilterGradient extends Filter {

    private ColorProperty startColor, endColor;
    private PercentPointProperty startPosition, endPosition;

    public FilterGradient(FilterSet filterSet, Updatable updatable) {
        super(filterSet, updatable, FilterType.GRADIENT);
        startColor = new ColorProperty("Start color:", updatable, false);
        endColor = new ColorProperty("End color:", updatable, false);
        startPosition = new PercentPointProperty("Start position:", updatable, false);
        endPosition = new PercentPointProperty("End position:", updatable, false);
    }
    public FilterGradient(FilterSet filterSet, Updatable updatable, Node node) {
        super(filterSet, updatable, FilterType.GRADIENT);
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if (nodeList.item(i).getNodeName().equals("properties")){
                NodeList properties = nodeList.item(i).getChildNodes();
                for(int j = 0; j < properties.getLength(); j++){
                    switch (properties.item(j).getNodeName()) {
                        case "startPosition" -> startPosition = new PercentPointProperty(updatable, false, properties.item(j));
                        case "startColor" -> startColor = new ColorProperty(updatable, false, properties.item(j));
                        case "endPosition" -> endPosition = new PercentPointProperty(updatable, false, properties.item(j));
                        case "endColor" -> endColor = new ColorProperty(updatable, false, properties.item(j));
                    }
                }
            }
        }
    }

    @Override
    public BufferedImage apply(BufferedImage img) {
        GradientPaint gradientPaint = new GradientPaint(
                (float) ((img.getWidth() / 100d) * startPosition.x()),
                (float) ((img.getHeight() / 100d) * startPosition.y()),
                startColor.AWT(),
                (float) ((img.getWidth() / 100d) * endPosition.x()),
                (float) ((img.getHeight() / 100d) * endPosition.y()),
                endColor.AWT()
        );
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) result.getGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.setPaint(gradientPaint);
        graphics2D.fill(new Rectangle(0, 0, img.getWidth(), img.getHeight()));
        return result;
    }

    @Override
    public VBox createSettingsPane() {
        VBox box = new VBox(
                createNameLabel(),
                startPosition.createRepresentFX(),
                startColor.createRepresentFX(),
                endPosition.createRepresentFX(),
                endColor.createRepresentFX()
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
        startPosition.toXML(document, props, "startPosition");
        startColor.toXML(document, props, "startColor");
        endPosition.toXML(document, props, "endPosition");
        endColor.toXML(document, props, "endColor");
    }
}