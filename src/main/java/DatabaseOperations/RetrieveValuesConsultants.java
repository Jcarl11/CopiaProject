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

public class RetrieveValuesConsultants extends Thread
{
    volatile boolean running = true;
    volatile int iterations = 0;
    private String searchData;
    private String searchClass;
    final ArrayList<ConsultantsEntity> consultantsEntitys = new ArrayList<>();
    public RetrieveValuesConsultants(String searchData, String searchClass)
    {
        this.searchData = searchData;
        this.searchClass = searchClass;
    }
    public ArrayList<ConsultantsEntity> getResult()
    {
        return consultantsEntitys;
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
                final ParseQuery<ParseObject> consultants = ParseQuery.getQuery(searchClass);
                ArrayList<String> parameters = new ArrayList<String>();
                String[] getValues = searchData.toUpperCase().split(",");
                for(String values : getValues)
                {
                    parameters.add(values);
                }
                consultants.whereContainedIn("Tags", Arrays.asList(parameters));
                if(iterations <= 0)
                {
                    consultants.findInBackground(new FindCallback<ParseObject>() 
                    {
                        @Override
                        public void done(List<ParseObject> list, ParseException parseException) 
                        {
                            if(list != null)
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
