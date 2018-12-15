package Entities;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Joey Francisco
 */
public class ClientEntity 
{
    private String ObjectID;
    private String Representative;
    private String Position;
    private String Company_Name;
    private String Industry;
    private String Type;
    private ArrayList<File> fileToUpload;
    private ArrayList<NotesEntity> notes;

    public ClientEntity(String ObjectID, String Representative, String Position, String Company_Name,String Industry, String Type) {
        this.ObjectID = ObjectID;
        this.Representative = Representative;
        this.Position = Position;
        this.Company_Name = Company_Name;
        this.Industry = Industry;
        this.Type = Type;
    }
    public ClientEntity(){}
      
    public ArrayList<File> getFileToUpload() {
        return fileToUpload;
    }

    public void setFileToUpload(ArrayList<File> fileToUpload) {
        this.fileToUpload = fileToUpload;
    }

    public void setObjectID(String ObjectID) {
        this.ObjectID = ObjectID;
    }
    
    public String getObjectID() {
        return ObjectID;
    }
    
    public String getRepresentative() {
        return Representative;
    }

    public void setRepresentative(String Representative) {
        this.Representative = Representative;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String Company_Name) {
        this.Company_Name = Company_Name;
    }

    public String getIndustry() {
        return Industry;
    }

    public void setIndustry(String Industry) {
        this.Industry = Industry;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }
    public ArrayList<NotesEntity> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<NotesEntity> notes) {
        this.notes = notes;
    }
}
