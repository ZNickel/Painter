package zn.soft.logic.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import zn.soft.logic.elements.FilterSet;
import zn.soft.logic.elements.filters.FilterType;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ManualControl {
    @FXML private ImageView imgView;
    @FXML private Label lb_ImageName;
    @FXML private Label lb_SetName;
    @FXML private AnchorPane pane;
    @FXML private VBox pane_Filters;
    @FXML private StackPane pane_Img;
    @FXML private VBox pane_Settings;

    private File originalImageFile = null;
    public static File addedSet = null;
    public File loadedSet = null;
    public static FilterSet set = null;
    public static FilterType addedFilterType = null;
    public static BufferedImage lastGenerated = null;

    @FXML void pressed_Add() {
        Stage stage = UI.openAppModal(getClass().getResource("/models/SelectFilter.fxml"), false, "Select filter");
        if (stage != null) {
            stage.setOnHidden(event -> {
                if (addedFilterType != null) set.add(addedFilterType);
                addedFilterType = null;
            });
        }
    }

    @FXML void pressed_Del() {
        set.del();
    }

    @FXML void pressed_LoadImage() {
        originalImageFile = new FileChooser().showOpenDialog(imgView.getScene().getWindow());
        if (originalImageFile != null) lb_ImageName.setText(originalImageFile.getName());
        update();
    }

    @FXML void pressed_LoadSet() {
        Stage stage = UI.openAppModal(getClass().getResource("/models/LoadSet.fxml"), false, "Select preset");
        if (stage != null) {
            stage.setOnHidden(event -> {
                if (addedSet != null) {
                    try {
                        set = new FilterSet(pane_Filters, pane_Settings, this::update, addedSet);
                        lb_SetName.setText(set.name());
                        loadedSet = addedSet;
                        addedSet = null;
                        update();
                        // TODO: 20.10.2022 Exception
                    } catch (ParserConfigurationException | SAXException | IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @FXML void pressed_Save() {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/models/ImageSave.fxml")));
            Parent parent = loader.load();
            stage.setScene(new Scene(parent));
            stage.setTitle("Save image");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.getIcons().add(
                    new Image(Objects.requireNonNull(UI.class.getResource("/images/FilterSelectIcon.png")
                    ).openStream()));
            stage.show();
        }
        catch (IOException ex){
            ex.printStackTrace();
            // TODO: 26.09.2022 Error message. IOException
        }
    }

    @FXML void pressed_SaveSet() {
        UI.openAppModal(getClass().getResource("/models/SetSave.fxml"), false, "Save set");
    }

    @FXML void pressed_Back() {
        UI.openNewModel((AnchorPane) pane.getParent(), getClass().getResource("/models/Menu.fxml"));
    }

    @FXML void pressed_Reset() {
        if (loadedSet != null){
            try {
                set = new FilterSet(pane_Filters, pane_Settings, this::update, loadedSet);
                update();
                // TODO: 20.10.2022 Exception
            } catch (ParserConfigurationException | IOException | SAXException e) {
                e.printStackTrace();
            }
        }
        else {
            set.clear();
        }
    }

    @FXML
    void initialize() {
        set = new FilterSet(pane_Filters, pane_Settings, this::update);
    }

    private void update(){
        if (originalImageFile != null && originalImageFile.exists()){
            try {
                lastGenerated = set.apply(ImageIO.read(originalImageFile));
                double minContDim = (Math.min(pane_Img.getWidth(), pane_Img.getHeight()) - 10);
                double maxImgDim = Math.max(lastGenerated.getHeight(), lastGenerated.getWidth());
                double scale = minContDim / maxImgDim;
                int w = (int) (lastGenerated.getWidth() * scale);
                int h = (int) (lastGenerated.getHeight() * scale);
                BufferedImage paste = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics graphics = paste.getGraphics();
                graphics.drawImage(lastGenerated, 0, 0, w, h, null);
                imgView.setFitWidth(w);
                imgView.setFitHeight(h);
                imgView.setImage(SwingFXUtils.toFXImage(paste, null));
            } catch (IOException ex) {
                ex.printStackTrace();
                //TODO IOException
            }
        }
    }
}
