package zn.soft.logic.elements.properties;

import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import zn.soft.logic.elements.Updatable;

public class NumericProperty extends Property{
    private final int min, max;
    private int value;
    private final String style;

    public NumericProperty(String propertyName, Updatable updatable, boolean isComponent, int min, int value, int max, String style) {
        super(propertyName, updatable, isComponent);
        this.min = min;
        this.value = value;
        this.max = max;
        this.style = style;
    }

    public NumericProperty(String propertyName, Updatable updatable, boolean isComponent, int min, int value, int max) {
        super(propertyName, updatable, isComponent);
        this.min = min;
        this.value = value;
        this.max = max;
        this.style = null;
    }
    public NumericProperty(Updatable updatable, boolean isComponent, Node node) {
        super(((Element)node).getAttribute("name"), updatable, isComponent);
        Element element = (Element) node;
        min = Integer.parseInt(element.getAttribute("min"));
        value = Integer.parseInt(element.getAttribute("value"));
        max = Integer.parseInt(element.getAttribute("max"));
        style = element.getAttribute("style");
    }

    @Override
    public VBox createRepresentFX() {
        Slider slider = createSlider();
        TextField textField = createTextField();

        slider.valueProperty().addListener((obs, ov, nv) -> sliderChangeListener(slider, textField));
        textField.textProperty().addListener((obs, ov, nv) -> textFieldChangeListener(slider, textField, ov, nv));

        HBox hBox = new HBox(slider, textField);
        hBox.setAlignment(Pos.CENTER);

        VBox box = new VBox(createNameLabel(), hBox);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("Background");
        if (!isComponent) box.getStyleClass().add("BorderBottom");
        return box;
    }

    private void textFieldChangeListener(Slider slider, TextField textField, String ov, String nv){
        boolean correct = true;
        for (int i = 0; i < nv.length(); i++)
            if (!Character.isDigit(nv.charAt(i))) correct = false;
        if (correct)
            slider.setValue(Math.min(Math.max(min, Integer.parseInt(nv)), max));
        else{
            textField.setText(ov);
            slider.setValue(Integer.parseInt(ov));
        }
    }
    private void sliderChangeListener(Slider slider, TextField textField){
        value = (int) slider.getValue();
        textField.setText(Integer.toString(value));
        updatable.update();
    }

    private TextField createTextField(){
        TextField textField = new TextField(Integer.toString(value));
        textField.setPrefWidth(40d);
        textField.setPrefHeight(30d);
        textField.setAlignment(Pos.CENTER);
        textField.getStyleClass().add("TFSilent");
        return textField;
    }
    private Slider createSlider(){
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(value);
        slider.setPrefWidth(134d);
        slider.setPrefHeight(30d);
        slider.getStyleClass().add("Slider");
        if (style != null) slider.getStyleClass().add(style);
        return slider;
    }

    @Override
    public void toXML(Document document, Element parent, String tag) {
        Element root = document.createElement(tag);
        root.setAttribute("name", propertyName);
        root.setAttribute("min", min + "");
        root.setAttribute("value", value + "");
        root.setAttribute("max", max + "");
        root.setAttribute("style", style);
        parent.appendChild(root);
    }

    public int value() {return value;}
    public void setValue(int value) {this.value = value;}
}