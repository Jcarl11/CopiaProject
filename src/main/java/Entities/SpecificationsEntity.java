package Entities;

import java.io.File;
import java.util.ArrayList;

public class SpecificationsEntity
{

    
    private String objectId;
    private String title;
    private String division;
    private String section;
    private String type;
    private String keywords;
    private ArrayList<File> fileToUpload;
    
    public SpecificationsEntity(String objectId, String title, String division, String section, String type, String keywords, ArrayList<File> fileToUpload) 
    {
        this.objectId = objectId;
        this.title = title;
        this.division = division;
        this.section = section;
        this.type = type;
        this.keywords = keywords;
        this.fileToUpload = fileToUpload;
    }
    
    public SpecificationsEntity(){}
    
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public ArrayList<File> getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(ArrayList<File> fileToUpload) {
        this.fileToUpload = fileToUpload;
    }
    
}
