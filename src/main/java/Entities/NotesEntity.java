package Entities;

public class NotesEntity 
{

    private String createdAt;
    private String updatedAt;
    private String remarks;
    
    public NotesEntity(String createdAt, String updatedAt, String remarks) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.remarks = remarks;
    }
    public NotesEntity(){}
   
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
}
