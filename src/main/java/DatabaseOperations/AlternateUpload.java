package DatabaseOperations;

import Entities.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import org.apache.commons.io.FilenameUtils;
import org.asynchttpclient.Response;
import org.json.JSONArray;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.SaveCallback;

public class AlternateUpload
{
    boolean isFinished;
    Task<List<Future<Response>>> myTask;
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
        final AlternateFileUpload alt = new AlternateFileUpload();
        final boolean isFinished = false;
        
        this.clientEntity = clientEntity;
        myTask = new Task<List<Future<Response>>>() 
        {
            List<Future<Response>> responses;
            @Override
            protected List<Future<Response>> call() throws Exception 
            {
                setIsFinished(false);
                final ParseObject parseObject = new ParseObject(searchClass);
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
                        {
                            setIsFinished(true);
                        }
                    }
                });
               
                while(getIsFinished() == false)
                {
                    Thread.sleep(500);
                }
                
                ExecutorService executor = Executors.newFixedThreadPool(5);
                            List<Callable<Response>> callables = new ArrayList<Callable<Response>>();
                            for(File file : clientEntity.getFileToUpload())
                            {
                                try {
                                    byte[] data = Files.readAllBytes(file.toPath());
                                    alt.setData(data);
                                    alt.setFilename(file.getName());
                                    callables.add(alt);
                                } catch (IOException ex) {
                                    Logger.getLogger(AlternateUpload.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            try {
                                responses = executor.invokeAll(callables);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(AlternateUpload.class.getName()).log(Level.SEVERE, null, ex);
                            }
                return responses;
            }
        };
        new Thread(myTask).start();
    }
    
    public Task<List<Future<Response>>> getTask()
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
