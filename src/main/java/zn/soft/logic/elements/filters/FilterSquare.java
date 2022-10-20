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

public class FilterSquare extends Filter {

    private NumericProperty size, shift;

    public FilterSquare(FilterSet filterSet, Updatable updatable) {
        super(filterSet, updatable, FilterType.SQUARE);
        size = new NumericProperty("Side size:", updatable, false, 4, 512, 1024);
        shift = new NumericProperty("Shift in percent:", updatable, false, 0, 50, 100);
    }
    public FilterSquare(FilterSet filterSet, Updatable updatable, Node node) {
        super(filterSet, updatable, FilterType.SQUARE);
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if (nodeList.item(i).getNodeName().equals("properties")){
                NodeList properties = nodeList.item(i).getChildNodes();
                for(int j = 0; j < properties.getLength(); j++){
                    switch (properties.item(j).getNodeName()) {
                        case "size" -> size = new NumericProperty(updatable, false, properties.item(j));
                        case "shift" -> shift = new NumericProperty(updatable, false, properties.item(j));
                    }
                }
            }
        }
    }

    @Override
    public BufferedImage apply(BufferedImage img) {
        int size = this.size.value();
        int shift = this.shift.value();
        double scale = size / (double) Math.min(img.getWidth(), img.getHeight());
        double newWidth = img.getWidth() * scale;
        double newHeight = img.getHeight() * scale;
        BufferedImage res = new BufferedImage((int)newWidth + 1, (int)newHeight + 1, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = res.getGraphics();
        graphics.drawImage(img, 0, 0, res.getWidth(), res.getHeight(), null);
        if(newHeight < newWidth)
            return res.getSubimage((int) (((res.getWidth()-size)/100d) * shift), 0, size, size);
        else if(newWidth < newHeight)
            return res.getSubimage(0, (int) (((res.getHeight()-size)/100d) * shift), size, size);
        else return res.getSubimage(0, 0, size, size);
    }

    @Override
    public VBox createSettingsPane() {
        VBox box = new VBox(
                createNameLabel(),
                size.createRepresentFX(),
                shift.createRepresentFX()
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
        size.toXML(document, props, "size");
        shift.toXML(document, props, "shift");
    }
}