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

public class RetrieveValuesSpecifications extends Thread
{
    volatile boolean running = true;
    volatile int iterations = 0;
    private String searchData;
    private String searchClass;
    final ArrayList<SpecificationsEntity> specificationsEntitys = new ArrayList<>();
    public RetrieveValuesSpecifications(String searchData, String searchClass)
    {
        this.searchData = searchData;
        this.searchClass = searchClass;
    }
    public ArrayList<SpecificationsEntity> getResult()
    {
        return specificationsEntitys;
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
                final ParseQuery<ParseObject> specifications = ParseQuery.getQuery(searchClass);
                ArrayList<String> parameters = new ArrayList<String>();
                String[] getValues = searchData.toUpperCase().split(",");
                for(String values : getValues)
                {
                    parameters.add(values);
                }
                specifications.whereContainedIn("Tags", Arrays.asList(parameters));
                if(iterations <= 0)
                {
                    specifications.findInBackground(new FindCallback<ParseObject>() 
                    {
                        @Override
                        public void done(List<ParseObject> list, ParseException parseException) 
                        {
                            if(list != null)
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
