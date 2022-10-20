package zn.soft.logic.elements;

import javafx.scene.layout.VBox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import zn.soft.logic.elements.filters.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FilterSet {

    protected String name = "default";
    protected final ArrayList<Filter> filters = new ArrayList<>();
    protected final VBox filtersPane, settingsPane;
    protected final Updatable updatable;

    public FilterSet(VBox filtersPane, VBox settingsPane, Updatable updatable) {
        this.filtersPane = filtersPane;
        this.settingsPane = settingsPane;
        this.updatable = updatable;
    }

    public FilterSet(VBox filtersPane, VBox settingsPane, Updatable updatable, File xmlRepresent) throws ParserConfigurationException, IOException, SAXException {
        this.filtersPane = filtersPane;
        this.settingsPane = settingsPane;
        this.updatable = updatable;

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(xmlRepresent);

        Node root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getNodeName().equals("name"))
                this.name = node.getChildNodes().item(0).getTextContent();
            else if (node.getNodeName().equals("filters")){
                NodeList filtersNodes = node.getChildNodes();
                for(int j = 0; j < filtersNodes.getLength(); j++){
                    Node filterNode = filtersNodes.item(j);
                    if (filterNode.getNodeName().equals(FilterType.ADD_IMAGE.tagName()))
                        filters.add(new FilterAddImage(this, updatable, filterNode));
                    else if (filterNode.getNodeName().equals(FilterType.ADD_TEXT.tagName()))
                        filters.add(new FilterAddText(this, updatable, filterNode));
                    else if (filterNode.getNodeName().equals(FilterType.GRADIENT.tagName()))
                        filters.add(new FilterGradient(this, updatable, filterNode));
                    else if (filterNode.getNodeName().equals(FilterType.INDENTS.tagName()))
                        filters.add(new FilterIndents(this, updatable, filterNode));
                    else if (filterNode.getNodeName().equals(FilterType.INVERSION.tagName()))
                        filters.add(new FilterInversion(this, updatable, filterNode));
                    else if (filterNode.getNodeName().equals(FilterType.MONOCHROME.tagName()))
                        filters.add(new FilterMonochrome(this, updatable, filterNode));
                    else if (filterNode.getNodeName().equals(FilterType.SPLIT.tagName()))
                        filters.add(new FilterSplit(this, updatable, filterNode));
                    else if (filterNode.getNodeName().equals(FilterType.SQUARE.tagName()))
                        filters.add(new FilterSquare(this, updatable, filterNode));
                }
            }
        }
        updateFiltersList();
    }

    public BufferedImage apply(BufferedImage img){
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = result.getGraphics();
        graphics.drawImage(img, 0, 0, null);
        for (int i = filters.size() - 1; i >= 0; i--)
            result = filters.get(i).apply(result);
        return result;
    }

    public void updateFiltersList(){
        filtersPane.getChildren().clear();
        settingsPane.getChildren().clear();
        for (Filter filter : filters)
            filtersPane.getChildren().add(filter.createControlPaneElement());
        updatable.update();
    }

    public void add(FilterType type){
        Filter filter = null;
        switch (type){
            case ADD_IMAGE -> filter = new FilterAddImage(this, updatable);
            case ADD_TEXT -> filter = new FilterAddText(this, updatable);
            case INDENTS -> filter = new FilterIndents(this, updatable);
            case MONOCHROME -> filter = new FilterMonochrome(this, updatable);
            case INVERSION -> filter = new FilterInversion(this, updatable);
            case SPLIT -> filter = new FilterSplit(this, updatable);
            case GRADIENT -> filter = new FilterGradient(this, updatable);
            case SQUARE -> filter = new FilterSquare(this, updatable);
        }
        if (filter != null) filters.add(0, filter);
        updateFiltersList();
    }

    public void del(){
        if (filters.size() > 0){
            filters.remove(0);
            updateFiltersList();
        }
    }

    public void del(Filter filter){
        if (filters.size() > 0){
            filters.remove(filter);
            updateFiltersList();
        }
    }

    public void showSettings(Filter filter){
        settingsPane.getChildren().clear();
        settingsPane.getChildren().add(filter.createSettingsPane());
    }

    public Document getXMLRepresent() throws ParserConfigurationException {
        DocumentBuilder builder = DocumentBuilderFactory.newNSInstance().newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement("set");
        document.appendChild(root);

        Element setName = document.createElement("name");
        setName.setTextContent("default");
        root.appendChild(setName);

        Element filtersElem = document.createElement("filters");
        root.appendChild(filtersElem);

        for (Filter filter : filters)
            filter.toXML(document, filtersElem);
        return document;
    }

    public boolean canBeSaved(){
        return filters.size() > 0;
    }

    public void clear(){
        filters.clear();
        updateFiltersList();
    }

    public String name() {
        return name;
    }
}
