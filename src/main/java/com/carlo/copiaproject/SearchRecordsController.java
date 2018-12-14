package com.carlo.copiaproject;

import DatabaseOperations.*;
import Entities.*;
import java.net.URL;
import java.text.Collator;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Joey Francisco
 */
public class SearchRecordsController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    @FXML private ListView<String> listview_searchrecord_fileshowcase;
    @FXML private TableView tableview_searchinrecord;
    @FXML private ComboBox<String> combobox_searchrecords_searchin;
    @FXML private TextField textfield_searchrecords_keyword;
    @FXML private ProgressIndicator searchpage_progress;
    @FXML private Button searchrecords_searchbutton,button_searchinrecord_showfiles;
    @FXML private ProgressIndicator showFile_progress;
    
    @FXML
    void button_searchrecords_showfilesOnClick(ActionEvent event)
    {
        listview_searchrecord_fileshowcase.getItems().clear();
        if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof ClientEntity)
        {
            ClientEntity ce = (ClientEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectID();
            RetrieveFileTask.getInstance().setId(objId);
            RetrieveFileTask.getInstance().setSearchClass("Client");
            RetrieveFileTask.getInstance().setField("ClientPointer");
            RetrieveFileTask.getInstance().setImageClass("Images");
            RetrieveFileTask.getInstance().setPdfClass("PDFFiles");
            RetrieveFileTask.getInstance().retrieveFile();
            showFile_progress.visibleProperty().unbind();
            button_searchinrecord_showfiles.disableProperty().unbind();
            showFile_progress.visibleProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            button_searchinrecord_showfiles.disableProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            
            RetrieveFileTask.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    if(RetrieveFileTask.getInstance().getTask().getValue().size() > 0)
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        listview_searchrecord_fileshowcase.getItems().addAll(RetrieveFileTask.getInstance().getTask().getValue());
                    }
                    else
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        JOptionPane.showMessageDialog(null, "No Files found");
                    }
                }
            });
        }
        else if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof SuppliersEntity)
        {
            SuppliersEntity ce = (SuppliersEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectID();
            RetrieveFileTask.getInstance().setId(objId);
            RetrieveFileTask.getInstance().setSearchClass("Suppliers");
            RetrieveFileTask.getInstance().setField("SuppliersPointer");
            RetrieveFileTask.getInstance().setImageClass("Images");
            RetrieveFileTask.getInstance().setPdfClass("PDFFiles");
            RetrieveFileTask.getInstance().retrieveFile();
            showFile_progress.visibleProperty().unbind();
            button_searchinrecord_showfiles.disableProperty().unbind();
            showFile_progress.visibleProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            button_searchinrecord_showfiles.disableProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            
            RetrieveFileTask.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    if(RetrieveFileTask.getInstance().getTask().getValue().size() > 0)
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        listview_searchrecord_fileshowcase.getItems().addAll(RetrieveFileTask.getInstance().getTask().getValue());
                    }
                    else
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        JOptionPane.showMessageDialog(null, "No Files found");
                    }
                }
            });
        }
        else if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof ContractorsEntity)
        {
            ContractorsEntity ce = (ContractorsEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectId();
            RetrieveFileTask.getInstance().setId(objId);
            RetrieveFileTask.getInstance().setSearchClass("Contractors");
            RetrieveFileTask.getInstance().setField("ContractorsPointer");
            RetrieveFileTask.getInstance().setImageClass("Images");
            RetrieveFileTask.getInstance().setPdfClass("PDFFiles");
            RetrieveFileTask.getInstance().retrieveFile();
            showFile_progress.visibleProperty().unbind();
            button_searchinrecord_showfiles.disableProperty().unbind();
            showFile_progress.visibleProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            button_searchinrecord_showfiles.disableProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            
            RetrieveFileTask.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    if(RetrieveFileTask.getInstance().getTask().getValue().size() > 0)
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        listview_searchrecord_fileshowcase.getItems().addAll(RetrieveFileTask.getInstance().getTask().getValue());
                    }
                    else
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        JOptionPane.showMessageDialog(null, "No Files found");
                    }
                }
            });
        }
        else if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof ConsultantsEntity)
        {
            ConsultantsEntity ce = (ConsultantsEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectId();
            RetrieveFileTask.getInstance().setId(objId);
            RetrieveFileTask.getInstance().setSearchClass("Consultants");
            RetrieveFileTask.getInstance().setField("ConsultantsPointer");
            RetrieveFileTask.getInstance().setImageClass("Images");
            RetrieveFileTask.getInstance().setPdfClass("PDFFiles");
            RetrieveFileTask.getInstance().retrieveFile();
            showFile_progress.visibleProperty().unbind();
            button_searchinrecord_showfiles.disableProperty().unbind();
            showFile_progress.visibleProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            button_searchinrecord_showfiles.disableProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            
            RetrieveFileTask.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    if(RetrieveFileTask.getInstance().getTask().getValue().size() > 0)
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        listview_searchrecord_fileshowcase.getItems().addAll(RetrieveFileTask.getInstance().getTask().getValue());
                    }
                    else
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        JOptionPane.showMessageDialog(null, "No Files found");
                    }
                }
            });
        }
        else if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof SpecificationsEntity)
        {
            SpecificationsEntity ce = (SpecificationsEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectId();
            RetrieveFileTask.getInstance().setId(objId);
            RetrieveFileTask.getInstance().setSearchClass("Specifications");
            RetrieveFileTask.getInstance().setField("SpecificationsPointer");
            RetrieveFileTask.getInstance().setImageClass("Images");
            RetrieveFileTask.getInstance().setPdfClass("PDFFiles");
            RetrieveFileTask.getInstance().retrieveFile();
            showFile_progress.visibleProperty().unbind();
            button_searchinrecord_showfiles.disableProperty().unbind();
            showFile_progress.visibleProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            button_searchinrecord_showfiles.disableProperty().bind(RetrieveFileTask.getInstance().getTask().runningProperty());
            
            RetrieveFileTask.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    if(RetrieveFileTask.getInstance().getTask().getValue().size() > 0)
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        listview_searchrecord_fileshowcase.getItems().addAll(RetrieveFileTask.getInstance().getTask().getValue());
                    }
                    else
                    {
                        listview_searchrecord_fileshowcase.getItems().clear();
                        JOptionPane.showMessageDialog(null, "No Files found");
                    }
                }
            });
        }
        
    }
    
    @FXML
    void button_searchrecords_search(ActionEvent event)
    {
        
        String searchClass = new String();
        if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("client"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            AlternateSearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            AlternateSearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<ClientEntity> clientEntityList = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    clientEntityList = (ArrayList<ClientEntity>) AlternateSearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(clientEntityList);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(clientEntityList);
                    }
                }
            });
            AlternateSearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getMessage());
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getException());
                }
            });
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("suppliers"))
        {
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            AlternateSearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            AlternateSearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<SuppliersEntity> suppliersEntitys = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println("Succeded");
                    suppliersEntitys = (ArrayList<SuppliersEntity>) AlternateSearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(suppliersEntitys);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(suppliersEntitys);
                    }
                }
            });
            AlternateSearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getMessage());
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getException());
                }
            });
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("contractors"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            AlternateSearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            AlternateSearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<ContractorsEntity> contractorsEntitys = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println("Succeded");
                    contractorsEntitys = (ArrayList<ContractorsEntity>) AlternateSearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(contractorsEntitys);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(contractorsEntitys);
                    }
                }
            });
            AlternateSearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getMessage());
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getException());
                }
            });
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("consultants"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            AlternateSearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            AlternateSearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<ConsultantsEntity> consultantsEntitys = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println("Succeded");
                    consultantsEntitys = (ArrayList<ConsultantsEntity>) AlternateSearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(consultantsEntitys);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(consultantsEntitys);
                    }
                }
            });
            AlternateSearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getMessage());
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getException());
                }
            });
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("specifications"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            AlternateSearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(AlternateSearchRecords.getInstance().getTask().runningProperty());
            AlternateSearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<SpecificationsEntity> specificationsEntitys = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println("Succeded");
                    specificationsEntitys = (ArrayList<SpecificationsEntity>) AlternateSearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(specificationsEntitys);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        initializeTable(searchClass1);
                        tableview_searchinrecord.getItems().addAll(specificationsEntitys);
                    }
                }
            });
            AlternateSearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getMessage());
                    System.out.println(AlternateSearchRecords.getInstance().getTask().getException());
                }
            });
        }
        
        
    }
    
    public void initializeTable(String searchClass)
    {
        if(searchClass.equalsIgnoreCase("Client"))
        {
            TableColumn objectid = new TableColumn("ObjectID");
            TableColumn representative = new TableColumn("Representative");
            TableColumn position = new TableColumn("Position");
            TableColumn company = new TableColumn("Company");
            TableColumn industry = new TableColumn("Industry");
            TableColumn type = new TableColumn("Type");
            tableview_searchinrecord.getColumns().add(objectid);
            tableview_searchinrecord.getColumns().add(representative);
            tableview_searchinrecord.getColumns().add(position);
            tableview_searchinrecord.getColumns().add(company);
            tableview_searchinrecord.getColumns().add(industry);
            tableview_searchinrecord.getColumns().add(type);
            objectid.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("ObjectID"));
            representative.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Representative"));
            position.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Position"));
            company.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Company_Name"));
            industry.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Industry"));
            type.setCellValueFactory(new PropertyValueFactory<ClientEntity, String>("Type"));
        }
        else if(searchClass.equalsIgnoreCase("Suppliers"))
        {
            TableColumn objectid = new TableColumn("ObjectID");
            TableColumn representative = new TableColumn("Representative");
            TableColumn position = new TableColumn("Position");
            TableColumn company = new TableColumn("Company");
            TableColumn brand = new TableColumn("Brand");
            TableColumn industry = new TableColumn("Industry");
            TableColumn type = new TableColumn("Type");
            tableview_searchinrecord.getColumns().add(objectid);
            tableview_searchinrecord.getColumns().add(representative);
            tableview_searchinrecord.getColumns().add(position);
            tableview_searchinrecord.getColumns().add(company);
            tableview_searchinrecord.getColumns().add(brand);
            tableview_searchinrecord.getColumns().add(industry);
            tableview_searchinrecord.getColumns().add(type);
            objectid.setCellValueFactory(new PropertyValueFactory<SuppliersEntity, String>("ObjectID"));
            representative.setCellValueFactory(new PropertyValueFactory<SuppliersEntity, String>("Representative"));
            position.setCellValueFactory(new PropertyValueFactory<SuppliersEntity, String>("Position"));
            company.setCellValueFactory(new PropertyValueFactory<SuppliersEntity, String>("Company_Name"));
            brand.setCellValueFactory(new PropertyValueFactory<SuppliersEntity, String>("Brand"));
            industry.setCellValueFactory(new PropertyValueFactory<SuppliersEntity, String>("Industry"));
            type.setCellValueFactory(new PropertyValueFactory<SuppliersEntity, String>("Type"));
        }
        else if(searchClass.equalsIgnoreCase("Contractors"))
        {
            TableColumn objectid = new TableColumn("ObjectID");
            TableColumn representative = new TableColumn("Representative");
            TableColumn position = new TableColumn("Position");
            TableColumn company = new TableColumn("Company");
            TableColumn specialization = new TableColumn("Specialization");
            TableColumn industry = new TableColumn("Industry");
            TableColumn classification = new TableColumn("Classification");
            tableview_searchinrecord.getColumns().add(objectid);
            tableview_searchinrecord.getColumns().add(representative);
            tableview_searchinrecord.getColumns().add(position);
            tableview_searchinrecord.getColumns().add(company);
            tableview_searchinrecord.getColumns().add(specialization);
            tableview_searchinrecord.getColumns().add(industry);
            tableview_searchinrecord.getColumns().add(classification);
            objectid.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("objectId"));
            representative.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("representative"));
            position.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("position"));
            company.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("companyName"));
            specialization.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("specialization"));
            industry.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("Industry"));
            classification.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("classification"));
        }
        else if(searchClass.equalsIgnoreCase("Consultants"))
        {
            TableColumn objectid = new TableColumn("ObjectID");
            TableColumn representative = new TableColumn("Representative");
            TableColumn position = new TableColumn("Position");
            TableColumn company = new TableColumn("Company");
            TableColumn specialization = new TableColumn("Specialization");
            TableColumn industry = new TableColumn("Industry");
            TableColumn classification = new TableColumn("Classification");
            tableview_searchinrecord.getColumns().add(objectid);
            tableview_searchinrecord.getColumns().add(representative);
            tableview_searchinrecord.getColumns().add(position);
            tableview_searchinrecord.getColumns().add(company);
            tableview_searchinrecord.getColumns().add(specialization);
            tableview_searchinrecord.getColumns().add(industry);
            tableview_searchinrecord.getColumns().add(classification);
            objectid.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("objectId"));
            representative.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("representative"));
            position.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("position"));
            company.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("companyName"));
            specialization.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("specialization"));
            industry.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("Industry"));
            classification.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("classification"));
        }
        else if(searchClass.equalsIgnoreCase("Specifications"))
        {
            TableColumn objectid = new TableColumn("ObjectID");
            TableColumn title = new TableColumn("Title");
            TableColumn division = new TableColumn("Division");
            TableColumn section = new TableColumn("Section");
            TableColumn type = new TableColumn("Type");
            tableview_searchinrecord.getColumns().add(objectid);
            tableview_searchinrecord.getColumns().add(title);
            tableview_searchinrecord.getColumns().add(division);
            tableview_searchinrecord.getColumns().add(section);
            tableview_searchinrecord.getColumns().add(type);
            objectid.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("objectId"));
            title.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("title"));
            division.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("division"));
            section.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("section"));
            type.setCellValueFactory(new PropertyValueFactory<ContractorsEntity, String>("type"));
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try
        {
            ObservableList<String> list = FXCollections.observableArrayList();
            for(String values : LocalStorage.getInstance().retrieve_local_Categories())
            {
                list.add(values);
            }
            combobox_searchrecords_searchin.setItems(new SortedList<String>(list, Collator.getInstance()));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
         
    }    
    
}
