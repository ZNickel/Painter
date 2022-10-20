package zn.soft.logic.elements.properties;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import zn.soft.logic.elements.Updatable;

public abstract class Property{
    protected final String propertyName;
    protected final Updatable updatable;
    protected final boolean isComponent;

    public Property(String propertyName, Updatable updatable, boolean isComponent) {
        this.propertyName = propertyName;
        this.updatable = updatable;
        this.isComponent = isComponent;
    }

    protected Label createNameLabel(){
        Label label = new Label(propertyName);
        label.setPrefWidth(174d);
        label.setPrefHeight(30d);
        label.getStyleClass().add("Lb");
        return label;
    }

    public abstract VBox createRepresentFX();
    public abstract void toXML(Document document, Element parent, String tag);
}
