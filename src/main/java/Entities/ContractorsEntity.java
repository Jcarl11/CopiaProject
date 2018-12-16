package Entities;

import MiscellaneousClasses.MyUtils;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class ContractorsEntity 
{

    
    private String objectId;
    private String representative;
    private String position;
    private String companyName;
    private String specialization;
    private String industry;
    private String classification;
    private ArrayList<File> fileToUpload;
    private ArrayList<NotesEntity> notes;
    
    public ContractorsEntity(String objectId, String representative, String position, String companyName, String specialization, String industry, String classification, ArrayList<File> fileToUpload) 
    {
        this.objectId = objectId;
        this.representative = representative;
        this.position = position;
        this.companyName = companyName;
        this.specialization = specialization;
        this.industry = industry;
        this.classification = classification;
        this.fileToUpload = fileToUpload;
    }
    public ContractorsEntity(){}
    
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public ArrayList<File> getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(ArrayList<File> fileToUpload) {
        this.fileToUpload = fileToUpload;
    }
    public ArrayList<NotesEntity> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<NotesEntity> notes) {
        this.notes = notes;
    }
    public JSONObject buildJSON()
    {
        JSONObject json = new JSONObject();
        json.put("Representative", getRepresentative());
        json.put("Position", getPosition());
        json.put("Company", getCompanyName());
        json.put("Specialization", getSpecialization());
        json.put("Industry", getIndustry());
        json.put("Classification", getClassification());
        json.put("Tags", new JSONArray(MyUtils.getInstance().contractors_extractStringsToTags(this)));
        return json;
    }
}
