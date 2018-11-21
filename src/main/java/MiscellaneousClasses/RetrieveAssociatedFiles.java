package MiscellaneousClasses;

import java.util.ArrayList;
import java.util.List;
import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

/**
 *
 * @author Joey Francisco
 */
public class RetrieveAssociatedFiles extends Thread
{
    volatile boolean running = true;
    String id;
    volatile int iterations = 0;
    final ArrayList<String> fileNames = new ArrayList<String>();
    public RetrieveAssociatedFiles(String id)
    {
        this.id = id;
    }
    public void terminate()
    {
        running = false;
    }
    public ArrayList<String> getResult()
    {
        return fileNames;
    }
    @Override
    public void run() 
    {
        while(running)
        {
            final ParseQuery<ParseObject> query = ParseQuery.getQuery("Client");
            query.whereEqualTo("objectId", id);
            if(iterations <= 0)
            {
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
                                            //ParseFile file = mylist.getParseFile("Image");
                                            String filename = mylist.getString("ImageName");
                                            fileNames.add(filename);
                                        }
                                        terminate();
                                    }
                                    else
                                    {
                                        fileNames.add("No records found");
                                        terminate();
                                    }
                                    
                                }
                            });
                        }
                    }
                });
                iterations++;
            }
        }
    }
    
}
