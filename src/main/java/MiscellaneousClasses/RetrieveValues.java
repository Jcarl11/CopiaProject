/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MiscellaneousClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.parse4j.*;
import org.parse4j.callback.FindCallback;

/**
 *
 * @author Windows
 */
public class RetrieveValues extends Thread
{
    volatile boolean running = true;
    volatile int iterations = 0;
    private String searchData;
    private String searchClass;
    final ArrayList<ClientEntity> cliententityList = new ArrayList<>();
    public RetrieveValues(String searchData, String searchClass)
    {
        this.searchData = searchData;
        this.searchClass = searchClass;
    }
    public ArrayList<ClientEntity> getResult()
    {
        return cliententityList;
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
                final ParseQuery<ParseObject> client = ParseQuery.getQuery(searchClass);
           
                ArrayList<String> parameters = new ArrayList<String>();
                parameters.add("Juan");
                parameters.add("Janitor");
                parameters.add("Juan Dela Cruz");
                client.whereContainedIn("Tags", Arrays.asList(parameters));
                if(iterations <= 0)
                {
                    client.findInBackground(new FindCallback<ParseObject>() 
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
                                terminate();
                            }
                            else
                            {
                                System.out.println("Something went wrong");
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
