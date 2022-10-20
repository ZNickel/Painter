package zn.soft.logic.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class BaseControl {
    @FXML private AnchorPane basePane;

    @FXML void initialize() {
        UI.openNewModel(basePane, getClass().getResource("/models/Menu.fxml"));
    }
}
