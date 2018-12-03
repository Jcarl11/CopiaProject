package com.carlo.copiaproject;

import DatabaseOperations.*;
import Entities.*;
import MiscellaneousClasses.*;
import UploadProcess.ClientUpload;
import UploadProcess.SuppliersUpload;
import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class FXMLController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    PreviewImage previewimage = new PreviewImage();
    ClientEntity clientEntity = new ClientEntity();
    SuppliersEntity suppliersEntity = new SuppliersEntity();
    @FXML private Button button_search;
    @FXML private AnchorPane anchorpane_main,anchorpane_viewdocument;
    @FXML private Parent client_file,suppliers_file,contractors_file,specifications_file,searchrecord_file, consultants_file;
    
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
    @FXML void button_consultantsOnClick(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, consultants_file);
    }
    @FXML void specificationsClicked(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, specifications_file);
    }
    @FXML void searchRecordsOnClick(ActionEvent event)
    {
        SectionsManager.showPane(anchorpane_main, searchrecord_file);
    }
    
    @FXML void button_upload(ActionEvent event)
    {
        if(anchorpane_main.getChildren().contains(client_file))
        {
            HashMap<String, TextField> clientCategoryTextFields = GetOtherControllerAttributesSingleton.getInstance().clientGetTextFields();
            HashMap<String, ComboBox> clientCategoryComboBox = GetOtherControllerAttributesSingleton.getInstance().clientGetComboBox();
            HashMap<String, ListView> clientCategoryListView = GetOtherControllerAttributesSingleton.getInstance().clientGetListView();
            clientEntity.setRepresentative(clientCategoryTextFields.get("Representative").getText().toUpperCase());
            clientEntity.setPosition(clientCategoryTextFields.get("Position").getText().toUpperCase());
            clientEntity.setCompany_Name(clientCategoryTextFields.get("Company Name").getText().toUpperCase());
            clientEntity.setIndustry(clientCategoryComboBox.get("Industry").getSelectionModel().getSelectedItem().toString().toUpperCase());
            clientEntity.setType(clientCategoryComboBox.get("Type").getSelectionModel().getSelectedItem().toString().toUpperCase());
            if(clientCategoryListView.get("Files").getItems().size() > 0)
            {
                ArrayList<File> files = new ArrayList<>();
                for(int counter = 0; counter < clientCategoryListView.get("Files").getItems().size(); counter++)
                {
                    String path = clientCategoryListView.get("Files").getItems().get(counter).toString();
                    files.add(new File(path));
                }
                clientEntity.setFileToUpload(files);
            }
            ClientUpload clientUpload = new ClientUpload(clientEntity);
            clientUpload.upload();
        }
        else if(anchorpane_main.getChildren().contains(suppliers_file))
        {
            HashMap<String, TextField> suppliersCategoryTextFields = GetOtherControllerAttributesSingleton.getInstance().supplierGetTextFields();
            HashMap<String, ComboBox> suppliersCategoryComboBox = GetOtherControllerAttributesSingleton.getInstance().supplierGetCombobox();
            HashMap<String, ListView> suppliersCategoryListView = GetOtherControllerAttributesSingleton.getInstance().supplierGetListView();
            suppliersEntity.setRepresentative(suppliersCategoryTextFields.get("Representative").getText().toUpperCase());
            suppliersEntity.setPosition(suppliersCategoryTextFields.get("Position").getText().toUpperCase());
            suppliersEntity.setCompany_Name(suppliersCategoryTextFields.get("Company Name").getText().toUpperCase());
            suppliersEntity.setBrand(suppliersCategoryTextFields.get("Brand").getText().toUpperCase());
            suppliersEntity.setIndustry(suppliersCategoryComboBox.get("Industry").getSelectionModel().getSelectedItem().toString().toUpperCase());
            suppliersEntity.setType(suppliersCategoryComboBox.get("Type").getSelectionModel().getSelectedItem().toString().toUpperCase());
            if(suppliersCategoryListView.get("Files").getItems().size() > 0)
            {
                ArrayList<File> files = new ArrayList<>();
                for(int counter = 0; counter < suppliersCategoryListView.get("Files").getItems().size(); counter++)
                {
                    String path = suppliersCategoryListView.get("Files").getItems().get(counter).toString();
                    files.add(new File(path));
                }
                suppliersEntity.setFileToUpload(files);
            }
            SuppliersUpload supplierUpload = new SuppliersUpload(suppliersEntity);
            supplierUpload.upload();
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
        LocalStorage.getInstance().initialize_local_ComboboxData();
        GetOtherControllerAttributesSingleton.getInstance().previewSetContainer(anchorpane_viewdocument);
    }    
}
