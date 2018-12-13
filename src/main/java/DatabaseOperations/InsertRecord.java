package DatabaseOperations;

import Entities.*;
import MiscellaneousClasses.MyUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.SaveCallback;

public class InsertRecord 
{
    ParseObject parseObject;
    boolean isFinished;
    public InsertRecord()
    {
   
    }
    
    public ParseObject clientInsert(String searchClass, ClientEntity clientEntity)
    {
        setIsFinished(false);
        parseObject = new ParseObject(searchClass);
        parseObject.put("Representative", clientEntity.getRepresentative());
        parseObject.put("Position", clientEntity.getPosition());
        parseObject.put("Company", clientEntity.getCompany_Name());
        parseObject.put("Industry", clientEntity.getIndustry());
        parseObject.put("Type", clientEntity.getType());
        parseObject.put("Tags", new JSONArray(MyUtils.getInstance().client_extractStringsToTags(clientEntity)));
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
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(InsertRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parseObject;
    }
    
    public ParseObject suppliersInsert(String searchClass, SuppliersEntity suppliersEntity)
    {
        setIsFinished(false);
        parseObject = new ParseObject(searchClass);
        parseObject.put("Representative", suppliersEntity.getRepresentative());
        parseObject.put("Position", suppliersEntity.getPosition());
        parseObject.put("Company_Name", suppliersEntity.getCompany_Name());
        parseObject.put("Brand", suppliersEntity.getBrand());
        parseObject.put("Industry", suppliersEntity.getIndustry());
        parseObject.put("Type", suppliersEntity.getType());
        parseObject.put("Tags", new JSONArray(MyUtils.getInstance().suppliers_extractStringsToTags(suppliersEntity)));
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
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(InsertRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parseObject;
    }
    public ParseObject contractorsInsert(String searchClass, ContractorsEntity contractorsEntity)
    {
        setIsFinished(false);
        parseObject = new ParseObject(searchClass);
        parseObject.put("Representative", contractorsEntity.getRepresentative());
        parseObject.put("Position", contractorsEntity.getPosition());
        parseObject.put("Company", contractorsEntity.getCompanyName());
        parseObject.put("Specialization", contractorsEntity.getSpecialization());
        parseObject.put("Industry", contractorsEntity.getIndustry());
        parseObject.put("Classification", contractorsEntity.getClassification());
        parseObject.put("Tags", new JSONArray(MyUtils.getInstance().contractors_extractStringsToTags(contractorsEntity)));
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
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(InsertRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parseObject;
    }
    
    public ParseObject consultantsInsert(String searchClass, ConsultantsEntity consultantsEntity)
    {
        setIsFinished(false);
        parseObject = new ParseObject(searchClass);
        parseObject.put("Representative", consultantsEntity.getRepresentative());
        parseObject.put("Position", consultantsEntity.getPosition());
        parseObject.put("Company", consultantsEntity.getCompanyName());
        parseObject.put("Specialization", consultantsEntity.getSpecialization());
        parseObject.put("Industry", consultantsEntity.getIndustry());
        parseObject.put("Classification", consultantsEntity.getClassification());
        parseObject.put("Tags", new JSONArray(MyUtils.getInstance().consultants_extractStringsToTags(consultantsEntity)));
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
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(InsertRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parseObject;
    }
    public ParseObject specificationsInsert(String searchClass, SpecificationsEntity specificationsEntity)
    {
        setIsFinished(false);
        parseObject = new ParseObject(searchClass);
        parseObject.put("Title", specificationsEntity.getTitle());
        parseObject.put("Division", specificationsEntity.getDivision());
        parseObject.put("Section", specificationsEntity.getSection());
        parseObject.put("Type", specificationsEntity.getType());
        parseObject.put("Tags", new JSONArray(MyUtils.getInstance().speicifications_extractStringsToTags(specificationsEntity)));
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
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(InsertRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return parseObject;
    }
    private boolean getIsFinished() {
        return isFinished;
    }

    private void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }
}
