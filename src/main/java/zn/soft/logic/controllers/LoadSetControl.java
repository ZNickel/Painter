package zn.soft.logic.controllers;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoadSetControl {
    @FXML private VBox container;

    private ToggleGroup group = new ToggleGroup();
    private File selectedType = null;

    @FXML void pressed_Cancel() {
        ((Stage)container.getScene().getWindow()).close();
    }
    @FXML void pressed_Load() {
        if (selectedType != null){
            ManualControl.addedSet = selectedType;
            ((Stage)container.getScene().getWindow()).close();
        }
    }
    @FXML void initialize() {
        File dir = new File("preset");
        for (File file : dir.listFiles()){
            // TODO: 20.10.2022 Rewrite split to filename and file extension
            ToggleButton button = new ToggleButton(file.getName().split("\\.")[0]);
            button.setPrefWidth(285d);
            button.setPrefHeight(30d);
            button.getStyleClass().add("Toggle");
            button.getStyleClass().add("BorderBottom");
            button.setToggleGroup(group);
            button.setOnAction(e -> selectedType = button.isSelected() ? file : null);
            container.getChildren().add(button);
        }
    }
}
