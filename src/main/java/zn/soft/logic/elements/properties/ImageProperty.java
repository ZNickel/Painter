package zn.soft.logic.elements.properties;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import zn.soft.logic.elements.Updatable;

import java.io.File;

public class ImageProperty extends Property{

    private File file = null;

    public ImageProperty(String propertyName, Updatable updatable, boolean isComponent) {
        super(propertyName, updatable, isComponent);
    }
    public ImageProperty(Updatable updatable, boolean isComponent, Node node) {
        super(((Element)node).getAttribute("name"), updatable, isComponent);
        String filename = ((Element)node).getAttribute("file");
        if (!filename.equals("null")) file = new File(filename);
    }

    @Override
    public VBox createRepresentFX() {
        Button btn = new Button(file == null ? "Browse" : file.getName());
        btn.setPrefWidth(174d);
        btn.setPrefHeight(30d);
        btn.getStyleClass().add("Btn");
        btn.setOnAction(event -> {
            file = new FileChooser().showOpenDialog(null);
            btn.setText(file == null ? "Browse" : file.getName());
            updatable.update();
        });
        VBox.setMargin(btn, new Insets(0d, 0d, 1d, 0d));
        VBox box = new VBox(createNameLabel(), btn);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("Background");
        if (!isComponent) box.getStyleClass().add("BorderBottom");
        return box;
    }


    @Override
    public void toXML(Document document, Element parent, String tag) {
        Element root = document.createElement(tag);
        root.setAttribute("name", propertyName);
        root.setAttribute("file", file != null ? file.getAbsolutePath() : "null");
        parent.appendChild(root);
    }

    public File file() {return file;}
}