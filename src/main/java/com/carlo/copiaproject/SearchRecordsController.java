package com.carlo.copiaproject;

import DatabaseOperations.*;
import Entities.*;
import java.net.URL;
import java.text.Collator;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
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
    
    @FXML
    void button_searchrecords_showfilesOnClick(ActionEvent event)
    {
        listview_searchrecord_fileshowcase.getItems().clear();
        if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof ClientEntity)
        {
            ClientEntity ce = (ClientEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectID();
            RetrieveAssociatedFiles raf = new RetrieveAssociatedFiles(objId,"Client","ClientPointer","Images","PDFFiles");
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
        else if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof SuppliersEntity)
        {
            SuppliersEntity ce = (SuppliersEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectID();
            RetrieveAssociatedFiles raf = new RetrieveAssociatedFiles(objId,"Suppliers","SuppliersPointer","Images","PDFFiles");
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
        else if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof ContractorsEntity)
        {
            ContractorsEntity ce = (ContractorsEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectId();
            RetrieveAssociatedFiles raf = new RetrieveAssociatedFiles(objId,"Contractors","ContractorsPointer","Images","PDFFiles");
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
        else if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof ConsultantsEntity)
        {
            ConsultantsEntity ce = (ConsultantsEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectId();
            RetrieveAssociatedFiles raf = new RetrieveAssociatedFiles(objId,"Consultants","ConsultantsPointer","Images","PDFFiles");
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
        else if(tableview_searchinrecord.getSelectionModel().getSelectedItem() instanceof SpecificationsEntity)
        {
            SpecificationsEntity ce = (SpecificationsEntity) tableview_searchinrecord.getSelectionModel().getSelectedItem();
            String objId = ce.getObjectId();
            RetrieveAssociatedFiles raf = new RetrieveAssociatedFiles(objId,"Specifications","SpecificationsPointer","Images","Specifications_PDFFiles");
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
        
    }
    
    @FXML
    void button_searchrecords_search(ActionEvent event)
    {
        String searchClass = new String();
        ArrayList<ClientEntity> clientEntityList = new ArrayList<>();
        ArrayList<SuppliersEntity> suppliersEntity = new ArrayList<>();
        ArrayList<ContractorsEntity> contractorsEntitys = new ArrayList<>();
        ArrayList<ConsultantsEntity> consultantsEntitys = new ArrayList<>();
        ArrayList<SpecificationsEntity> specificationsEntitys = new ArrayList<>();
        if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("client"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            RetrieveValuesClient ret = new RetrieveValuesClient(search, output);
            Thread thread = new Thread(ret);
            thread.start();
            try{thread.join();}catch(Exception ex){ex.printStackTrace();}
            clientEntityList = ret.getResult();
            if(tableview_searchinrecord.getColumns().isEmpty())
            {
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(clientEntityList);
            }
            else
            {
                tableview_searchinrecord.getItems().clear();
                tableview_searchinrecord.getColumns().clear();
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(clientEntityList);
            }
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("suppliers"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            RetrieveValuesSuppliers ret = new RetrieveValuesSuppliers(search, output);
            Thread thread = new Thread(ret);
            thread.start();
            try{thread.join();}catch(Exception ex){ex.printStackTrace();}
            suppliersEntity = ret.getResult();
            if(tableview_searchinrecord.getColumns().isEmpty())
            {
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(suppliersEntity);
            }
            else
            {
                tableview_searchinrecord.getItems().clear();
                tableview_searchinrecord.getColumns().clear();
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(suppliersEntity);
            }
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("contractors"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            RetrieveValuesContractors ret = new RetrieveValuesContractors(search, output);
            Thread thread = new Thread(ret);
            thread.start();
            try{thread.join();}catch(Exception ex){ex.printStackTrace();}
            contractorsEntitys = ret.getResult();
            if(tableview_searchinrecord.getColumns().isEmpty())
            {
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(contractorsEntitys);
            }
            else
            {
                tableview_searchinrecord.getItems().clear();
                tableview_searchinrecord.getColumns().clear();
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(contractorsEntitys);
            }
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("consultants"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            RetrieveValuesConsultants ret = new RetrieveValuesConsultants(search, output);
            Thread thread = new Thread(ret);
            thread.start();
            try{thread.join();}catch(Exception ex){ex.printStackTrace();}
            consultantsEntitys = ret.getResult();
            if(tableview_searchinrecord.getColumns().isEmpty())
            {
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(consultantsEntitys);
            }
            else
            {
                tableview_searchinrecord.getItems().clear();
                tableview_searchinrecord.getColumns().clear();
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(consultantsEntitys);
            }
        }
        else if(combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase().equalsIgnoreCase("specifications"))
        {
            searchClass = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String searchIn = combobox_searchrecords_searchin.getSelectionModel().getSelectedItem().toLowerCase();
            String output = searchIn.substring(0, 1).toUpperCase() + searchIn.substring(1);
            String search = textfield_searchrecords_keyword.getText().trim();
            RetrieveValuesSpecifications ret = new RetrieveValuesSpecifications(search, output);
            Thread thread = new Thread(ret);
            thread.start();
            try{thread.join();}catch(Exception ex){ex.printStackTrace();}
            specificationsEntitys = ret.getResult();
            if(tableview_searchinrecord.getColumns().isEmpty())
            {
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(specificationsEntitys);
            }
            else
            {
                tableview_searchinrecord.getItems().clear();
                tableview_searchinrecord.getColumns().clear();
                initializeTable(searchClass);
                tableview_searchinrecord.getItems().addAll(specificationsEntitys);
            }
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
