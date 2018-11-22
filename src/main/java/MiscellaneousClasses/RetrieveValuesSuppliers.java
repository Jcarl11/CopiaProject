package MiscellaneousClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

/**
 *
 * @author Joey Francisco
 */
public class RetrieveValuesSuppliers extends Thread
{
    volatile boolean running = true;
    volatile int iterations = 0;
    private String searchData;
    private String searchClass;
    final ArrayList<SuppliersEntity> suppliersEntity = new ArrayList<>();
    public RetrieveValuesSuppliers(String searchData, String searchClass)
    {
        this.searchData = searchData;
        this.searchClass = searchClass;
    }
    public ArrayList<SuppliersEntity> getResult()
    {
        return suppliersEntity;
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
                final ParseQuery<ParseObject> suppliers = ParseQuery.getQuery(searchClass);
                ArrayList<String> parameters = new ArrayList<String>();
                String[] getValues = searchData.toUpperCase().split(",");
                for(String values : getValues)
                {
                    parameters.add(values);
                }
                suppliers.whereContainedIn("Tags", Arrays.asList(parameters));
                if(iterations <= 0)
                {
                    suppliers.findInBackground(new FindCallback<ParseObject>() 
                    {
                        @Override
                        public void done(List<ParseObject> list, ParseException parseException) 
                        {
                            if(list != null)
                            {
                                for(ParseObject po : list)
                                {
                                    SuppliersEntity suppliersentity = new SuppliersEntity();
                                    suppliersentity.setObjectID(po.getObjectId());
                                    suppliersentity.setRepresentative(po.getString("Representative"));
                                    suppliersentity.setPosition(po.getString("Position"));
                                    suppliersentity.setCompany_Name(po.getString("Company"));
                                    suppliersentity.setBrand(po.getString("Brand"));
                                    suppliersentity.setIndustry(po.getString("Industry"));
                                    suppliersentity.setType(po.getString("Type"));
                                    suppliersEntity.add(suppliersentity);
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
