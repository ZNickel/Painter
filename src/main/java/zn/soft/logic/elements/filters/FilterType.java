package zn.soft.logic.elements.filters;

public enum FilterType{
    ADD_IMAGE("Add image", "add_image"),
    ADD_TEXT("Add text", "add_text"),
    INDENTS("Indents", "indents"),
    MONOCHROME("Monochrome", "monochrome"),
    INVERSION("Inversion", "inversion"),
    SPLIT("Split", "split"),
    GRADIENT("Gradient", "gradient"),
    SQUARE("Square", "square");

    private final String typeName, tagName;

    FilterType(String typeName, String tagName) {
        this.typeName = typeName;
        this.tagName = tagName;
    }

    public String typeName() {
        return typeName;
    }

    public String tagName() {
        return tagName;
    }
}