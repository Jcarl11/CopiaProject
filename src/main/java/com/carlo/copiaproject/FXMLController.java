package com.carlo.copiaproject;

import MiscellaneousClasses.*;
import UploadProcess.ClientUpload;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    PreviewImage previewimage = new PreviewImage();
    ClientEntity clientEntity = new ClientEntity();
    SuppliersEntity suppliersEntity = new SuppliersEntity();
    @FXML private Button button_search;
    @FXML private AnchorPane anchorpane_main,anchorpane_viewdocument;
    
    
    @FXML private Parent client_file,suppliers_file,contractors_file,specifications_file,searchrecord_file;
    
    @FXML void clientOnClicked(ActionEvent event) throws IOException
    {
        SectionsManager.showPane(anchorpane_main, client_file);
    }
    @FXML void suppliersOnClicked(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, suppliers_file);
    }
    @FXML void button_contractorsOnClick(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, contractors_file);
    }
    @FXML void specificationsClicked(ActionEvent event)
    {
        //SectionsManager.showPane(anchorpane_main, gridpane_specifications);
        SectionsManager.showPane(anchorpane_main, specifications_file);
    }
    @FXML void searchRecordsOnClick(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, searchrecord_file);
    }
    
    @FXML void button_upload(ActionEvent event)
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
    
    @FXML void button_resetfields(ActionEvent event) 
    {
        //previewpdf.clear();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        anchorpane_main.getChildren().clear();
    }    
}
