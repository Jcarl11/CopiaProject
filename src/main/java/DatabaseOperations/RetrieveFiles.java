package DatabaseOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;

public class RetrieveFiles 
{
    private boolean finished;
    private RetrieveFiles(){}
    private static RetrieveFiles instance = null;
    public static RetrieveFiles getInstance()
    {
        if(instance == null)
            instance = new RetrieveFiles();
        return instance;
    }
    
    public ArrayList<String> retrieve(String id, String searchClass, final String field, final String imageClass, final String pdfClass)
    {
        setFinished(false);
        final ArrayList<String> response = new ArrayList<>();
        final ParseQuery<ParseObject> query = ParseQuery.getQuery(searchClass);
        query.whereEqualTo("objectId", id);
        query.findInBackground(new FindCallback<ParseObject>() 
        {
            @Override
            public void done(List<ParseObject> list, ParseException parseException) 
            {
                if(list != null && parseException == null)
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
                                    response.add(filename);
                                }
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
                                                response.add(filename);
                                                setFinished(true);
                                            }
                                        }
                                        else
                                        {
                                            setFinished(true);
                                        }
                                    }
                                });

                        }
                    });
                }
                else
                {
                    response.add("No records found");
                    setFinished(true);
                }
            }
        });
        while(isFinished() == false)
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(RetrieveFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return response;
    }
    
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
