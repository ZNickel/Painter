package zn.soft.logic.controllers;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class UI {

    public static void openNewModel(AnchorPane parent, URL url){
        try {
            FXMLLoader loader = new FXMLLoader(url);
            AnchorPane anchorPane = loader.load();
            AnchorPane.setLeftAnchor(anchorPane, 0d);
            AnchorPane.setRightAnchor(anchorPane, 0d);
            AnchorPane.setTopAnchor(anchorPane, 0d);
            AnchorPane.setBottomAnchor(anchorPane, 0d);
            parent.getChildren().clear();
            parent.getChildren().add(anchorPane);
            // TODO: 27.09.2022 Add fade effect
        } catch (IOException e) {
            throw new RuntimeException(e);
            // TODO: 26.09.2022 Error message. IOException
        }
    }

    public static Stage openAppModal(URL url, boolean resizable, String name){
        try{
            Stage stage = new Stage();
            Parent parent = FXMLLoader.load(url);
            stage.setScene(new Scene(parent));
            stage.setTitle(name);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(resizable);
            stage.getIcons().add(
                    new Image(Objects.requireNonNull(UI.class.getResource("/images/FilterSelectIcon.png")
            ).openStream()));
            stage.show();
            return stage;
        }
        catch (IOException ex){
            ex.printStackTrace();
            return null;
            // TODO: 26.09.2022 Error message. IOException
        }
    }
}
