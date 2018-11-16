package MiscellaneousClasses;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

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
    
    /*public String SendPostData(ClientEntity client, String path, String method)
    {
        String client_objectID = null;
        try
        {
            
            this.url = new URL(path);
            connection = (HttpURLConnection) this.url.openConnection(); 
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("X-Parse-Application-Id", APP_ID);
            connection.setRequestProperty("X-Parse-REST-API-Key", REST_API_KEY);
            connection.setRequestProperty("Content-Type", "applciation/json");
            HashMap<String, String> data = new HashMap<>();
            data.put("Representative", client.getRepresentative());
            data.put("Position", client.getPosition());
            data.put("Company", client.getCompany_Name());
            data.put("Industry", client.getIndustry());
            data.put("Type", client.getType());
            JSONObject insert = new JSONObject(data);
            
            byte[] byteData = insert.toString().getBytes("UTF-8");
            OutputStream os = connection.getOutputStream();
            os.write(byteData);
            os.close();
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            String inputline;
            while((inputline = bufferedReader.readLine()) != null)
            {
                response.append(inputline);
            }
            JSONObject response_json = new JSONObject(response.toString());
            client_objectID = response_json.getString("objectId");
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return client_objectID;
    }*/
    
}
