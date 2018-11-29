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
    private static GetOtherControllerAttributesSingleton instance = new GetOtherControllerAttributesSingleton();
    public static GetOtherControllerAttributesSingleton getInstance()
    {
        return instance;
    }
    
    private HashMap<String, Object> fields = new HashMap<>();
    private AnchorPane clientContainer, supplierContainer, contractorsContainer
            ,specificationsContainer,searchrecordsContainer, preview_container;
    ////////////////////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////
    public void supplierSetFields(HashMap<String, Object> fields)
    {
        this.fields = fields;
    }
    public HashMap<String, Object> supplierGetFields()
    {
        return this.fields;
    }
    public void supplierSetContainer(AnchorPane container)
    {
        this.supplierContainer = container;
    }
    public AnchorPane supplierGetContainer()
    {
        return this.supplierContainer;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void contractorsSetFields(HashMap<String, Object> fields)
    {
        this.fields = fields;
    }
    public HashMap<String, Object> contractorsGetFields()
    {
        return this.fields;
    }
    public void contractorsSetContainer(AnchorPane container)
    {
        this.contractorsContainer = container;
    }
    public AnchorPane contractorsGetContainer()
    {
        return this.contractorsContainer;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void specificationsSetFields(HashMap<String, Object> fields)
    {
        this.fields = fields;
    }
    public HashMap<String, Object> specificationsGetFields()
    {
        return this.fields;
    }
    public void specificationsSetContainer(AnchorPane container)
    {
        this.specificationsContainer = container;
    }
    public AnchorPane specificationsGetContainer()
    {
        return this.specificationsContainer;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void searchrecordsSetFields(HashMap<String, Object> fields)
    {
        this.fields = fields;
    }
    public HashMap<String, Object> searchrecordsGetFields()
    {
        return this.fields;
    }
    public void searchrecordsSetContainer(AnchorPane container)
    {
        this.searchrecordsContainer = container;
    }
    public AnchorPane searchrecordsGetContainer()
    {
        return this.searchrecordsContainer;
    }
    ////////////////////////////////////////////////////////////////////////////
    public AnchorPane previewGetContainer()
    {
        return preview_container;
    }
    public void previewSetContainer(AnchorPane container)
    {
        this.preview_container = container;
    }
    ////////////////////////////////////////////////////////////////////////////
}
