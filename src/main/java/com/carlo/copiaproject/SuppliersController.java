package com.carlo.copiaproject;

import MiscellaneousClasses.DatabaseQuery;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import java.io.File;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SuppliersController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    @FXML private TextField textfield_suppliers_representative, textfield_suppliers_position, 
            textfield_suppliers_companyname, textfield_suppliers_brand;
    @FXML private ListView<String> listview_suppliers_FiletoUpload; 
    @FXML private ComboBox<String> combobox_suppliers_industry,combobox_suppliers_type;
    @FXML private AnchorPane anchorpane_suppliers;
    
    @FXML
    void button_suppliers_choosefileOnClick(ActionEvent event)
    {
        listview_suppliers_FiletoUpload.getItems().addAll(showChooserDialog("pdf","jpg","png","gif"));
    }
    
    @FXML
    void button_suppliers_previewOnClick(ActionEvent event) 
    {
        /*if(listview_suppliers_FiletoUpload.getSelectionModel().getSelectedItem() != null)
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
        }*/
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
        try
        {
            combobox_suppliers_industry.setItems(new SortedList<String>(dbQuery.RetrieveComboboxData("Suppliers", "Industry"), Collator.getInstance()));
            combobox_suppliers_type.setItems(new SortedList<String>(dbQuery.RetrieveComboboxData("Suppliers", "Type"), Collator.getInstance()));
            HashMap<String, Object> fields = new HashMap<>();
            fields.put("Representative", textfield_suppliers_representative);
            fields.put("Position", textfield_suppliers_position);
            fields.put("Company Name", textfield_suppliers_companyname);
            fields.put("Brand", textfield_suppliers_brand);
            fields.put("Industry", combobox_suppliers_industry);
            fields.put("Type", combobox_suppliers_type);
            fields.put("Files", listview_suppliers_FiletoUpload);
            GetOtherControllerAttributesSingleton.getInstance().supplierSetFields(fields);
            GetOtherControllerAttributesSingleton.getInstance().supplierSetContainer(anchorpane_suppliers);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }   
    
    
}
