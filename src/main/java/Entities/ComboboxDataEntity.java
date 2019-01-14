package Entities;

public class ComboboxDataEntity 
{

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public ComboboxDataEntity(String objectId, String title, String category, String field) {
        this.objectId = objectId;
        this.title = title;
        this.category = category;
        this.field = field;
    }
    public ComboboxDataEntity(){}
    private String objectId;
    private String title;
    private String category;
    private String field;
    
    
}
