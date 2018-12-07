package DatabaseOperations;

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
    String searchClass;
    String field;
    String imageClass;
    String pdfClass;
    public RetrieveAssociatedFiles(String id, String searchClass, String field, String imageClass, String pdfClass)
    {
        this.id = id;
        this.searchClass = searchClass;
        this.field= field;
        this.imageClass = imageClass;
        this.pdfClass = pdfClass;
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
            final ParseQuery<ParseObject> query = ParseQuery.getQuery(searchClass);
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
                            if(searchClass.equalsIgnoreCase("Specifications"))
                            {
                                ParseQuery<ParseObject> query3 = ParseQuery.getQuery(pdfClass);
                                        query3.include(field);
                                        query3.whereMatchesQuery(field, query);
                                        query3.findInBackground(new FindCallback<ParseObject>() 
                                        {
                                            @Override
                                            public void done(List<ParseObject> list, ParseException parseException) 
                                            {
                                                if(parseException == null && list != null)
                                                {
                                                    for(ParseObject mylist : list)
                                                    {
                                                        String filename = mylist.getString("Name");
                                                        fileNames.add(filename);
                                                    }
                                                    
                                                }
                                                terminate();
                                            }
                                        });
                            }
                            else
                            {
                               
                                ParseQuery<ParseObject> query2 = ParseQuery.getQuery(imageClass);
                                query2.include(field);
                                query2.whereMatchesQuery(field, query);
                                query2.findInBackground(new FindCallback<ParseObject>() 
                                {
                                    @Override
                                    public void done(List<ParseObject> list, ParseException parseException) 
                                    {
                                        if(parseException == null && list != null)
                                        {
                                            for(ParseObject mylist : list)
                                            {
                                                String filename = mylist.getString("Name");
                                                fileNames.add(filename);
                                            }
                                            ParseQuery<ParseObject> query3 = ParseQuery.getQuery(pdfClass);
                                            query3.include(field);
                                            query3.whereMatchesQuery(field, query);
                                            query3.findInBackground(new FindCallback<ParseObject>() 
                                            {
                                                @Override
                                                public void done(List<ParseObject> list, ParseException parseException) 
                                                {
                                                    if(parseException == null && list != null)
                                                    {
                                                        for(ParseObject mylist : list)
                                                        {
                                                            String filename = mylist.getString("Name");
                                                            fileNames.add(filename);
                                                        }

                                                    }
                                                    terminate();
                                                }
                                            });

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
                    }
                });
                iterations++;
            }
        }
    }
    
}
