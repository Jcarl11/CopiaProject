package com.carlo.copiaproject;

import DatabaseOperations.DatabaseQuery;
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
import org.apache.commons.io.FilenameUtils;

public class ContractorsController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    @FXML AnchorPane anchorpane_contractors;
    @FXML private ListView<String> listview_contractors_FiletoUpload;
    @FXML private ComboBox<String> combobox_contractors_industry,combobox_contractors_classificiation;
    @FXML private TextField textfield_contractors_representative, textfield_contractors_position,
            textfield_contractors_companyname,textfield_contractors_specialization;
    @FXML
    void button_contractors_choosefileOnClick(ActionEvent event) 
    {
        listview_contractors_FiletoUpload.getItems().addAll(showChooserDialog("pdf","jpg","png","gif"));
    }
    
    @FXML
    void button_contractors_previewOnClick(ActionEvent event) 
    {
        /*if(listview_contractors_FiletoUpload.getSelectionModel().getSelectedItem() != null)
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
        }*/
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
            //combobox_contractors_industry.setItems(new SortedList<String>(dbQuery.RetrieveComboboxData("Contractors", "Industry"), Collator.getInstance()));
            //combobox_contractors_classificiation.setItems(new SortedList<String>(dbQuery.RetrieveComboboxData("Contractors", "Classification"), Collator.getInstance()));
            HashMap<String, Object> fields = new HashMap<>();
            fields.put("Representative", textfield_contractors_representative);
            fields.put("Position", textfield_contractors_position);
            fields.put("Company Name", textfield_contractors_companyname);
            fields.put("Specialization", textfield_contractors_specialization);
            fields.put("Industry", combobox_contractors_industry);
            fields.put("Classification", combobox_contractors_classificiation);
            fields.put("Files", listview_contractors_FiletoUpload);
            GetOtherControllerAttributesSingleton.getInstance().contractorsSetContainer(anchorpane_contractors);
            GetOtherControllerAttributesSingleton.getInstance().contractorsSetFields(fields);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
           
    }    
    
}
