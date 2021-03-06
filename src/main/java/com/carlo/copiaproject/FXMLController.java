package com.carlo.copiaproject;

import DatabaseOperations.*;
import Entities.*;
import MiscellaneousClasses.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

public class FXMLController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    PreviewImage previewimage = new PreviewImage();
    ClientEntity clientEntity = new ClientEntity();
    SuppliersEntity suppliersEntity = new SuppliersEntity();
    ContractorsEntity contractorsEntity = new ContractorsEntity();
    ConsultantsEntity consultantsEntity = new ConsultantsEntity();
    SpecificationsEntity specificationsEntity = new SpecificationsEntity();
  
    @FXML private Button button_search, button_upload_id;
    @FXML private AnchorPane anchorpane_main,anchorpane_viewdocument;
    @FXML private Parent client_file,suppliers_file,contractors_file,specifications_file,searchrecord_file, consultants_file;
    @FXML private ProgressIndicator progress_indicator;
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
            HashMap<String, TextArea> clientCategoryTextArea = GetOtherControllerAttributesSingleton.getInstance().clientGetTextArea();
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
            if(!clientCategoryTextArea.get("Remarks").getText().trim().isEmpty())
            {
                clientEntity.setNotes(MyUtils.getInstance().extractNotes(clientCategoryTextArea.get("Remarks").getText().trim().toUpperCase()));
            }
            TaskExecute.getInstance().clientInsertRecord(clientEntity, "Client", "ClientPointer");
            button_upload_id.disableProperty().unbind();
            progress_indicator.visibleProperty().unbind();
            button_upload_id.disableProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            progress_indicator.visibleProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    JOptionPane.showMessageDialog(null, "Record(s) Added");
                }
            });
        }
        else if(anchorpane_main.getChildren().contains(suppliers_file))
        {
            HashMap<String, TextField> suppliersCategoryTextFields = GetOtherControllerAttributesSingleton.getInstance().supplierGetTextFields();
            HashMap<String, ComboBox> suppliersCategoryComboBox = GetOtherControllerAttributesSingleton.getInstance().supplierGetCombobox();
            HashMap<String, ListView> suppliersCategoryListView = GetOtherControllerAttributesSingleton.getInstance().supplierGetListView();
            HashMap<String, TextArea> suppliersCategoryTextArea = GetOtherControllerAttributesSingleton.getInstance().supplierGetTextArea();
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
            if(!suppliersCategoryTextArea.get("Remarks").getText().trim().isEmpty())
            {
                suppliersEntity.setNotes(MyUtils.getInstance().extractNotes(suppliersCategoryTextArea.get("Remarks").getText().trim().toUpperCase()));
            }
            TaskExecute.getInstance().suppliersInsertRecord(suppliersEntity, "Suppliers","SuppliersPointer");
            button_upload_id.disableProperty().unbind();
            progress_indicator.visibleProperty().unbind();
            button_upload_id.disableProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            progress_indicator.visibleProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    JOptionPane.showMessageDialog(null, "Record(s) Added");
                    
                }
            });
        }
        else if(anchorpane_main.getChildren().contains(contractors_file))
        {
            HashMap<String, TextField> contractorsCategoryTextFields = GetOtherControllerAttributesSingleton.getInstance().contractorsGetTextFields();
            HashMap<String, ComboBox> contractorsCategoryComboBox = GetOtherControllerAttributesSingleton.getInstance().contractorsGetCombobox();
            HashMap<String, ListView> contractorsCategoryListView = GetOtherControllerAttributesSingleton.getInstance().contractorsGetListView();
            HashMap<String, TextArea> contractorsCategoryTextArea = GetOtherControllerAttributesSingleton.getInstance().contractorsGetTextArea();
            contractorsEntity.setRepresentative(contractorsCategoryTextFields.get("Representative").getText().toUpperCase());
            contractorsEntity.setPosition(contractorsCategoryTextFields.get("Position").getText().toUpperCase());
            contractorsEntity.setCompanyName(contractorsCategoryTextFields.get("Company").getText().toUpperCase());
            contractorsEntity.setSpecialization(contractorsCategoryTextFields.get("Specialization").getText().toUpperCase());
            contractorsEntity.setIndustry(contractorsCategoryComboBox.get("Industry").getSelectionModel().getSelectedItem().toString().toUpperCase());
            contractorsEntity.setClassification(contractorsCategoryComboBox.get("Classification").getSelectionModel().getSelectedItem().toString().toUpperCase());
            if(contractorsCategoryListView.get("Files").getItems().size() > 0)
            {
                ArrayList<File> files = new ArrayList<>();
                for(int counter = 0; counter < contractorsCategoryListView.get("Files").getItems().size(); counter++)
                {
                    String path = contractorsCategoryListView.get("Files").getItems().get(counter).toString();
                    files.add(new File(path));
                }
                contractorsEntity.setFileToUpload(files);
            }
            if(!contractorsCategoryTextArea.get("Remarks").getText().trim().isEmpty())
            {
                contractorsEntity.setNotes(MyUtils.getInstance().extractNotes(contractorsCategoryTextArea.get("Remarks").getText().trim().toUpperCase()));
            }
            TaskExecute.getInstance().contractorsInsertRecord(contractorsEntity, "Contractors", "ContractorsPointer");
            button_upload_id.disableProperty().unbind();
            progress_indicator.visibleProperty().unbind();
            button_upload_id.disableProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            progress_indicator.visibleProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    JOptionPane.showMessageDialog(null, "Record(s) Added");
                }
            });
        }
        else if(anchorpane_main.getChildren().contains(consultants_file))
        {
            HashMap<String, TextField> consultantsCategoryTextFields = GetOtherControllerAttributesSingleton.getInstance().consultantsGetTextFields();
            HashMap<String, ComboBox> consultantsCategoryComboBox = GetOtherControllerAttributesSingleton.getInstance().consultantsGetCombobox();
            HashMap<String, ListView> consultantsCategoryListView = GetOtherControllerAttributesSingleton.getInstance().consultantsGetListView();
            HashMap<String, TextArea> consultantsCategoryTextArea = GetOtherControllerAttributesSingleton.getInstance().consultantsGetTextArea();
            consultantsEntity.setRepresentative(consultantsCategoryTextFields.get("Representative").getText().toUpperCase());
            consultantsEntity.setPosition(consultantsCategoryTextFields.get("Position").getText().toUpperCase());
            consultantsEntity.setCompanyName(consultantsCategoryTextFields.get("Company Name").getText().toUpperCase());
            consultantsEntity.setSpecialization(consultantsCategoryTextFields.get("Specialization").getText().toUpperCase());
            consultantsEntity.setIndustry(consultantsCategoryComboBox.get("Industry").getSelectionModel().getSelectedItem().toString().toUpperCase());
            consultantsEntity.setClassification(consultantsCategoryComboBox.get("Classification").getSelectionModel().getSelectedItem().toString().toUpperCase());
            if(consultantsCategoryListView.get("Files").getItems().size() > 0)
            {
                ArrayList<File> files = new ArrayList<>();
                for(int counter = 0; counter < consultantsCategoryListView.get("Files").getItems().size(); counter++)
                {
                    String path = consultantsCategoryListView.get("Files").getItems().get(counter).toString();
                    files.add(new File(path));
                }
                consultantsEntity.setFileToUpload(files);
            }
            if(!consultantsCategoryTextArea.get("Remarks").getText().trim().isEmpty())
            {
                consultantsEntity.setNotes(MyUtils.getInstance().extractNotes(consultantsCategoryTextArea.get("Remarks").getText().trim().toUpperCase()));
            }
            TaskExecute.getInstance().consultantsInsertRecord(consultantsEntity, "Consultants", "ConsultantsPointer");
            button_upload_id.disableProperty().unbind();
            progress_indicator.visibleProperty().unbind();
            button_upload_id.disableProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            progress_indicator.visibleProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    JOptionPane.showMessageDialog(null, "Record(s) Added");
                }
            });
        }
        else if(anchorpane_main.getChildren().contains(specifications_file))
        {
            HashMap<String, TextField> fields = GetOtherControllerAttributesSingleton.getInstance().specificationsGetTextFields();
            HashMap<String, TextArea> textArea = GetOtherControllerAttributesSingleton.getInstance().specificationsGetTextArea();
            HashMap<String, ListView> listView = GetOtherControllerAttributesSingleton.getInstance().specificationsGetListView();
            HashMap<String, TextArea> remarks = GetOtherControllerAttributesSingleton.getInstance().specificationsGetRemarks();
            specificationsEntity.setTitle(fields.get("Document").getText().trim().toUpperCase());
            specificationsEntity.setDivision(fields.get("Division").getText().trim().toUpperCase());
            specificationsEntity.setSection(fields.get("Section").getText().trim().toUpperCase());
            specificationsEntity.setType(fields.get("Type").getText().trim().toUpperCase());
            specificationsEntity.setKeywords(textArea.get("Keyword").getText().trim().toUpperCase());
            if(listView.get("Files").getItems().size() > 0)
            {
                ArrayList<File> files = new ArrayList<>();
                for(int counter = 0; counter < listView.get("Files").getItems().size(); counter++)
                {
                    String path = listView.get("Files").getItems().get(counter).toString();
                    files.add(new File(path));
                }
                specificationsEntity.setFileToUpload(files);
            }
            if(!remarks.get("Remarks").getText().trim().isEmpty())
            {
                specificationsEntity.setNotes(MyUtils.getInstance().extractNotes(remarks.get("Remarks").getText().trim().toUpperCase()));
            }
            TaskExecute.getInstance().specificationsInsertRecord(specificationsEntity, "Specifications", "SpecificationsPointer");
            button_upload_id.disableProperty().unbind();
            progress_indicator.visibleProperty().unbind();
            button_upload_id.disableProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            progress_indicator.visibleProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    JOptionPane.showMessageDialog(null, "Record(s) Added");
                }
            });
            /*SpecificationsUpload specificationsUpload = new SpecificationsUpload(specificationsEntity);
            specificationsUpload.upload();*/
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
        ArrayList<String> constants = LocalStorage.getInstance().retrieve_local_Categories_CONSTANTS();
        if(constants.size() <= 0)
        {
            LocalStorage.getInstance().insert_constants();
        }
        GetOtherControllerAttributesSingleton.getInstance().previewSetContainer(anchorpane_viewdocument);
    }    
}
