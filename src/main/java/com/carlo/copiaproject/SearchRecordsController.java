package com.carlo.copiaproject;

import DatabaseOperations.*;
import Entities.*;
import MiscellaneousClasses.CustomCell;
import MiscellaneousClasses.EventHandlers;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import MiscellaneousClasses.MyUtils;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
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
    @FXML private Button searchrecords_searchbutton,button_searchinrecord_showfiles,searchrecords_button_update,searchrecords_button_delete;
    @FXML private ProgressIndicator showFile_progress;
    @FXML private ListView<NotesEntity> searchrecords_listview_notes;
    @FXML private Button searchrecords_showremarks;
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
        listview_searchrecord_fileshowcase.getItems().clear();
        searchrecords_listview_notes.getItems().clear();
        if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("client"))
        {
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            SearchRecords.getInstance().Search(search, output);
            MyUtils.getInstance().bindSearchNProgress(searchrecords_searchbutton, searchpage_progress, SearchRecords.getInstance().getTask().runningProperty());
            SearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<ClientEntity> clientEntityList = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    MyUtils.getInstance().bindBtn(searchrecords_button_update, searchrecords_button_delete,searchrecords_showremarks,button_searchinrecord_showfiles, tableview_searchinrecord.getSelectionModel().selectedItemProperty());
                    clientEntityList = (ArrayList<ClientEntity>) SearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(clientEntityList);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(clientEntityList);
                    }
                }
            });
            SearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(SearchRecords.getInstance().getTask().getMessage());
                    System.out.println(SearchRecords.getInstance().getTask().getException());
                }
            });
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("suppliers"))
        {
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            SearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(SearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(SearchRecords.getInstance().getTask().runningProperty());
            SearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<SuppliersEntity> suppliersEntitys = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println("Succeded");
                    suppliersEntitys = (ArrayList<SuppliersEntity>) SearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(suppliersEntitys);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(suppliersEntitys);
                    }
                }
            });
            SearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(SearchRecords.getInstance().getTask().getMessage());
                    System.out.println(SearchRecords.getInstance().getTask().getException());
                }
            });
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("contractors"))
        {
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            SearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(SearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(SearchRecords.getInstance().getTask().runningProperty());
            SearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<ContractorsEntity> contractorsEntitys = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println("Succeded");
                    contractorsEntitys = (ArrayList<ContractorsEntity>) SearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(contractorsEntitys);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(contractorsEntitys);
                    }
                }
            });
            SearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(SearchRecords.getInstance().getTask().getMessage());
                    System.out.println(SearchRecords.getInstance().getTask().getException());
                }
            });
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("consultants"))
        {
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            SearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(SearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(SearchRecords.getInstance().getTask().runningProperty());
            SearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<ConsultantsEntity> consultantsEntitys = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println("Succeded");
                    consultantsEntitys = (ArrayList<ConsultantsEntity>) SearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(consultantsEntitys);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(consultantsEntitys);
                    }
                }
            });
            SearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(SearchRecords.getInstance().getTask().getMessage());
                    System.out.println(SearchRecords.getInstance().getTask().getException());
                }
            });
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("specifications"))
        {
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            SearchRecords.getInstance().Search(search, output);
            searchpage_progress.visibleProperty().unbind();
            searchrecords_searchbutton.disableProperty().unbind();
            searchpage_progress.visibleProperty().bind(SearchRecords.getInstance().getTask().runningProperty());
            searchrecords_searchbutton.disableProperty().bind(SearchRecords.getInstance().getTask().runningProperty());
            SearchRecords.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                String searchClass1 = new String();
                ArrayList<SpecificationsEntity> specificationsEntitys = new ArrayList<>();
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println("Succeded");
                    specificationsEntitys = (ArrayList<SpecificationsEntity>) SearchRecords.getInstance().getTask().getValue();
                    searchClass1 = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
                    if(tableview_searchinrecord.getColumns().isEmpty())
                    {
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(specificationsEntitys);
                    }
                    else
                    {
                        tableview_searchinrecord.getItems().clear();
                        tableview_searchinrecord.getColumns().clear();
                        MyUtils.getInstance().initializeTable(searchClass1, tableview_searchinrecord);
                        tableview_searchinrecord.getItems().addAll(specificationsEntitys);
                    }
                }
            });
            SearchRecords.getInstance().getTask().setOnFailed(new EventHandler<WorkerStateEvent>()
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    System.out.println(SearchRecords.getInstance().getTask().getMessage());
                    System.out.println(SearchRecords.getInstance().getTask().getException());
                }
            });
        }
    }
    @FXML
    void button_searchrecords_showremarksOnClick(ActionEvent event) 
    {
        if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof ClientEntity)
        {
            ClientEntity clientEntity = (ClientEntity)tableview_searchinrecord.getSelectionModel().getSelectedItem();
            TaskExecute.getInstance().retrieveNotes("Client", clientEntity.getObjectID(), "ClientPointer");
            MyUtils.getInstance().bindSearchNProgress(searchrecords_showremarks, showFile_progress, TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(EventHandlers.getInstance().taskSetOnSucceededEvent(searchrecords_listview_notes, TaskExecute.getInstance().getTask()));
        } 
    }
    @FXML
    void searchrecords_edit(ActionEvent event) 
    {
        if(searchrecords_listview_notes.getSelectionModel().getSelectedItem() != null)
        {
            HashMap<String, ListView<NotesEntity>> listview = new HashMap<>();
            listview.put("ListViewNotes", searchrecords_listview_notes);
            GetOtherControllerAttributesSingleton.getInstance().setListviewNotes(listview);
            NotesEntity note = searchrecords_listview_notes.getSelectionModel().getSelectedItem();
            GetOtherControllerAttributesSingleton.getInstance().setNotes(note);
            MyUtils.getInstance().openNewWindow("EditNotes.fxml", "Edit Notes");
        }
    }
    @FXML
    void button_updateOnClick(ActionEvent event) 
    {
        int result = JOptionPane.showConfirmDialog(null, "This record will be updated", "Confirm Change", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.OK_OPTION)
        {
            TaskExecute.getInstance().updateRecord(EventHandlers.getInstance().getClient());
            showFile_progress.visibleProperty().unbind();
            searchrecords_button_update.disableProperty().unbind();
            showFile_progress.visibleProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            searchrecords_button_update.disableProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    JOptionPane.showMessageDialog(null, "1 RECORD UPDATED");
                }
            });
        }
        
    }
    @FXML
    void searchrecords_deleteOnClick(ActionEvent event) 
    {
        int result = JOptionPane.showConfirmDialog(null, "This selected record will be deleted", "Confirm Delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.OK_OPTION)
        {
            int selectedIndex = tableview_searchinrecord.getSelectionModel().getSelectedIndex();
            TaskExecute.getInstance().deleteRecord(((ClientEntity)tableview_searchinrecord.getSelectionModel().getSelectedItem()).getObjectID());
            showFile_progress.visibleProperty().unbind();
            searchrecords_button_delete.disableProperty().unbind();
            showFile_progress.visibleProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            searchrecords_button_delete.disableProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    HashMap<String, String> result = (HashMap<String, String>)TaskExecute.getInstance().getTask().getValue();
                    if(result.get("deleteRecord").equalsIgnoreCase("Successful"))
                    {
                        tableview_searchinrecord.getItems().remove(selectedIndex);
                        listview_searchrecord_fileshowcase.getItems().clear();
                        searchrecords_listview_notes.getItems().clear();
                        JOptionPane.showMessageDialog(null, "Record deleted successfully");
                    }
                }
            });
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
