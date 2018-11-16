package MiscellaneousClasses;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
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
    public DatabaseQuery() 
    {
          
    }
    
    public ObservableList<String> RetrieveComboboxData(String category, String field) throws Exception
    {
        final ObservableList<String> list = FXCollections.observableArrayList();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ComboboxData");
        query.whereEqualTo("Category", category);
        query.whereEqualTo("Field", field);
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> arg0, ParseException arg1) 
            {
                for(ParseObject result: arg0)
                {
                    list.add(result.getString("Title"));
                }
            }
        });
        
        return list;
    }
    
    public void SendPostData(final ClientEntity client, String dataClass)
    {
        final ParseObject obj = new ParseObject(dataClass);
        obj.put("Representative", client.getRepresentative());
        obj.put("Position", client.getPosition());
        obj.put("Company", client.getCompany_Name());
        obj.put("Industry", client.getIndustry());
        obj.put("Type", client.getType());
        obj.saveInBackground(new SaveCallback() 
        {
            @Override
            public void done(ParseException parseException) 
            {
                if(parseException == null)
                {
                    try
                    {
                        for(File individual : client.getFileToUpload() )
                        {
                            final ParseFile fileobj = new ParseFile(individual.getName(), Files.readAllBytes(individual.toPath()), "image/jpeg");
                            fileobj.saveInBackground(new SaveCallback() 
                            {
                                @Override
                                public void done(ParseException parseException) 
                                {
                                    if(fileobj.isUploaded())
                                    {
                                        ParseObject PO = new ParseObject("Images");
                                        PO.put("ImageName", "MyImages");
                                        PO.put("Image", fileobj);
                                        PO.put("ClientPointer", obj);
                                        PO.saveInBackground(new SaveCallback() 
                                        {
                                            @Override
                                            public void done(ParseException parseException) 
                                            {
                                                if(parseException == null)
                                                  JOptionPane.showMessageDialog(null, "Data added");
                                                else
                                                  JOptionPane.showMessageDialog(null, "Something went wrong");
                                            }
                                        });
                                    }
                                }
                            });
                        }



                    }catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
    
}
