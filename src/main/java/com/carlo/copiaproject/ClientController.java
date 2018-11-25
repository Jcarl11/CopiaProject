package com.carlo.copiaproject;

import DatabaseOperations.RetrieveClientCombobox;
import Entities.ComboboxDataEntity;
import MiscellaneousClasses.*;
import java.io.File;
import java.net.URL;
import java.text.Collator;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FXML Controller class
 *
 * @author Joey Francisco
 */
public class ClientController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    @FXML private TextField textfield_client_representative,textfield_client_position,textfield_client_companyname;
    @FXML private ListView<String> listview_client_FiletoUpload;
    @FXML private ComboBox<String> combobox_client_industry,combobox_client_type;
    @FXML private AnchorPane anchorpane_client;
    
    
    
    @FXML void button_clients_choosefileOnClick(ActionEvent event)
    {
        listview_client_FiletoUpload.getItems().addAll(showChooserDialog("pdf","jpg","png","gif"));
    }
    
    @FXML void button_client_previewOnClick(ActionEvent event) 
    {
        /*if(listview_client_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            anchorpane_viewdocument.getChildren().clear();
            String extension = FilenameUtils.getExtension(listview_client_FiletoUpload.getSelectionModel().getSelectedItem());
            if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif"))
            {
                anchorpane_viewdocument.getChildren().add(previewimage.showImage(listview_client_FiletoUpload.getSelectionModel().getSelectedItem()));
            }
            else if(extension.equalsIgnoreCase("pdf"))
            {
                try
                {
                    //anchorpane_viewdocument.getChildren().add(previewpdf.showPDF(listview_client_FiletoUpload.getSelectionModel().getSelectedItem()));
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
    
    @FXML void button_client_removeOnClick(ActionEvent event) 
    {
        if(listview_client_FiletoUpload.getSelectionModel().getSelectedItem() != null)
        {
            int selection = JOptionPane.showConfirmDialog(null, "Delete selected fie?", "Confirm", 
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selection == JOptionPane.YES_OPTION)
            {
                int index = listview_client_FiletoUpload.getSelectionModel().getSelectedIndex();
                listview_client_FiletoUpload.getItems().remove(index);
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
        ObservableList<String> industryList = FXCollections.observableArrayList();
        ObservableList<String> typeList = FXCollections.observableArrayList();
        try
        {
            if(CacheManage.getInstance().getClientComboboxCache("Client") != null)
            {
                System.out.println("Not null");
            }
            else
            {
                RetrieveClientCombobox retrieve = new RetrieveClientCombobox("Client");
                Thread retrieveThread = new Thread(retrieve);
                retrieveThread.start();
                try{retrieveThread.join();}catch(Exception ex){ex.printStackTrace();}
                System.out.println(retrieve.getResult().size());
                
                for(ComboboxDataEntity comboboxData : retrieve.getResult())
                {
                    if(comboboxData.getField().equals("Industry"))
                    {
                        industryList.add(comboboxData.getTitle());
                    }
                    else if(comboboxData.getField().equals("Type"))
                    {
                        typeList.add(comboboxData.getTitle());
                    }
                }
                System.out.println(industryList.size());
                if(retrieve.getResult() != null)
                {
                    CacheManage.getInstance().addClientComboboxCache("Client", retrieve.getResult());
                }
                    
            
            }
            combobox_client_industry.setItems(new SortedList<String>(industryList, Collator.getInstance()));
            combobox_client_type.setItems(new SortedList<String>(typeList, Collator.getInstance()));
            HashMap<String, Object> fields = new HashMap<>();
            fields.put("Representative", textfield_client_representative);
            fields.put("Position", textfield_client_position);
            fields.put("Company Name", textfield_client_companyname);
            fields.put("Industry", combobox_client_industry);
            fields.put("Type", combobox_client_type);
            fields.put("Files", listview_client_FiletoUpload);
            GetOtherControllerAttributesSingleton.getInstance().clientSetFields(fields);
            GetOtherControllerAttributesSingleton.getInstance().clientSetContainer(anchorpane_client);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }   
          
    }    
    
}
