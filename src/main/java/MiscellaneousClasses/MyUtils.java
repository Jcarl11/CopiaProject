
package MiscellaneousClasses;

import Entities.*;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;

public class MyUtils 
{
    private MyUtils(){}
    private static MyUtils instance = null;
    public static MyUtils getInstance()
    {
        if(instance == null)
            instance = new MyUtils();
        return instance;
    }
    public static String APP_ID = "4GCD5XK7GucFbTKnJa0fonFEBlAh3azBS3Gh0NNd";
    public static String REST_API_KEY = "RYznH1yrJ3DVly2f02aEMkZJNwmPVdDBUQyqRT6H";
    public static String URL_FILE = "https://concipiotektura.back4app.io/files/";
    public static String URL = "https://concipiotektura.back4app.io/classes/";
    public ArrayList<String> client_extractStringsToTags(ClientEntity clientEntity)
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
    
    public ArrayList<String> suppliers_extractStringsToTags(SuppliersEntity suppliersEntity)
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
        String[] brand = suppliersEntity.getBrand().split("\\s+");
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
        for(String values : brand)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
    
    public ArrayList<String> contractors_extractStringsToTags(ContractorsEntity contractorsEntity)
    {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(contractorsEntity.getRepresentative());
        tags.add(contractorsEntity.getPosition());
        tags.add(contractorsEntity.getCompanyName());
        tags.add(contractorsEntity.getSpecialization());
        tags.add(contractorsEntity.getIndustry());
        tags.add(contractorsEntity.getClassification());
        String[] representativeSplit = contractorsEntity.getRepresentative().split("\\s+");
        String[] positionSplit = contractorsEntity.getPosition().split("\\s+");
        String[] companySplit = contractorsEntity.getCompanyName().split("\\s+");
        String[] specialization = contractorsEntity.getSpecialization().split("\\s+");
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
        for(String values : specialization)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
    public ArrayList<String> consultants_extractStringsToTags(ConsultantsEntity consultantsEntity)
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
        String[] specialization = consultantsEntity.getSpecialization().split("\\s+");
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
        for(String values : specialization)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
    
    public ArrayList<String> speicifications_extractStringsToTags(SpecificationsEntity specificationsEntity)
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
    public String getFileType(String filePath)
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
