package DatabaseOperations;

import Entities.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

public class RetrieveValuesContractors extends Thread
{
    volatile boolean running = true;
    volatile int iterations = 0;
    private String searchData;
    private String searchClass;
    final ArrayList<ContractorsEntity> contractorsEntitys = new ArrayList<>();
    public RetrieveValuesContractors(String searchData, String searchClass)
    {
        this.searchData = searchData;
        this.searchClass = searchClass;
    }
    public ArrayList<ContractorsEntity> getResult()
    {
        return contractorsEntitys;
    }
    public void terminate()
    {
        running = false;
    }
    
    @Override
    public void run()
    {
        while(running)
        {
            try
            {
                final ParseQuery<ParseObject> contractors = ParseQuery.getQuery(searchClass);
                ArrayList<String> parameters = new ArrayList<String>();
                String[] getValues = searchData.toUpperCase().split(",");
                for(String values : getValues)
                {
                    parameters.add(values);
                }
                contractors.whereContainedIn("Tags", Arrays.asList(parameters));
                if(iterations <= 0)
                {
                    contractors.findInBackground(new FindCallback<ParseObject>() 
                    {
                        @Override
                        public void done(List<ParseObject> list, ParseException parseException) 
                        {
                            if(list != null)
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
                                terminate();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "No records found");
                                terminate();
                            }
                        }
                    });
                    iterations++;
                }
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
