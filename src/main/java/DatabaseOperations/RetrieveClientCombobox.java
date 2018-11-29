package DatabaseOperations;

import Entities.ComboboxDataEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

public class RetrieveClientCombobox extends Thread 
{
    String category;
    volatile boolean running = true;
    volatile int iterations = 0;
    final ArrayList<ComboboxDataEntity> comboboxEntity = new ArrayList<>();
    
    public RetrieveClientCombobox(String category)
    {
        this.category = category;
    }
    
    public void terminate()
    {
        running = false;
    }
    
    public ArrayList<ComboboxDataEntity> getResult()
    {
        return comboboxEntity;
    }
    
    
    @Override
    public void run() 
    {
        while(running)
        {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("ComboboxData");
            query.whereEqualTo("Category", category);
            String[] data = {"Industry","Type"};
            query.whereContainedIn("Field", Arrays.asList(data));
            if(iterations <= 0)
            {
                query.findInBackground(new FindCallback<ParseObject>() 
                {
                    @Override
                    public void done(List<ParseObject> arg0, ParseException arg1) 
                    {
                        if(arg1 == null && arg0 != null)
                        {
                            for(ParseObject result: arg0)
                            {
                                ComboboxDataEntity cde = new ComboboxDataEntity();
                                cde.setObjectId(result.getObjectId());
                                cde.setTitle(result.getString("Title"));
                                cde.setCategory(result.getString("Category"));
                                cde.setField(result.getString("Field"));
                                comboboxEntity.add(cde);
                            }
                            terminate();
                        }
                        else
                            terminate();
                            
                    }
                });
                iterations++;
            }
        }
    }
    
}
