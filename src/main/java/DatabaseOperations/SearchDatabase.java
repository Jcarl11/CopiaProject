package DatabaseOperations;

import Entities.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

public class SearchDatabase
{
    ParseQuery<ParseObject> query;
    private boolean finished;
    private String searchClass;
    private String searchKeyword;
    private SearchDatabase(){}
    private static SearchDatabase instance = null;
    public static SearchDatabase getInstance()
    {
        if(instance == null)
            instance = new SearchDatabase();
        return instance;
    }
    public ArrayList<ClientEntity> searchClient()
    {
        setFinished(false);
        final ArrayList<ClientEntity> cliententityList = new ArrayList<>();
        query = ParseQuery.getQuery(getSearchClass());
        ArrayList<String> parameters = new ArrayList<String>();
        String[] getValues = getSearchKeyword().toUpperCase().split(",");
        for(String values : getValues)
        {
            parameters.add(values);
        }
        query.whereContainedIn("Tags", Arrays.asList(parameters));
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    for(ParseObject po : list)
                    {
                        ClientEntity cliententity = new ClientEntity();
                        cliententity.setObjectID(po.getObjectId());
                        cliententity.setRepresentative(po.getString("Representative"));
                        cliententity.setPosition(po.getString("Position"));
                        cliententity.setCompany_Name(po.getString("Company"));
                        cliententity.setIndustry(po.getString("Industry"));
                        cliententity.setType(po.getString("Type"));
                        cliententityList.add(cliententity);
                    } 
                    
                    setFinished(true);
                }
                else
                {
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return cliententityList;
    }
    
    public ArrayList<SuppliersEntity> searchSuppliers()
    {
        setFinished(false);
        final ArrayList<SuppliersEntity> suppliersEntitys = new ArrayList<>();
        query = ParseQuery.getQuery(getSearchClass());
        ArrayList<String> parameters = new ArrayList<String>();
        String[] getValues = getSearchKeyword().toUpperCase().split(",");
        for(String values : getValues)
        {
            parameters.add(values);
        }
        query.whereContainedIn("Tags", Arrays.asList(parameters));
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    for(ParseObject po : list)
                    {
                        SuppliersEntity suppliersEntity = new SuppliersEntity();
                        suppliersEntity.setObjectID(po.getObjectId());
                        suppliersEntity.setRepresentative(po.getString("Representative"));
                        suppliersEntity.setPosition(po.getString("Position"));
                        suppliersEntity.setCompany_Name(po.getString("Company_Name"));
                        suppliersEntity.setBrand(po.getString("Brand"));
                        suppliersEntity.setIndustry(po.getString("Industry"));
                        suppliersEntity.setType(po.getString("Type"));
                        suppliersEntitys.add(suppliersEntity);
                    } 
                    
                    setFinished(true);
                }
                else
                {
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return suppliersEntitys;
    }
    
    public ArrayList<ContractorsEntity> searchContractors()
    {
        setFinished(false);
        final ArrayList<ContractorsEntity> contractorsEntitys = new ArrayList<>();
        query = ParseQuery.getQuery(getSearchClass());
        ArrayList<String> parameters = new ArrayList<String>();
        String[] getValues = getSearchKeyword().toUpperCase().split(",");
        for(String values : getValues)
        {
            parameters.add(values);
        }
        query.whereContainedIn("Tags", Arrays.asList(parameters));
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    for(ParseObject po : list)
                    {
                        ContractorsEntity contractorsEntity = new ContractorsEntity();
                        contractorsEntity.setObjectId(po.getObjectId());
                        contractorsEntity.setRepresentative(po.getString("Representative"));
                        contractorsEntity.setPosition(po.getString("Position"));
                        contractorsEntity.setCompanyName(po.getString("Company"));
                        contractorsEntity.setSpecialization(po.getString("Specialization"));
                        contractorsEntity.setIndustry(po.getString("Industry"));
                        contractorsEntity.setClassification(po.getString("Classification"));
                        contractorsEntitys.add(contractorsEntity);
                    } 
                    
                    setFinished(true);
                }
                else
                {
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return contractorsEntitys;
    }
    
    public ArrayList<ConsultantsEntity> searchConsultants()
    {
        setFinished(false);
        final ArrayList<ConsultantsEntity> consultantsEntitys = new ArrayList<>();
        query = ParseQuery.getQuery(getSearchClass());
        ArrayList<String> parameters = new ArrayList<String>();
        String[] getValues = getSearchKeyword().toUpperCase().split(",");
        for(String values : getValues)
        {
            parameters.add(values);
        }
        query.whereContainedIn("Tags", Arrays.asList(parameters));
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    for(ParseObject po : list)
                    {
                        ConsultantsEntity consultantsEntity = new ConsultantsEntity();
                        consultantsEntity.setObjectId(po.getObjectId());
                        consultantsEntity.setRepresentative(po.getString("Representative"));
                        consultantsEntity.setPosition(po.getString("Position"));
                        consultantsEntity.setCompanyName(po.getString("Company"));
                        consultantsEntity.setSpecialization(po.getString("Specialization"));
                        consultantsEntity.setIndustry(po.getString("Industry"));
                        consultantsEntity.setClassification(po.getString("Classification"));
                        consultantsEntitys.add(consultantsEntity);
                    } 
                    
                    setFinished(true);
                }
                else
                {
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return consultantsEntitys;
    }
    
    public ArrayList<SpecificationsEntity> searchSpecifications()
    {
        setFinished(false);
        final ArrayList<SpecificationsEntity> specificationsEntitys = new ArrayList<>();
        query = ParseQuery.getQuery(getSearchClass());
        ArrayList<String> parameters = new ArrayList<String>();
        String[] getValues = getSearchKeyword().toUpperCase().split(",");
        for(String values : getValues)
        {
            parameters.add(values);
        }
        query.whereContainedIn("Tags", Arrays.asList(parameters));
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    for(ParseObject po : list)
                    {
                        SpecificationsEntity specificationsEntity = new SpecificationsEntity();
                        specificationsEntity.setObjectId(po.getObjectId());
                        specificationsEntity.setTitle(po.getString("Title"));
                        specificationsEntity.setDivision(po.getString("Division"));
                        specificationsEntity.setSection(po.getString("Section"));
                        specificationsEntity.setType(po.getString("Type"));
                        specificationsEntitys.add(specificationsEntity);
                    } 
                    
                    setFinished(true);
                }
                else
                {
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(SearchDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return specificationsEntitys;
    }
    public String getSearchClass() {
        return searchClass;
    }

    public void setSearchClass(String searchClass) {
        this.searchClass = searchClass;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
