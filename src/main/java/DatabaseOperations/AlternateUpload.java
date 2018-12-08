package DatabaseOperations;

import Entities.*;
import java.util.ArrayList;
import javafx.concurrent.Task;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.SaveCallback;

public class AlternateUpload
{
    boolean isFinished;
    Task<String> myTask;
    ClientEntity clientEntity;
    private AlternateUpload(){}
    private static AlternateUpload instance = null;
    public static  AlternateUpload getInstance()
    {
        if(instance == null)
            instance = new AlternateUpload();
        return instance;
    }

    public void clientInsertRecord(final ClientEntity clientEntity, final String searchClass)
    {
        final boolean isFinished = false;
        this.clientEntity = clientEntity;
        myTask = new Task<String>() 
        {
            @Override
            protected String call() throws Exception 
            {
                setIsFinished(false);
                ParseObject parseObject = new ParseObject(searchClass);
                parseObject.put("Representative", clientEntity.getRepresentative());
                parseObject.put("Position", clientEntity.getPosition());
                parseObject.put("Company", clientEntity.getCompany_Name());
                parseObject.put("Industry", clientEntity.getIndustry());
                parseObject.put("Type", clientEntity.getType());
                parseObject.put("Tags", new JSONArray(extractStringsToTags()));
                parseObject.saveInBackground(new SaveCallback() 
                {
                    @Override
                    public void done(ParseException parseException) 
                    {
                        if(parseException == null)
                            setIsFinished(true);
                    }
                });
                while(getIsFinished() == false)
                {
                    Thread.sleep(500);
                }
                return "Successful";
            }
        };
        new Thread(myTask).start();
    }
    
    public Task<String> getTask()
    {
        return myTask;
    }
    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
    private ArrayList<String> extractStringsToTags()
    {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(clientEntity.getRepresentative());
        tags.add(clientEntity.getPosition());
        tags.add(clientEntity.getCompany_Name());
        tags.add(clientEntity.getIndustry());
        tags.add(clientEntity.getType());
        String[] representativeSplit = clientEntity.getRepresentative().split("\\s+");
        String[] positionSplit = clientEntity.getPosition().split("\\s+");
        String[] companySplit = clientEntity.getCompany_Name().split("\\s+");
        for(String values : representativeSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : positionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : companySplit)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
    
    private String getFileType(String filePath)
    {
        String type = "";
        String extension = FilenameUtils.getExtension(filePath).toLowerCase();
        if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("jpeg"))
        {
            type = "Image";
        }
        else if(extension.equalsIgnoreCase("pdf"))
        {
            type = "pdf";
        }
        return type;
    }
    
}
