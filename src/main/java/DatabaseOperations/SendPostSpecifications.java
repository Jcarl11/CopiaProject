package DatabaseOperations;

import Entities.SpecificationsEntity;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.SaveCallback;

public class SendPostSpecifications extends Thread
{
    SpecificationsEntity specificationsEntity;
    ParseObject obj;
    ArrayList<String> tags;
    String result = null;
    volatile boolean running = true;
    volatile int iterations = 0;
    
    public SendPostSpecifications(SpecificationsEntity specificationsEntity, String dataClass)
    {
        this.specificationsEntity = specificationsEntity;
        obj = new ParseObject(dataClass);
        tags = new ArrayList<>();
    }    
    public void terminate()
    {
        running = false;
    }
    public String getResult()
    {
        return result;
    }
    
    @Override
    public void run() 
    {
        while(running)
        {
            if(iterations <= 0)
            {
                obj.put("Title", specificationsEntity.getTitle());
                obj.put("Division", specificationsEntity.getDivision());
                obj.put("Section", specificationsEntity.getSection());
                obj.put("Type", specificationsEntity.getType());
                obj.put("Tags", new JSONArray(extractStringsToTags()));
                obj.saveInBackground(new SaveCallback() 
                {
                    @Override
                    public void done(ParseException parseException) 
                    {
                        if(parseException == null)
                        {
                            if(specificationsEntity.getFileToUpload() != null)
                            {
                                try
                                {
                                    for(final File individual : specificationsEntity.getFileToUpload() )
                                    {
                                        FileUpload fileUpload = new FileUpload(individual, individual.getName(), obj.getObjectId(),"SpecificationsPointer", "Specifications");
                                        if(getFileType(individual.getAbsolutePath()) == "pdf")
                                        {
                                            Thread t1 = new Thread(fileUpload);
                                            t1.start();
                                            t1.join();
                                        }
                                    }
                                    result = "Successful";
                                    terminate();
                                }catch(Exception ex)
                                {
                                    ex.printStackTrace();
                                }
                            }
                            else
                            {
                                result = "Successful";
                                terminate();
                            }

                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Something went wrong");
                            terminate();
                        }
                    }
                });
                iterations++;
            }
        }
    }
    
    private ArrayList<String> extractStringsToTags()
    {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(specificationsEntity.getTitle());
        tags.add(specificationsEntity.getDivision());
        tags.add(specificationsEntity.getSection());
        tags.add(specificationsEntity.getType());
        tags.add(specificationsEntity.getKeywords());
        String[] titleSplit = specificationsEntity.getTitle().split("\\s+");
        String[] divisionSplit = specificationsEntity.getDivision().split("\\s+");
        String[] sectionSplit = specificationsEntity.getSection().split("\\s+");
        String[] typeSplit = specificationsEntity.getType().split("\\s+");
        String[] keywordSplit = specificationsEntity.getKeywords().split("\\s+");
        for(String values : titleSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : divisionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : sectionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : typeSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : keywordSplit)
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
