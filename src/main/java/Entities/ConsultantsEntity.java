package Entities;

import java.io.File;
import java.util.ArrayList;

public class ConsultantsEntity 
{
    private String representative;
    private String position;
    private String companyName;
    private String specialization;
    private String industry;
    private String classification;
    private ArrayList<File> fileToUpload;
    
    public ConsultantsEntity(String representative, String position, String companyName, String specialization, String industry, String classification, ArrayList<File> fileToUpload) 
    {
        this.representative = representative;
        this.position = position;
        this.companyName = companyName;
        this.specialization = specialization;
        this.industry = industry;
        this.classification = classification;
        this.fileToUpload = fileToUpload;
    }
    public ConsultantsEntity(){}
    
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
    
}
