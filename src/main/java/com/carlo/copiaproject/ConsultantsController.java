package com.carlo.copiaproject;

import DatabaseOperations.DatabaseQuery;
import DatabaseOperations.LocalStorage;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * FXML Controller class
 *
 * @author Joey Francisco
 */
public class ConsultantsController implements Initializable 
{
    PreviewImage previewimage = new PreviewImage();
    DatabaseQuery dbQuery = new DatabaseQuery();
    @FXML AnchorPane anchorpane_consultants;
    @FXML private ListView<String> listview_consultants_FiletoUpload;
    @FXML private ComboBox<String> combobox_consultants_industry, combobox_consultants_classificiation;
    @FXML private TextField textfield_consultants_representative, textfield_consultants_position, textfield_consultants_companyname
            ,textfield_consultants_specialization;
    @FXML TextArea consultants_textarea;
    
    @FXML 
    void button_consultants_choosefileOnClick(ActionEvent event)
    {
        listview_consultants_FiletoUpload.getItems().addAll(showChooserDialog("pdf","jpg","png","gif"));
    }
    
    @FXML 
    void button_consultants_previewOnClick(ActionEvent event)
    {
        if(listview_consultants_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            GetOtherControllerAttributesSingleton.getInstance().previewGetContainer().getChildren().clear();
            String extension = FilenameUtils.getExtension(listview_consultants_FiletoUpload.getSelectionModel().getSelectedItem());
            if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif"))
            {
                GetOtherControllerAttributesSingleton.getInstance().previewGetContainer().getChildren().add(previewimage.showImage(listview_consultants_FiletoUpload.getSelectionModel().getSelectedItem()));
            }
            /*else if(extension.equalsIgnoreCase("pdf"))
            {
                try
                {
                    //anchorpane_viewdocument.getChildren().add(previewpdf.showPDF(listview_contractors_FiletoUpload.getSelectionModel().getSelectedItem()));
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
    void button_consultants_removeOnClick(ActionEvent event)
    {
         if(listview_consultants_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            int selection = JOptionPane.showConfirmDialog(null, "Delete selected fie?", "Confirm", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selection == JOptionPane.YES_OPTION)
            {
                int index = listview_consultants_FiletoUpload.getSelectionModel().getSelectedIndex();
                listview_consultants_FiletoUpload.getItems().remove(index);
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
        ObservableList<String> classificationList = FXCollections.observableArrayList();
        try
        {
            result = LocalStorage.getInstance().retrieve_local_ComboboxData("Contractors");
            
            if(result.size() > 0)
            {
                for(ComboboxDataEntity comboboxData : result)
                {
                    if(comboboxData.getField().equalsIgnoreCase("Industry"))
                    {
                        industryList.add(comboboxData.getTitle());
                    }
                    else if(comboboxData.getField().equalsIgnoreCase("Classification"))
                    {
                        classificationList.add(comboboxData.getTitle());
                    }
                }
            }
            combobox_consultants_industry.setItems(new SortedList<String>(industryList, Collator.getInstance()));
            combobox_consultants_classificiation.setItems(new SortedList<String>(classificationList, Collator.getInstance()));
            HashMap<String, TextField> fields = new HashMap<>();
            HashMap<String, ComboBox> combobox = new HashMap<>();
            HashMap<String, ListView> listView = new HashMap<>();
            HashMap<String, TextArea> textarea = new HashMap<>();
            fields.put("Representative", textfield_consultants_representative);
            fields.put("Position", textfield_consultants_position);
            fields.put("Company Name", textfield_consultants_companyname);
            fields.put("Specialization", textfield_consultants_specialization);
            combobox.put("Industry", combobox_consultants_industry);
            combobox.put("Classification", combobox_consultants_classificiation);
            listView.put("Files", listview_consultants_FiletoUpload);
            textarea.put("Remarks", consultants_textarea);
            GetOtherControllerAttributesSingleton.getInstance().consultantsSetTextFields(fields);
            GetOtherControllerAttributesSingleton.getInstance().consultantsSetCombobox(combobox);
            GetOtherControllerAttributesSingleton.getInstance().consultantsSetListView(listView);
            GetOtherControllerAttributesSingleton.getInstance().consultantsSetTextArea(textarea);
            GetOtherControllerAttributesSingleton.getInstance().consultantsSetContainer(anchorpane_consultants);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }    
    
}
