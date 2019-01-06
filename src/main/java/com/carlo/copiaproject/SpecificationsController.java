package com.carlo.copiaproject;

import DatabaseOperations.DatabaseQuery;
import DatabaseOperations.LocalStorage;
import DatabaseOperations.RetrieveCombobox;
import Entities.ComboboxDataEntity;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SpecificationsController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    
    @FXML ListView<String> listview_specifications_FiletoUpload;
    @FXML AnchorPane anchorpane_specifications;
    @FXML TextField textfield_specifications_document, textfield_specifications_division
            ,textfield_specifications_section,textfield_specifications_type;
    @FXML TextArea textfield_specifications_keywords, specifications_textarea;
    
    @FXML
    void button_specifications_choosefileOnClick(ActionEvent event) 
    {
        listview_specifications_FiletoUpload.getItems().addAll(showChooserDialog("pdf"));
    }
    
    
    @FXML
    void button_specifications_removeOnClick(ActionEvent event) 
    {
         if(listview_specifications_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            int selection = JOptionPane.showConfirmDialog(null, "Delete selected file?", "Confirm", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selection == JOptionPane.YES_OPTION)
            {
                int index = listview_specifications_FiletoUpload.getSelectionModel().getSelectedIndex();
                listview_specifications_FiletoUpload.getItems().remove(index);
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
            result = LocalStorage.getInstance().retrieve_local_ComboboxData("Specifications");
            if(result.size() > 0){}
            else
            {
                RetrieveCombobox retrieve = new RetrieveCombobox("Specifications");
                Thread retrieveThread = new Thread(retrieve);
                retrieveThread.start();
                try{retrieveThread.join();}catch(Exception ex){ex.printStackTrace();}
                
                for(ComboboxDataEntity comboboxData : retrieve.getResult())
                {
                    LocalStorage.getInstance().insert_local_ComboboxData(comboboxData);
                }
            }
            
            
            HashMap<String, TextField> fields = new HashMap<>();
            HashMap<String, TextArea> textArea = new HashMap<>();
            HashMap<String, ListView> listView = new HashMap<>();
            HashMap<String, TextArea> remarks = new HashMap<>();
            fields.put("Document", textfield_specifications_document);
            fields.put("Division", textfield_specifications_division);
            fields.put("Section", textfield_specifications_section);
            fields.put("Type", textfield_specifications_type);
            textArea.put("Keyword", textfield_specifications_keywords);
            listView.put("Files", listview_specifications_FiletoUpload);
            remarks.put("Remarks", specifications_textarea);
            GetOtherControllerAttributesSingleton.getInstance().specificationsSetTextFields(fields);
            GetOtherControllerAttributesSingleton.getInstance().specificationsSetTextArea(textArea);
            GetOtherControllerAttributesSingleton.getInstance().specificationsSetListView(listView);
            GetOtherControllerAttributesSingleton.getInstance().specificationsSetRemarks(remarks);
            GetOtherControllerAttributesSingleton.getInstance().specificationsSetContainer(anchorpane_specifications);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }    
    
}
