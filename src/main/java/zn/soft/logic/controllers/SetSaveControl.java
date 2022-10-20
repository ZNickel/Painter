package zn.soft.logic.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;

public class SetSaveControl {
    @FXML private TextField tf_SetName;

    private File saveDir = null;

    @FXML void pressed_Cancel() {
        ((Stage)tf_SetName.getScene().getWindow()).close();
    }

    @FXML void pressed_Save() {
        var ready = tf_SetName.getText() != null && tf_SetName.getText().length() != 0;
        if (ManualControl.set == null || !ManualControl.set.canBeSaved()) ready = false;
        if (ready){
            try {
                var dir = new File("preset");
                dir.mkdirs();
                var file = new File(dir.getPath() + "/" + tf_SetName.getText() + ".xml");
                var transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                var streamResult = new StreamResult(new FileOutputStream(file));
                transformer.transform(new DOMSource(ManualControl.set.getXMLRepresent()), streamResult);
                System.out.println("SAVED!");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("NO SAVED...");
                // TODO: 19.10.2022 XML save/parse Exceptions
            }
            ((Stage)tf_SetName.getScene().getWindow()).close();
        }
    }
}
