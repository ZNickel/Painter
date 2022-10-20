package zn.soft.logic.elements.properties;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import zn.soft.logic.elements.Updatable;

public class BooleanProperty extends Property{

    private boolean value;

    public BooleanProperty(String propertyName, Updatable updatable, boolean isComponent, boolean value) {
        super(propertyName, updatable, isComponent);
        this.value = value;
    }
    public BooleanProperty(Updatable updatable, boolean isComponent, Node node) {
        super(((Element)node).getAttribute("name"), updatable, isComponent);
        value = ((Element)node).getAttribute("value").equals("true");
    }

    @Override
    public VBox createRepresentFX() {
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(value);
        checkBox.getStyleClass().add("CheckBox");
        checkBox.setPrefWidth(174d);
        checkBox.setPrefHeight(30d);
        checkBox.setText(propertyName);
        checkBox.selectedProperty().addListener((obs, ov, nv) -> {
            value = checkBox.isSelected();
            updatable.update();
        });

        VBox box = new VBox(checkBox);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("Background");
        if (!isComponent) box.getStyleClass().add("BorderBottom");
        return box;
    }

    @Override
    public void toXML(Document document, Element parent, String tag) {
        Element root = document.createElement(tag);
        root.setAttribute("name", propertyName);
        root.setAttribute("value", value ? "true" : "false");
        parent.appendChild(root);
    }

    public boolean value() {return value;}
    public void setValue(boolean value) {this.value = value;}
}
