package DatabaseOperations;

import Entities.ComboboxDataEntity;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.SaveCallback;

/**
 *
 * @author Joey Francisco
 */
public class DatabaseQuery 
{
    
    public DatabaseQuery(){}
    
    public ArrayList<ComboboxDataEntity> RetrieveComboboxData(String category) throws Exception
    {
        
        //final ObservableList<String> list = FXCollections.observableArrayList();
        final ArrayList<ComboboxDataEntity> list = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ComboboxData");
        query.whereEqualTo("Category", category);
        query.whereEqualTo("Field", "Industry");
        query.whereEqualTo("Field","Type");
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
                        list.add(cde);
                    }
                     System.out.println("Finished here");
                }
               
            }
        });
        
        return list;
    }
    
    public ObservableList<String> RetrieveComboboxDataCategories()
    {
        final ObservableList<String> returnlist = FXCollections.observableArrayList();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ComboboxData");
        List<String> ids = new ArrayList<String>();
        ids.add("gfp6fCQFVZ");
        ids.add("WUdSseHbuq");
        ids.add("FACbGp156f");
        ids.add("cfhgK7i4Wd");
        query.whereContainedIn("objectId", ids);
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    for(ParseObject lists : list)
                    {
                        returnlist.add(lists.getString("Category"));
                    }
                }
            }
        });
        
        return returnlist;
    } 
    
    
}
