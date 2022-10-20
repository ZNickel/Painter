package zn.soft.logic.elements.filters;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import zn.soft.logic.elements.FilterSet;
import zn.soft.logic.elements.Updatable;

import java.awt.image.BufferedImage;


public abstract class Filter {
    protected final FilterSet filterSet;
    protected final Updatable updatable;
    protected final FilterType type;

    public Filter(FilterSet filterSet, Updatable updatable, FilterType type) {
        this.filterSet = filterSet;
        this.updatable = updatable;
        this.type = type;
    }

    protected Label createNameLabel(){
        Label label = new Label(type.typeName());
        label.setPrefWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("Lb");
        label.getStyleClass().add("BorderBottom");
        return label;
    }

    public abstract BufferedImage apply(BufferedImage img);
    public abstract void toXML(Document document, Element parent);

    public abstract VBox createSettingsPane();
    public AnchorPane createControlPaneElement() {
        Button btnDelete = new Button("X");
        btnDelete.setPrefWidth(20d);
        btnDelete.setPrefHeight(20d);
        AnchorPane.setBottomAnchor(btnDelete,0d);
        AnchorPane.setTopAnchor(btnDelete,0d);
        AnchorPane.setLeftAnchor(btnDelete,0d);
        btnDelete.setOnAction(e -> filterSet.del(this));
        btnDelete.getStyleClass().add("Btn");
        btnDelete.getStyleClass().add("RedText");
        btnDelete.getStyleClass().add("BorderBottom");

        Label label = new Label(type.typeName());
        label.setAlignment(Pos.CENTER);
        AnchorPane.setBottomAnchor(label,0d);
        AnchorPane.setTopAnchor(label,0d);
        AnchorPane.setLeftAnchor(label,20d);
        AnchorPane.setRightAnchor(label,20d);
        label.getStyleClass().add("Lb");
        label.getStyleClass().add("BorderBottom");

        Button btnSettings = new Button("?");
        btnSettings.setPrefWidth(20d);
        btnSettings.setPrefHeight(20d);
        AnchorPane.setBottomAnchor(btnSettings,0d);
        AnchorPane.setTopAnchor(btnSettings,0d);
        AnchorPane.setRightAnchor(btnSettings,0d);
        btnSettings.setOnAction(e -> filterSet.showSettings(this));
        btnSettings.getStyleClass().add("Btn");
        btnSettings.getStyleClass().add("YellowText");
        btnSettings.getStyleClass().add("BorderBottom");

        AnchorPane box = new AnchorPane(btnDelete, label, btnSettings);
        box.setPrefWidth(184d);
        return box;
    }


}
