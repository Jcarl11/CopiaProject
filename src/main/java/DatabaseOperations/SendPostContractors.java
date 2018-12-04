/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseOperations;

import Entities.ContractorsEntity;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.SaveCallback;

/**
 *
 * @author Windows
 */
public class SendPostContractors extends Thread
{

    ContractorsEntity contractors;
    ParseObject obj;
    ArrayList<String> tags;
    String result = null;
    volatile boolean running = true;
    volatile int iterations = 0;
    
    public SendPostContractors(ContractorsEntity contractors, String dataClass)
    {
        this.contractors = contractors;
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
                obj.put("Representative", contractors.getRepresentative());
                System.out.println(contractors.getRepresentative());
                obj.put("Position", contractors.getPosition());
                System.out.println(contractors.getPosition());
                obj.put("Company", contractors.getCompanyName());
                System.out.println(contractors.getCompanyName());
                obj.put("Specialization", contractors.getSpecialization());
                System.out.println(contractors.getSpecialization());
                obj.put("Industry", contractors.getIndustry());
                obj.put("Classification", contractors.getClassification());
                obj.put("Tags", new JSONArray(extractStringsToTags()));
                obj.saveInBackground(new SaveCallback() 
                {
                    @Override
                    public void done(ParseException parseException) 
                    {
                        if(parseException == null)
                        {
                            if(contractors.getFileToUpload() != null)
                            {
                                try
                                {
                                    for(final File individual : contractors.getFileToUpload() )
                                    {
                                        FileUpload fileUpload = new FileUpload(individual, individual.getName(), obj.getObjectId(),"ContractorsPointer", "Contractors");
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
        tags.add(contractors.getRepresentative());
        tags.add(contractors.getPosition());
        tags.add(contractors.getCompanyName());
        tags.add(contractors.getSpecialization());
        tags.add(contractors.getIndustry());
        tags.add(contractors.getClassification());
        String[] representativeSplit = contractors.getRepresentative().split("\\s+");
        String[] positionSplit = contractors.getPosition().split("\\s+");
        String[] companySplit = contractors.getCompanyName().split("\\s+");
        String[] specializationSplit = contractors.getSpecialization().split("\\s+");
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
