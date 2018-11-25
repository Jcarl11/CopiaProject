package com.carlo.copiaproject;

import MiscellaneousClasses.DatabaseQuery;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
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
    @FXML TextArea textfield_specifications_keywords;
    
    
    @FXML
    void button_specifications_choosefileOnClick(ActionEvent event) 
    {
        listview_specifications_FiletoUpload.getItems().addAll(showChooserDialog("pdf"));
    }
    
    @FXML
    void button_specifications_previewOnClick(ActionEvent event)
    {
        /*if(listview_specifications_FiletoUpload.getSelectionModel().getSelectedItem() != null)
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
        }*/
    }
    
    @FXML
    void button_specifications_removeOnClick(ActionEvent event) 
    {
         if(listview_specifications_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            int selection = JOptionPane.showConfirmDialog(null, "Delete selected fie?", "Confirm", 
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
        try
        {
            HashMap<String, Object> fields = new HashMap<>();
            fields.put("Document", textfield_specifications_document);
            fields.put("Division", textfield_specifications_division);
            fields.put("Section", textfield_specifications_section);
            fields.put("Type", textfield_specifications_type);
            fields.put("Keyword", textfield_specifications_keywords);
            fields.put("Files", listview_specifications_FiletoUpload);
            GetOtherControllerAttributesSingleton.getInstance().specificationsSetContainer(anchorpane_specifications);
            GetOtherControllerAttributesSingleton.getInstance().specificationsSetFields(fields);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }    
    
}
