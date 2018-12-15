package MiscellaneousClasses;

import java.util.HashMap;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
    
    private HashMap<String, TextField> clientTextFieldList = new HashMap<>();
    private HashMap<String, ComboBox> clientComboboxList = new HashMap<>();
    private HashMap<String, ListView> clientListView = new HashMap<>();
    private HashMap<String, TextArea> clientTextArea = new HashMap<>();
    
    private HashMap<String, TextField> suppliersTextFieldList = new HashMap<>();
    private HashMap<String, ComboBox> suppliersComboboxList = new HashMap<>();
    private HashMap<String, ListView> suppliersListView = new HashMap<>();
    private HashMap<String, TextArea> suppliersTextArea = new HashMap<>();
    
    private HashMap<String, TextField> contractorsTextFieldList = new HashMap<>();
    private HashMap<String, ComboBox> contractorsComboboxList = new HashMap<>();
    private HashMap<String, ListView> contractorsListView = new HashMap<>();
    private HashMap<String, TextArea> contractorsTextArea = new HashMap<>();
    
    private HashMap<String, TextField> consultantsTextFieldList = new HashMap<>();
    private HashMap<String, ComboBox> consultantsComboboxList = new HashMap<>();
    private HashMap<String, ListView> consultantsListView = new HashMap<>();
    private HashMap<String, TextArea> consultantsTextArea = new HashMap<>();
    
    private HashMap<String, TextField> specificationsTextFieldList = new HashMap<>();
    private HashMap<String, TextArea> specificationsTextArea = new HashMap<>();
    private HashMap<String, ListView> specificationsListView = new HashMap<>();
    private HashMap<String, TextArea> specificationsRemarks = new HashMap<>();
    
    private AnchorPane clientContainer, supplierContainer, contractorsContainer,
            consultantsContainer,specificationsContainer,searchrecordsContainer, preview_container;
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
    public void clientSetTextArea(HashMap<String, TextArea> fields)
    {
        this.clientTextArea = fields;
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
    public HashMap<String, TextArea> clientGetTextArea()
    {
        return this.clientTextArea;
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
    public void supplierSetTextArea(HashMap<String, TextArea> textarea)
    {
        this.suppliersTextArea = textarea;
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
    public HashMap<String, TextArea> supplierGetTextArea()
    {
        return this.suppliersTextArea;
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
    public void contractorsSetTextFields(HashMap<String, TextField> contractorsField)
    {
        this.contractorsTextFieldList = contractorsField;
    }
    public void contractorsSetCombobox(HashMap<String, ComboBox> combobox)
    {
        this.contractorsComboboxList = combobox;
    }
    public void contractorsSetListView(HashMap<String, ListView> listview)
    {
        this.contractorsListView = listview;
    }
    public void contractorsSetTextArea(HashMap<String, TextArea> textarea)
    {
        this.contractorsTextArea = textarea;
    }
    public HashMap<String, TextField> contractorsGetTextFields()
    {
        return this.contractorsTextFieldList;
    }
    public HashMap<String, ComboBox> contractorsGetCombobox()
    {
        return this.contractorsComboboxList;
    }
    public HashMap<String, ListView> contractorsGetListView()
    {
        return this.contractorsListView;
    }
    public HashMap<String, TextArea> contractorsGetTextArea()
    {
        return this.contractorsTextArea;
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
    public void consultantsSetTextFields(HashMap<String, TextField> consultantsField)
    {
        this.consultantsTextFieldList = consultantsField;
    }
    public void consultantsSetCombobox(HashMap<String, ComboBox> combobox)
    {
        this.consultantsComboboxList = combobox;
    }
    public void consultantsSetListView(HashMap<String, ListView> listview)
    {
        this.consultantsListView = listview;
    }
    public void consultantsSetTextArea(HashMap<String, TextArea> textArea)
    {
        this.consultantsTextArea = textArea;
    }
    public HashMap<String, TextField> consultantsGetTextFields()
    {
        return this.consultantsTextFieldList;
    }
    public HashMap<String, ComboBox> consultantsGetCombobox()
    {
        return this.consultantsComboboxList;
    }
    public HashMap<String, ListView> consultantsGetListView()
    {
        return this.consultantsListView;
    }
    public HashMap<String, TextArea> consultantsGetTextArea()
    {
        return this.consultantsTextArea;
    }
    public void consultantsSetContainer(AnchorPane container)
    {
        this.consultantsContainer = container;
    }
    public AnchorPane consultantsGetContainer()
    {
        return this.consultantsContainer;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void specificationsSetTextFields(HashMap<String, TextField> specificationsField)
    {
        this.specificationsTextFieldList = specificationsField;
    }
    public void specificationsSetTextArea(HashMap<String, TextArea> specificationsTextArea)
    {
        this.specificationsTextArea = specificationsTextArea;
    }
    public void specificationsSetListView(HashMap<String, ListView> listview)
    {
        this.specificationsListView = listview;
    }
    public void specificationsSetRemarks(HashMap<String, TextArea> specificationsTextArea)
    {
        this.specificationsRemarks = specificationsTextArea;
    }
    public HashMap<String, TextField> specificationsGetTextFields()
    {
        return this.specificationsTextFieldList;
    }
    public HashMap<String, TextArea> specificationsGetTextArea()
    {
        return this.specificationsTextArea;
    }
    public HashMap<String, ListView> specificationsGetListView()
    {
        return this.specificationsListView;
    }
    public HashMap<String, TextArea> specificationsGetRemarks()
    {
        return this.specificationsRemarks;
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
