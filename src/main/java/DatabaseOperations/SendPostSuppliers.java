package DatabaseOperations;

import Entities.SuppliersEntity;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.callback.SaveCallback;

/**
 *
 * @author Joey Francisco
 */
public class SendPostSuppliers extends Thread
{
    SuppliersEntity suppliersEntity;
    ParseObject obj;
    ArrayList<String> tags;
    String result = null;
    volatile boolean running = true;
    volatile int iterations = 0;
    
    public SendPostSuppliers(SuppliersEntity suppliers, String dataClass)
    {
        this.suppliersEntity = suppliers;
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
                obj.put("Representative", suppliersEntity.getRepresentative());
                obj.put("Position", suppliersEntity.getPosition());
                obj.put("Company_Name", suppliersEntity.getCompany_Name());
                obj.put("Brand", suppliersEntity.getBrand());
                obj.put("Industry", suppliersEntity.getIndustry());
                obj.put("Type", suppliersEntity.getType());
                obj.put("Tags", new JSONArray(extractStringsToTags()));
                obj.saveInBackground(new SaveCallback() 
                {
                    @Override
                    public void done(ParseException parseException) 
                    {
                        if(parseException == null)
                        {
                            if(suppliersEntity.getFileToUpload() != null)
                            {
                                try
                                {
                                    for(final File individual : suppliersEntity.getFileToUpload() )
                                    {
                                        final ParseFile fileobj = new ParseFile(individual.getName(), Files.readAllBytes(individual.toPath()));
                                        fileobj.saveInBackground(new SaveCallback() 
                                        {
                                            @Override
                                            public void done(ParseException parseException) 
                                            {
                                                if(fileobj.isUploaded() && parseException == null)
                                                {
                                                    ParseObject PO = new ParseObject("Images");
                                                    PO.put("ImageName", individual.getName());
                                                    PO.put("Image", fileobj);
                                                    PO.put("SuppliersPointer", obj);
                                                    PO.saveInBackground(new SaveCallback() 
                                                    {
                                                        @Override
                                                        public void done(ParseException parseException) 
                                                        {
                                                            if(parseException == null)
                                                            {
                                                                iterations++;
                                                            }
                                                        }
                                                    });
                                                }
                                                else
                                                {
                                                        JOptionPane.showMessageDialog(null, "Error uploading files");
                                                        result = "Failed";
                                                        terminate();
                                                }
                                            }
                                        });
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
        tags.add(suppliersEntity.getRepresentative());
        tags.add(suppliersEntity.getPosition());
        tags.add(suppliersEntity.getCompany_Name());
        tags.add(suppliersEntity.getBrand());
        tags.add(suppliersEntity.getIndustry());
        tags.add(suppliersEntity.getType());
        String[] representativeSplit = suppliersEntity.getRepresentative().split("\\s+");
        String[] positionSplit = suppliersEntity.getPosition().split("\\s+");
        String[] companySplit = suppliersEntity.getCompany_Name().split("\\s+");
        String[] brandSplit = suppliersEntity.getBrand().split("\\s+");
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
        for(String values : brandSplit)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
}
