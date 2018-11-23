package com.carlo.copiaproject;

import MiscellaneousClasses.*;
import UploadProcess.ClientUpload;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.io.FilenameUtils;

public class FXMLController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    PreviewImage previewimage = new PreviewImage();
    ClientEntity clientEntity = new ClientEntity();
    SuppliersEntity suppliersEntity = new SuppliersEntity();
    Object i = new Object();
    @FXML
    private Button button_search;
    
    @FXML
    private ListView<String> listview_specifications_FiletoUpload,listview_suppliers_FiletoUpload, 
            listview_contractors_FiletoUpload,listview_searchrecord_fileshowcase;
    @FXML
    private AnchorPane anchorpane_main,anchorpane_viewdocument;
    @FXML
    private GridPane gridpane_specifications,gridpane_consultants,gridpane_contractors,gridpane_suppliers,gridpane_searchrecords;
    @FXML
    private ComboBox<String> combobox_suppliers_industry,combobox_suppliers_type
            ,combobox_contractors_industry,combobox_contractors_classificiation,combobox_searchrecords_searchin;
    @FXML
    private TextField textfield_searchrecords_keyword,textfield_suppliers_representative,textfield_suppliers_position,textfield_suppliers_companyname,textfield_suppliers_brand;
    @FXML
    private TableView<ClientEntity> tableview_searchinrecord;
    
    @FXML void clientOnClicked(ActionEvent event)
    {
        GetOtherControllerAttributesSingleton.getInstance().clientGetContainer().setVisible(true);
    }
    
    @FXML
    void suppliersOnClicked(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, gridpane_suppliers);
    }
    
    @FXML
    void button_suppliers_choosefileOnClick(ActionEvent event)
    {
        listview_suppliers_FiletoUpload.getItems().addAll(showChooserDialog("pdf","jpg","png","gif"));
    }
    
    @FXML
    void button_suppliers_previewOnClick(ActionEvent event) 
    {
        if(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            anchorpane_viewdocument.getChildren().clear();
            String extension = FilenameUtils.getExtension(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem());
            if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif"))
            {
                anchorpane_viewdocument.getChildren().add(previewimage.showImage(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem()));
            }
            else if(extension.equalsIgnoreCase("pdf"))
            {
                try
                {
                    //anchorpane_viewdocument.getChildren().add(previewpdf.showPDF(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem()));
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "File type not supported for preview");
            }
        }
    }

    @FXML
    void button_suppliers_removeOnClick(ActionEvent event) 
    {
        if(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            int selection = JOptionPane.showConfirmDialog(null, "Delete selected fie?", "Confirm", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selection == JOptionPane.YES_OPTION)
            {
                int index = listview_suppliers_FiletoUpload.getSelectionModel().getSelectedIndex();
                listview_suppliers_FiletoUpload.getItems().remove(index);
            }
        }
    }
    
    @FXML
    void specificationsClicked(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, gridpane_specifications);
    }
    
    @FXML
    void button_specifications_choosefileOnClick(ActionEvent event) 
    {
        listview_specifications_FiletoUpload.getItems().addAll(showChooserDialog("pdf"));
    }
    
    @FXML
    void button_specifications_previewOnClick(ActionEvent event)
    {
        
        if(listview_specifications_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
           anchorpane_viewdocument.getChildren().clear();
            try{
                //anchorpane_viewdocument.getChildren().add(previewpdf.showPDF(listview_specifications_FiletoUpload.getSelectionModel().getSelectedItem()));
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            System.out.println("Error");
        }
    }
    
    @FXML
    void button_specifications_removeOnClick(ActionEvent event) 
    {
         if(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            int selection = JOptionPane.showConfirmDialog(null, "Delete selected fie?", "Confirm", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selection == JOptionPane.YES_OPTION)
            {
                int index = listview_suppliers_FiletoUpload.getSelectionModel().getSelectedIndex();
                listview_suppliers_FiletoUpload.getItems().remove(index);
            }
        }
    }
    
    @FXML
    void button_contractorsOnClick(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, gridpane_contractors);
    }
    
    @FXML
    void button_contractors_choosefileOnClick(ActionEvent event) 
    {
        listview_contractors_FiletoUpload.getItems().addAll(showChooserDialog("pdf","jpg","png","gif"));
    }
    
    @FXML
    void button_contractors_previewOnClick(ActionEvent event) 
    {
        if(listview_contractors_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            anchorpane_viewdocument.getChildren().clear();
            String extension = FilenameUtils.getExtension(listview_contractors_FiletoUpload.getSelectionModel().getSelectedItem());
            if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif"))
            {
                anchorpane_viewdocument.getChildren().add(previewimage.showImage(listview_contractors_FiletoUpload.getSelectionModel().getSelectedItem()));
            }
            else if(extension.equalsIgnoreCase("pdf"))
            {
                try
                {
                    //anchorpane_viewdocument.getChildren().add(previewpdf.showPDF(listview_contractors_FiletoUpload.getSelectionModel().getSelectedItem()));
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "File type not supported for preview");
            }
        }
    }
    @FXML
    void button_contractors_removeOnClick(ActionEvent event) 
    {
         if(listview_contractors_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            int selection = JOptionPane.showConfirmDialog(null, "Delete selected fie?", "Confirm", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selection == JOptionPane.YES_OPTION)
            {
                int index = listview_contractors_FiletoUpload.getSelectionModel().getSelectedIndex();
                listview_contractors_FiletoUpload.getItems().remove(index);
            }
        }
    }
    
    @FXML
    void searchRecordsOnClick(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, gridpane_searchrecords);
    }
    @FXML
    void button_searchrecords_showfilesOnClick(ActionEvent event)
    {
        listview_searchrecord_fileshowcase.getItems().clear();
        String objId = tableview_searchinrecord.getSelectionModel().getSelectedItem().getObjectID();
        //dbQuery.RetrieveAssociatedFiles(objId, listview_searchrecord_fileshowcase);
        RetrieveAssociatedFiles raf = new RetrieveAssociatedFiles(objId);
        Thread thread = new Thread(raf);
        thread.start();
        try{thread.join();}catch(Exception ex){ex.printStackTrace();}
        ArrayList<String> result = raf.getResult();
        if(result.size() > 0)
        {
            listview_searchrecord_fileshowcase.getItems().clear();
            listview_searchrecord_fileshowcase.getItems().addAll(result);
        }
        else
        {
            listview_searchrecord_fileshowcase.getItems().clear();
            JOptionPane.showMessageDialog(null, "No Files found");
        }
    }
    @FXML
    void button_upload(ActionEvent event)
    {
        if(anchorpane_main.getChildren().contains(GetOtherControllerAttributesSingleton.getInstance().clientGetContainer()))
        {
            
            HashMap<String, Object> clientCategoryFields = GetOtherControllerAttributesSingleton.getInstance().clientGetFields();
            clientEntity.setRepresentative(((TextField)clientCategoryFields.get("Representative")).getText().toUpperCase());
            clientEntity.setPosition(((TextField)clientCategoryFields.get("Position")).getText().toUpperCase());
            clientEntity.setCompany_Name(((TextField)clientCategoryFields.get("Company Name")).getText().toUpperCase());
            clientEntity.setIndustry(((ComboBox)clientCategoryFields.get("Industry")).getSelectionModel().getSelectedItem().toString().toUpperCase());
            clientEntity.setType(((ComboBox)clientCategoryFields.get("Type")).getSelectionModel().getSelectedItem().toString().toUpperCase());
            if(((ListView)clientCategoryFields.get("Files")).getItems().size() > 0)
            {
                ArrayList<File> files = new ArrayList<>();
                for(int counter = 0; counter < ((ListView)clientCategoryFields.get("Files")).getItems().size(); counter++)
                {
                    String path = (String)((ListView)clientCategoryFields.get("Files")).getItems().get(counter);
                    files.add(new File(path));
                }
                clientEntity.setFileToUpload(files);
            }
            ClientUpload clientUpload = new ClientUpload(clientEntity);
            clientUpload.upload();
        }
    }
    
    @FXML
    void button_resetfields(ActionEvent event) 
    {
        //previewpdf.clear();
    }
    
    private ArrayList<String> showChooserDialog(String... acceptableFileTypes)
    {
        ArrayList<String> paths = new ArrayList<>();
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        chooser.setFileFilter(new FileNameExtensionFilter("Files",acceptableFileTypes));
        chooser.showOpenDialog(null);
        File[] selectedFiles = chooser.getSelectedFiles();
        if(selectedFiles != null)
        {
            for(File getPath: selectedFiles)
            {
                paths.add(getPath.getAbsolutePath().toString());
            }
        }
        return paths;
    }
    
    @FXML
    void button_searchrecords_search(ActionEvent event)
    {
        ArrayList<ClientEntity> clientEntityList = new ArrayList<>();
        String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem();
        String search = textfield_searchrecords_keyword.getText().trim();
        RetrieveValues ret = new RetrieveValues(search, searchIn);
        Thread thread = new Thread(ret);
        thread.start();
        try{thread.join();}catch(Exception ex){ex.printStackTrace();}
        clientEntityList = ret.getResult();
        if(tableview_searchinrecord.getColumns().isEmpty())
        {
            initializeTable();
            tableview_searchinrecord.getItems().addAll(clientEntityList);
        }
        else
        {
            tableview_searchinrecord.getItems().clear();
            tableview_searchinrecord.getItems().addAll(clientEntityList);
        }
    }
    
    public void initializeTable()
    {
        TableColumn objectid = new TableColumn("ObjectID");
        TableColumn representative = new TableColumn("Representative");
        TableColumn position = new TableColumn("Position");
        TableColumn company = new TableColumn("Company");
        TableColumn industry = new TableColumn("Industry");
        TableColumn type = new TableColumn("Type");

        tableview_searchinrecord.getColumns().add(objectid);
        tableview_searchinrecord.getColumns().add(representative);
        tableview_searchinrecord.getColumns().add(position);
        tableview_searchinrecord.getColumns().add(company);
        tableview_searchinrecord.getColumns().add(industry);
        tableview_searchinrecord.getColumns().add(type);

        objectid.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("ObjectID"));
        representative.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Representative"));
        position.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Position"));
        company.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Company_Name"));
        industry.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Industry"));
        type.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Type"));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        //SectionsManager.clearThis(anchorpane_main);
        try
        {
            /*combobox_suppliers_industry.setItems(new SortedList<String>(dbQuery.RetrieveComboboxData("Suppliers", "Industry"), Collator.getInstance()));
            combobox_suppliers_type.setItems(new SortedList<String>(dbQuery.RetrieveComboboxData("Suppliers", "Type"), Collator.getInstance()));
            combobox_contractors_industry.setItems(new SortedList<String>(dbQuery.RetrieveComboboxData("Contractors", "Industry"), Collator.getInstance()));
            combobox_contractors_classificiation.setItems(new SortedList<String>(dbQuery.RetrieveComboboxData("Contractors", "Classification"), Collator.getInstance()));
            combobox_searchrecords_searchin.setItems(new SortedList<String>(dbQuery.RetrieveComboboxDataCategories(), Collator.getInstance()));*/
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        /*gridpane_suppliers.setVisible(true);
        gridpane_contractors.setVisible(true);
        gridpane_specifications.setVisible(true);
        gridpane_searchrecords.setVisible(true);*/
    }    
}
