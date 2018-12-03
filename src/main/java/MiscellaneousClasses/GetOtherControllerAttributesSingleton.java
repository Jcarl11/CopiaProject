package MiscellaneousClasses;

import java.util.HashMap;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
        {
            instance = new GetOtherControllerAttributesSingleton();
        }
        return instance;
    }
    
    private HashMap<String, Object> fields = new HashMap<>();
    private HashMap<String, TextField> clientTextFieldList = new HashMap<>();
    private HashMap<String, ComboBox> clientComboboxList = new HashMap<>();
    private HashMap<String, ListView> clientListView = new HashMap<>();
    
    private HashMap<String, TextField> suppliersTextFieldList = new HashMap<>();
    private HashMap<String, ComboBox> suppliersComboboxList = new HashMap<>();
    private HashMap<String, ListView> suppliersListView = new HashMap<>();
    private AnchorPane clientContainer, supplierContainer, contractorsContainer
            ,specificationsContainer,searchrecordsContainer, preview_container;
    ////////////////////////////////////////////////////////////////////////////
    public void clientSetTextFields(HashMap<String, TextField> fields)
    {
        this.clientTextFieldList = fields;
    }
    public void clientSetComboBox(HashMap<String, ComboBox> fields)
    {
        this.clientComboboxList = fields;
    }
    public void clientSetListView(HashMap<String, ListView> fields)
    {
        this.clientListView = fields;
    }
    public HashMap<String, TextField> clientGetTextFields()
    {
        return this.clientTextFieldList;
    }
    public HashMap<String, ComboBox> clientGetComboBox()
    {
        return this.clientComboboxList;
    }
     public HashMap<String, ListView> clientGetListView()
    {
        return this.clientListView;
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
    public void supplierSetTextFields(HashMap<String, TextField> fields)
    {
        this.suppliersTextFieldList = fields;
    }
    public void supplierSetCombobox(HashMap<String, ComboBox> combobox)
    {
        this.suppliersComboboxList = combobox;
    }
    public void supplierSetListView(HashMap<String, ListView> listview)
    {
        this.suppliersListView = listview;
    }
    public HashMap<String, TextField> supplierGetTextFields()
    {
        return this.suppliersTextFieldList;
    }
    public HashMap<String, ComboBox> supplierGetCombobox()
    {
        return this.suppliersComboboxList;
    }
    public HashMap<String, ListView> supplierGetListView()
    {
        return this.suppliersListView;
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
