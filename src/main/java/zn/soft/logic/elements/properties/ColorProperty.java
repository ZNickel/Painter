package zn.soft.logic.elements.properties;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import zn.soft.logic.elements.Updatable;

import java.awt.*;

public class ColorProperty extends Property{

    private final NumericProperty red, green, blue, alpha;

    public ColorProperty(String propertyName, Updatable updatable, boolean isComponent) {
        super(propertyName, updatable, isComponent);
        red = new NumericProperty("Red:", updatable, true, 0, 0, 255, "SliderRed");
        green = new NumericProperty("Green:", updatable, true, 0, 0, 255, "SliderGreen");
        blue = new NumericProperty("Blue:", updatable, true, 0, 0, 255, "SliderBlue");
        alpha = new NumericProperty("Alpha:", updatable, true, 0, 255, 255, "SliderWhite");
    }

    public ColorProperty(String propertyName, Updatable updatable, boolean isComponent, int r, int g, int b, int a) {
        super(propertyName, updatable, isComponent);
        red = new NumericProperty("Red:", updatable, true, 0, r, 255, "SliderRed");
        green = new NumericProperty("Green:", updatable, true, 0, g, 255, "SliderGreen");
        blue = new NumericProperty("Blue:", updatable, true, 0, b, 255, "SliderBlue");
        alpha = new NumericProperty("Alpha:", updatable, true, 0, a, 255, "SliderWhite");
    }
    public ColorProperty(Updatable updatable, boolean isComponent, Node node) {
        super(((Element)node).getAttribute("name"), updatable, isComponent);
        Element element = (Element) node;
        int redVal = Integer.parseInt(element.getAttribute("red"));
        int greenVal = Integer.parseInt(element.getAttribute("green"));
        int blueVal = Integer.parseInt(element.getAttribute("blue"));
        int alphaVal = Integer.parseInt(element.getAttribute("alpha"));
        red = new NumericProperty("Red:", updatable, true, 0, redVal, 255, "SliderRed");
        green = new NumericProperty("Green:", updatable, true, 0, greenVal, 255, "SliderGreen");
        blue = new NumericProperty("Blue:", updatable, true, 0, blueVal, 255, "SliderBlue");
        alpha = new NumericProperty("Alpha:", updatable, true, 0, alphaVal, 255, "SliderWhite");
    }

    @Override
    public VBox createRepresentFX() {
        VBox box = new VBox(
                createNameLabel(),
                red.createRepresentFX(),
                green.createRepresentFX(),
                blue.createRepresentFX(),
                alpha.createRepresentFX()
        );
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("Background");
        if (!isComponent) box.getStyleClass().add("BorderBottom");
        return box;
    }

    @Override
    public void toXML(Document document, Element parent, String tag) {
        Element root = document.createElement(tag);
        root.setAttribute("name", propertyName);
        root.setAttribute("red", red.value() + "");
        root.setAttribute("green", green.value() + "");
        root.setAttribute("blue", blue.value() + "");
        root.setAttribute("alpha", alpha.value() + "");
        parent.appendChild(root);
    }

    public int red() {return red.value();}
    public int green() {return green.value();}
    public int blue() {return blue.value();}
    public int alpha() {return alpha.value();}
    public Color AWT(){return new Color(red(), green(), blue(), alpha());}
    public int RGB(){return AWT().getRGB();}
}
