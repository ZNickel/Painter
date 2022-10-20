package zn.soft.logic.elements.properties;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import zn.soft.logic.elements.Updatable;

public class TextProperty extends Property{

    private String value;

    public TextProperty(String propertyName, Updatable updatable, boolean isComponent, String value) {
        super(propertyName, updatable, isComponent);
        this.value = value;
    }
    public TextProperty(Updatable updatable, boolean isComponent, Node node) {
        super(((Element) node).getAttribute("name"), updatable, isComponent);
        value = ((Element) node).getAttribute("value");
    }

    @Override
    public VBox createRepresentFX() {
        TextField textField = new TextField(value);
        textField.setAlignment(Pos.CENTER);
        textField.setPrefWidth(174d);
        textField.setPrefHeight(30d);
        textField.getStyleClass().add("TFSilent");
        textField.textProperty().addListener((obs, ov, nv) -> {
            value = textField.getText();
            updatable.update();
        });

        VBox box = new VBox(createNameLabel(), textField);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("Background");
        if (!isComponent) box.getStyleClass().add("BorderBottom");
        return box;
    }

    @Override
    public void toXML(Document document, Element parent, String tag) {
        Element root = document.createElement(tag);
        root.setAttribute("name", propertyName);
        root.setAttribute("value", value);
        parent.appendChild(root);
    }

    public String text() {return value;}
    public void setValue(String value) {this.value = value;}

}