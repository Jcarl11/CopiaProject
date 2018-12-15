package com.carlo.copiaproject;

import DatabaseOperations.DatabaseQuery;
import DatabaseOperations.LocalStorage;
import DatabaseOperations.RetrieveCombobox;
import Entities.ComboboxDataEntity;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
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

public class ContractorsController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    @FXML AnchorPane anchorpane_contractors;
    @FXML ListView<String> listview_contractors_FiletoUpload;
    @FXML ComboBox<String> combobox_contractors_industry,combobox_contractors_classificiation;
    @FXML TextField textfield_contractors_representative,textfield_contractors_position,textfield_contractors_companyname
            ,textfield_contractors_specialization;
    @FXML TextArea contractors_textarea;
    
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
            else
            {
                RetrieveCombobox retrieve = new RetrieveCombobox("Contractors");
                Thread retrieveThread = new Thread(retrieve);
                retrieveThread.start();
                try{retrieveThread.join();}catch(Exception ex){ex.printStackTrace();}
                System.out.println("Size is: " + retrieve.getResult().size());
                for(ComboboxDataEntity comboboxData : retrieve.getResult())
                {
                    if(comboboxData.getField().equals("Industry"))
                    {
                        industryList.add(comboboxData.getTitle());
                        LocalStorage.getInstance().insert_local_ComboboxData(comboboxData);
                    }
                    else if(comboboxData.getField().equals("Classification"))
                    {
                        classificationList.add(comboboxData.getTitle());
                        LocalStorage.getInstance().insert_local_ComboboxData(comboboxData);
                    }
                }
                
            }
            combobox_contractors_industry.setItems(new SortedList<String>(industryList, Collator.getInstance()));
            combobox_contractors_classificiation.setItems(new SortedList<String>(classificationList, Collator.getInstance()));
            HashMap<String, TextField> contractorstextfieldsList = new HashMap<>();
            HashMap<String, ComboBox> combobox = new HashMap<>();
            HashMap<String, ListView> listView = new HashMap<>();
            HashMap<String, TextArea> textarea = new HashMap<>();
            contractorstextfieldsList.put("Representative", textfield_contractors_representative);
            contractorstextfieldsList.put("Position", textfield_contractors_position);
            contractorstextfieldsList.put("Company", textfield_contractors_companyname);
            contractorstextfieldsList.put("Specialization", textfield_contractors_specialization);
            combobox.put("Industry", combobox_contractors_industry);
            combobox.put("Classification", combobox_contractors_classificiation);
            listView.put("Files", listview_contractors_FiletoUpload);
            textarea.put("Remarks", contractors_textarea);
            GetOtherControllerAttributesSingleton.getInstance().contractorsSetTextFields(contractorstextfieldsList);
            GetOtherControllerAttributesSingleton.getInstance().contractorsSetCombobox(combobox);
            GetOtherControllerAttributesSingleton.getInstance().contractorsSetListView(listView);
            GetOtherControllerAttributesSingleton.getInstance().contractorsSetTextArea(textarea);
            GetOtherControllerAttributesSingleton.getInstance().contractorsSetContainer(anchorpane_contractors);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
           
    }    
    
}
