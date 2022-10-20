package zn.soft.logic.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MenuControl {
    @FXML private AnchorPane pane;

    @FXML void pressed_Auto() {

    }

    @FXML void pressed_EditPresets() {

    }

    @FXML void pressed_Exit() {
        System.exit(0);
    }

    @FXML void pressed_FullAuto() {

    }

    @FXML void pressed_Manual() {
        UI.openNewModel((AnchorPane) pane.getParent(), getClass().getResource("/models/Manual.fxml"));
    }

    @FXML void pressed_Options() {

    }

    @FXML void initialize() {

    }

}
