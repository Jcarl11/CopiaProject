package MiscellaneousClasses;

import java.util.HashMap;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Joey Francisco
 */
public class GetOtherControllerAttributesSingleton 
{
    private GetOtherControllerAttributesSingleton(){}
    private static GetOtherControllerAttributesSingleton instance = null;
    public static GetOtherControllerAttributesSingleton getInstance()
    {
        if(instance == null)
            instance = new GetOtherControllerAttributesSingleton();
        
        return instance;
    }
    
    private HashMap<String, Object> fields = new HashMap<>();
    private AnchorPane clientContainer;
    
    public void clientSetFields(HashMap<String, Object> fields)
    {
        this.fields = fields;
    }
    public HashMap<String, Object> clientGetFields()
    {
        return this.fields;
    }
    public void clientSetContainer(AnchorPane container)
    {
        this.clientContainer = container;
    }
    public AnchorPane clientGetContainer()
    {
        return this.clientContainer;
    }
}
