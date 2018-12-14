package DatabaseOperations;

import java.util.ArrayList;
import javafx.concurrent.Task;

public class RetrieveFileTask 
{
    private String id;
    private String searchClass;
    private String field;
    private String imageClass;
    private String pdfClass;
    private RetrieveFileTask(){}
    private static RetrieveFileTask instance = null;
    public static RetrieveFileTask getInstance()
    {
        if(instance == null)
            instance = new RetrieveFileTask();
        return instance;
    }
    Task<ArrayList<String>> retrieveFileTask;
    
    public void retrieveFile()
    {
        retrieveFileTask = new Task<ArrayList<String>>() 
        {
            @Override
            protected ArrayList<String> call() throws Exception 
            {
                ArrayList<String> results = new ArrayList<>();
                results = RetrieveFiles.getInstance().retrieve(getId(), getSearchClass(), getField(), getImageClass(), getPdfClass());
                return results;
            }
        };
        new Thread(retrieveFileTask).start();
    }
    
    public Task<ArrayList<String>> getTask() {
        return retrieveFileTask;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSearchClass() {
        return searchClass;
    }

    public void setSearchClass(String searchClass) {
        this.searchClass = searchClass;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getImageClass() {
        return imageClass;
    }

    public void setImageClass(String imageClass) {
        this.imageClass = imageClass;
    }

    public String getPdfClass() {
        return pdfClass;
    }

    public void setPdfClass(String pdfClass) {
        this.pdfClass = pdfClass;
    }
}
