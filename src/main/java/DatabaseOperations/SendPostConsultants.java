package DatabaseOperations;

import Entities.ConsultantsEntity;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.SaveCallback;

public class SendPostConsultants extends Thread
{
    ConsultantsEntity consultantsEntity;
    ParseObject obj;
    ArrayList<String> tags;
    String result = null;
    volatile boolean running = true;
    volatile int iterations = 0;
    
    public SendPostConsultants(ConsultantsEntity consultantsEntity, String dataClass)
    {
        this.consultantsEntity = consultantsEntity;
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
                obj.put("Representative", consultantsEntity.getRepresentative());
                obj.put("Position", consultantsEntity.getPosition());
                obj.put("Company", consultantsEntity.getCompanyName());
                obj.put("Specialization", consultantsEntity.getSpecialization());
                obj.put("Industry", consultantsEntity.getIndustry());
                obj.put("Classification", consultantsEntity.getClassification());
                obj.put("Tags", new JSONArray(extractStringsToTags()));
                obj.saveInBackground(new SaveCallback() 
                {
                    @Override
                    public void done(ParseException parseException) 
                    {
                        if(parseException == null)
                        {
                            if(consultantsEntity.getFileToUpload() != null)
                            {
                                try
                                {
                                    for(final File individual : consultantsEntity.getFileToUpload() )
                                    {
                                        FileUpload fileUpload = new FileUpload(individual, individual.getName(), obj.getObjectId(),"ConsultantsPointer", "Consultants");
                                        if(getFileType(individual.getAbsolutePath()) == "Image")
                                        {
                                            Thread t1 = new Thread(fileUpload);
                                            t1.start();
                                            t1.join();
                                        }
                                        else if(getFileType(individual.getAbsolutePath()) == "pdf")
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
        tags.add(consultantsEntity.getRepresentative());
        tags.add(consultantsEntity.getPosition());
        tags.add(consultantsEntity.getCompanyName());
        tags.add(consultantsEntity.getSpecialization());
        tags.add(consultantsEntity.getIndustry());
        tags.add(consultantsEntity.getClassification());
        String[] representativeSplit = consultantsEntity.getRepresentative().split("\\s+");
        String[] positionSplit = consultantsEntity.getPosition().split("\\s+");
        String[] companySplit = consultantsEntity.getCompanyName().split("\\s+");
        String[] specializationSplit = consultantsEntity.getSpecialization().split("\\s+");
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
        for(String values : specializationSplit)
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