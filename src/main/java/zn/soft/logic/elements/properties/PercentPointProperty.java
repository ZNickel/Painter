package zn.soft.logic.elements.properties;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import zn.soft.logic.elements.Updatable;

public class PercentPointProperty extends Property{

    private final NumericProperty x, y;

    public PercentPointProperty(String propertyName, Updatable updatable, boolean isComponent) {
        super(propertyName, updatable, isComponent);
        x = new NumericProperty("X:", updatable, true, 0, 50, 100);
        y = new NumericProperty("Y:", updatable, true, 0, 50, 100);
    }
    public PercentPointProperty(String propertyName, Updatable updatable, boolean isComponent, int x, int y) {
        super(propertyName, updatable, isComponent);
        this.x = new NumericProperty("X:", updatable, true, 0, x, 100);
        this.y = new NumericProperty("Y:", updatable, true, 0, y, 100);
    }
    public PercentPointProperty(Updatable updatable, boolean isComponent, Node node) {
        super(((Element)node).getAttribute("name"), updatable, isComponent);
        Element element = (Element) node;
        int xVal = Integer.parseInt(element.getAttribute("x"));
        int yVal = Integer.parseInt(element.getAttribute("y"));
        x = new NumericProperty("X:", updatable, true, 0, xVal, 100);
        y = new NumericProperty("Y:", updatable, true, 0, yVal, 100);
    }

    @Override
    public VBox createRepresentFX() {
        Label label = createNameLabel();
        VBox box = new VBox(label, x.createRepresentFX(), y.createRepresentFX());
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("Background");
        if (!isComponent) box.getStyleClass().add("BorderBottom");
        return box;
    }

    @Override
    public void toXML(Document document, Element parent, String tag) {
        Element root = document.createElement(tag);
        root.setAttribute("name", propertyName);
        root.setAttribute("x", x.value() + "");
        root.setAttribute("y", y.value() + "");
        parent.appendChild(root);
    }

    public int x() {return x.value();}
    public int y() {return y.value();}
}