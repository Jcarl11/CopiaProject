package com.carlo.copiaproject;

import DatabaseOperations.DatabaseQuery;
import DatabaseOperations.LocalStorage;
import DatabaseOperations.RetrieveCombobox;
import Entities.ComboboxDataEntity;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import MiscellaneousClasses.PreviewImage;
import java.io.File;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;

public class SuppliersController implements Initializable 
{
    PreviewImage previewimage = new PreviewImage();
    DatabaseQuery dbQuery = new DatabaseQuery();
    @FXML TextField textfield_suppliers_representative,textfield_suppliers_position,textfield_suppliers_companyname,textfield_suppliers_brand;
    @FXML ListView<String> listview_suppliers_FiletoUpload; 
    @FXML ComboBox<String> combobox_suppliers_industry,combobox_suppliers_type;
    @FXML AnchorPane anchorpane_suppliers;
    @FXML TextArea suppliers_textarea;
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
            GetOtherControllerAttributesSingleton.getInstance().previewGetContainer().getChildren().clear();
            String extension = FilenameUtils.getExtension(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem());
            if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif"))
            {
                GetOtherControllerAttributesSingleton.getInstance().previewGetContainer().getChildren().add(previewimage.showImage(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem()));
            }
            /*else if(extension.equalsIgnoreCase("pdf"))
            {
                try
                {
                    //anchorpane_viewdocument.getChildren().add(previewpdf.showPDF(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem()));
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }*/
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        ArrayList<ComboboxDataEntity> result = new ArrayList<>();
        ObservableList<String> industryList = FXCollections.observableArrayList();
        ObservableList<String> typeList = FXCollections.observableArrayList();
        try
        {
            result = LocalStorage.getInstance().retrieve_local_ComboboxData("Suppliers");
            if(result.size() > 0)
            {
                for(ComboboxDataEntity comboboxData : result)
                {
                    if(comboboxData.getField().equals("INDUSTRY"))
                    {
                        industryList.add(comboboxData.getTitle());
                    }
                    else if(comboboxData.getField().equals("TYPE"))
                    {
                        typeList.add(comboboxData.getTitle());
                    }
                }
            }
            else
            {
                RetrieveCombobox retrieve = new RetrieveCombobox("Suppliers");
                Thread retrieveThread = new Thread(retrieve);
                retrieveThread.start();
                try{retrieveThread.join();}catch(Exception ex){ex.printStackTrace();}
                
                for(ComboboxDataEntity comboboxData : retrieve.getResult())
                {
                    if(comboboxData.getField().equals("Industry"))
                    {
                        industryList.add(comboboxData.getTitle());
                        LocalStorage.getInstance().insert_local_ComboboxData(comboboxData);
                    }
                    else if(comboboxData.getField().equals("Type"))
                    {
                        typeList.add(comboboxData.getTitle());
                        LocalStorage.getInstance().insert_local_ComboboxData(comboboxData);
                    }
                }
            }
            combobox_suppliers_industry.setItems(new SortedList<String>(industryList, Collator.getInstance()));
            combobox_suppliers_type.setItems(new SortedList<String>(typeList, Collator.getInstance()));
            HashMap<String, TextField> textfields = new HashMap<>();
            HashMap<String, ComboBox> comboboxfields = new HashMap<>();
            HashMap<String, ListView> listviewfields = new HashMap<>();
            HashMap<String, TextArea> textarea = new HashMap<>();
            textfields.put("Representative", textfield_suppliers_representative);
            textfields.put("Position", textfield_suppliers_position);
            textfields.put("Company Name", textfield_suppliers_companyname);
            textfields.put("Brand", textfield_suppliers_brand);
            comboboxfields.put("Industry", combobox_suppliers_industry);
            comboboxfields.put("Type", combobox_suppliers_type);
            listviewfields.put("Files", listview_suppliers_FiletoUpload);
            textarea.put("Remarks", suppliers_textarea);
            GetOtherControllerAttributesSingleton.getInstance().supplierSetTextFields(textfields);
            GetOtherControllerAttributesSingleton.getInstance().supplierSetCombobox(comboboxfields);
            GetOtherControllerAttributesSingleton.getInstance().supplierSetListView(listviewfields);
            GetOtherControllerAttributesSingleton.getInstance().supplierSetTextArea(textarea);
            GetOtherControllerAttributesSingleton.getInstance().supplierSetContainer(anchorpane_suppliers);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }   
    
    
}
