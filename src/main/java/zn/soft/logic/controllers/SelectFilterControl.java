package zn.soft.logic.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import zn.soft.logic.elements.filters.FilterType;

public class SelectFilterControl {

    @FXML
    private VBox pane_container;

    private ToggleGroup group = new ToggleGroup();
    private FilterType selectedType = null;

    @FXML
    void pressed_Add() {
        if (selectedType != null){
            ManualControl.addedFilterType = selectedType;
            ((Stage)pane_container.getScene().getWindow()).close();
        }
    }

    @FXML
    void pressed_Cancel() {
        ((Stage)pane_container.getScene().getWindow()).close();
    }

    @FXML
    void initialize() {
        for (FilterType type : FilterType.values()){
            ToggleButton button = new ToggleButton(type.typeName());
            button.setPrefWidth(235d);
            button.setPrefHeight(30d);
            button.getStyleClass().add("Toggle");
            button.getStyleClass().add("BorderBottom");
            button.setToggleGroup(group);
            button.setOnAction(e -> selectedType = button.isSelected() ? type : null);
            pane_container.getChildren().add(button);
        }
    }

}
