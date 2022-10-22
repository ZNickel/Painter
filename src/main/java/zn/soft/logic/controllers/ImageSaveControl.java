package zn.soft.logic.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSaveControl {
    @FXML private TextField tf_ImageName;
    @FXML private TextField tf_SavePath;

    private String savePath = "";

    @FXML void pressed_BrowseSavePath() {
        File file = new DirectoryChooser().showDialog(tf_SavePath.getScene().getWindow());
        savePath = file == null ? "" : file.getAbsolutePath();
        tf_SavePath.setText(savePath);
    }

    @FXML void pressed_Cancel() {
        ((Stage)tf_ImageName.getScene().getWindow()).close();
    }

    @FXML void pressed_Save() {
        if (tf_SavePath.getText() != null && tf_ImageName.getText() != null){
            if (tf_SavePath.getText().length() > 0 && tf_ImageName.getText().length() > 0){
                BufferedImage bufferedImage = ManualControl.lastGenerated;
                if (bufferedImage != null){
                    try {
                        File file = new File(savePath + "/" + tf_ImageName.getText() + ".png");
                        ImageIO.write(bufferedImage, "png", file);
                        ((Stage)tf_ImageName.getScene().getWindow()).close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

}
