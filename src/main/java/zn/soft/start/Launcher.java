package zn.soft.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/models/Base.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("/style/StyleDark.css").toString());
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/AppIcon.png")).openStream()));
        stage.setTitle("IconBuilder v0.3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
