package MiscellaneousClasses;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    
    public DatabaseQuery(){}
    
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
    
    public ArrayList<ClientEntity> RetrieveImages(String searchData, String categoryClass)
    {
        
        final ArrayList<ClientEntity> cliententityList = new ArrayList<>();
        
        try
        {
            final ParseQuery<ParseObject> client = ParseQuery.getQuery(categoryClass);
            /*List<String> parameters = new ArrayList<>();
            parameters.add("Joey");
            parameters.add("Ese");
            client.whereContainedIn("Representative", Arrays.asList(parameters));*/
            client.whereContains("Representative", searchData);
            List<ParseObject> list = client.find();
            if(list != null)
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
            }
            else
            {
                System.out.println("Something went wrong");
            }
            /*client.findInBackground(new FindCallback<ParseObject>() 
            {
                @Override
                public void done(List<ParseObject> list, ParseException parseException) 
                {
                    if(list != null)
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
                    }
                    else
                    {
                        System.out.println("Something went wrong");
                    }
                }
            });*/
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

        return cliententityList;
    }
    
    public void RetrieveAssociatedFiles(String id, final ListView files)
    {
        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Client");
        query.whereEqualTo("objectId", id);
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(parseException == null && list != null)
                {
                    ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Images");
                    query2.include("ClientPointer");
                    query2.whereMatchesQuery("ClientPointer", query);
                    query2.findInBackground(new FindCallback<ParseObject>() 
                    {
                        @Override
                        public void done(List<ParseObject> list, ParseException parseException) 
                        {
                            if(parseException == null && list != null)
                            {
                                for(ParseObject mylist : list)
                                {
                                    ParseFile file = mylist.getParseFile("Image");
                                    System.out.println(file.getName());
                                    files.getItems().add(file.getName());
                                }
                            }
                            else
                            {
                                files.getItems().add("Something went wrong");
                            }
                        }
                    });
                }
            }
        });
    }
}
