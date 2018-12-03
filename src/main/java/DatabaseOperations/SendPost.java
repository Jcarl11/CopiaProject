package DatabaseOperations;

import Entities.ClientEntity;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.callback.SaveCallback;

/**
 *
 * @author Joey Francisco
 */
public class SendPost extends Thread
{
    ClientEntity client;
    ParseObject obj;
    ArrayList<String> tags;
    String result = null;
    volatile boolean running = true;
    volatile int iterations = 0;
    
    public SendPost(ClientEntity client, String dataClass)
    {
        this.client = client;
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
                obj.put("Representative", client.getRepresentative());
                obj.put("Position", client.getPosition());
                obj.put("Company", client.getCompany_Name());
                obj.put("Industry", client.getIndustry());
                obj.put("Type", client.getType());
                obj.put("Tags", new JSONArray(extractStringsToTags()));
                obj.saveInBackground(new SaveCallback() 
                {
                    @Override
                    public void done(ParseException parseException) 
                    {
                        if(parseException == null)
                        {
                            if(client.getFileToUpload() != null)
                            {
                                try
                                {
                                    for(final File individual : client.getFileToUpload() )
                                    {
                                        FileUpload fileUpload = new FileUpload(individual, individual.getName(), obj.getObjectId(),"ClientPointer", "Client");
                                        if(getFileType(individual.getAbsolutePath()) == "Image")
                                        {
                                            Thread t1 = new Thread(fileUpload);
                                            t1.start();
                                            t1.join();
                                        }
                                        else if(getFileType(individual.getAbsolutePath()) == "pdf")
                                        {
                                            System.out.println("Goes here first");
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
        tags.add(client.getRepresentative());
        tags.add(client.getPosition());
        tags.add(client.getCompany_Name());
        tags.add(client.getIndustry());
        tags.add(client.getType());
        String[] representativeSplit = client.getRepresentative().split("\\s+");
        String[] positionSplit = client.getPosition().split("\\s+");
        String[] companySplit = client.getCompany_Name().split("\\s+");
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
