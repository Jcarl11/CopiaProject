/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carlo.copiaproject;

import DatabaseOperations.RetrieveAssociatedFiles;
import DatabaseOperations.RetrieveValues;
import Entities.ClientEntity;
import MiscellaneousClasses.*;
import java.net.URL;
import java.text.Collator;
import java.util.*;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Windows
 */
public class SearchRecordsController implements Initializable 
{
    DatabaseQuery dbQuery = new DatabaseQuery();
    @FXML private ListView<String> listview_searchrecord_fileshowcase;
    @FXML private TableView<ClientEntity> tableview_searchinrecord;
    @FXML private ComboBox<String> combobox_searchrecords_searchin;
    @FXML private TextField textfield_searchrecords_keyword;
    
    @FXML
    void button_searchrecords_showfilesOnClick(ActionEvent event)
    {
        listview_searchrecord_fileshowcase.getItems().clear();
        String objId = tableview_searchinrecord.getSelectionModel().getSelectedItem().getObjectID();
        //dbQuery.RetrieveAssociatedFiles(objId, listview_searchrecord_fileshowcase);
        RetrieveAssociatedFiles raf = new RetrieveAssociatedFiles(objId);
        Thread thread = new Thread(raf);
        thread.start();
        try{thread.join();}catch(Exception ex){ex.printStackTrace();}
        ArrayList<String> result = raf.getResult();
        if(result.size() > 0)
        {
            listview_searchrecord_fileshowcase.getItems().clear();
            listview_searchrecord_fileshowcase.getItems().addAll(result);
        }
        else
        {
            listview_searchrecord_fileshowcase.getItems().clear();
            JOptionPane.showMessageDialog(null, "No Files found");
        }
    }
    
    @FXML
    void button_searchrecords_search(ActionEvent event)
    {
        ArrayList<ClientEntity> clientEntityList = new ArrayList<>();
        String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem();
        String search = textfield_searchrecords_keyword.getText().trim();
        RetrieveValues ret = new RetrieveValues(search, searchIn);
        Thread thread = new Thread(ret);
        thread.start();
        try{thread.join();}catch(Exception ex){ex.printStackTrace();}
        clientEntityList = ret.getResult();
        if(tableview_searchinrecord.getColumns().isEmpty())
        {
            initializeTable();
            tableview_searchinrecord.getItems().addAll(clientEntityList);
        }
        else
        {
            tableview_searchinrecord.getItems().clear();
            tableview_searchinrecord.getItems().addAll(clientEntityList);
        }
    }
    
    public void initializeTable()
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
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try
        {
            combobox_searchrecords_searchin.setItems(new SortedList<String>(dbQuery.RetrieveComboboxDataCategories(), Collator.getInstance()));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
         
    }    
    
}
