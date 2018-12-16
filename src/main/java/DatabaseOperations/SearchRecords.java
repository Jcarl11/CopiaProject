package DatabaseOperations;
import javafx.concurrent.Task;
public class SearchRecords 
{
    Task<Object> searchTask;
    private SearchRecords(){}
    private static SearchRecords instance = null;
    public static SearchRecords getInstance()
    {
        if(instance == null)
            instance = new SearchRecords();
        return instance;
    }
    
    public void Search(final String keywords, final String searchIn)
    {
        searchTask = new Task<Object>() 
        {
            @Override
            protected Object call() throws Exception 
            {
                Object object = new Object();
                SearchDatabase.getInstance().setSearchClass(searchIn);
                SearchDatabase.getInstance().setSearchKeyword(keywords);
                if(searchIn.equalsIgnoreCase("client"))
                    object = SearchDatabase.getInstance().searchClient();
                else if(searchIn.equalsIgnoreCase("suppliers"))
                    object = SearchDatabase.getInstance().searchSuppliers();
                else if(searchIn.equalsIgnoreCase("contractors"))
                    object = SearchDatabase.getInstance().searchContractors();
                else if(searchIn.equalsIgnoreCase("consultants"))
                    object = SearchDatabase.getInstance().searchConsultants();
                else if(searchIn.equalsIgnoreCase("specifications"))
                    object = SearchDatabase.getInstance().searchSpecifications();
                return object;
            }
        };
        new Thread(searchTask).start();
    }
    
    public Task<Object> getTask()
    {
        return searchTask;
    }
}
