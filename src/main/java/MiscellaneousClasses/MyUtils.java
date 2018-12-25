
package MiscellaneousClasses;

import DatabaseOperations.LocalStorage;
import Entities.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyUtils 
{
    private MyUtils(){}
    private static MyUtils instance = null;
    public static MyUtils getInstance()
    {
        if(instance == null)
            instance = new MyUtils();
        return instance;
    }
    public static String APP_ID = "4GCD5XK7GucFbTKnJa0fonFEBlAh3azBS3Gh0NNd";
    public static String REST_API_KEY = "RYznH1yrJ3DVly2f02aEMkZJNwmPVdDBUQyqRT6H";
    public static String IRREVOCABLE_SESSION = "1";
    public static String URL_FILE = "https://concipiotektura.back4app.io/files/";
    public static String URL = "https://concipiotektura.back4app.io/classes/";
    public static String URL_BASE = "https://concipiotektura.back4app.io/";
    public static boolean REMEMBER_PASSWORD = false;
    public ArrayList<String> client_extractStringsToTags(ClientEntity clientEntity)
    {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(clientEntity.getRepresentative());
        tags.add(clientEntity.getPosition());
        tags.add(clientEntity.getCompany_Name());
        tags.add(clientEntity.getIndustry());
        tags.add(clientEntity.getType());
        String[] representativeSplit = clientEntity.getRepresentative().split("\\s+");
        String[] positionSplit = clientEntity.getPosition().split("\\s+");
        String[] companySplit = clientEntity.getCompany_Name().split("\\s+");
        for(String values : representativeSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : positionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : companySplit)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
    
    public ArrayList<String> suppliers_extractStringsToTags(SuppliersEntity suppliersEntity)
    {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(suppliersEntity.getRepresentative());
        tags.add(suppliersEntity.getPosition());
        tags.add(suppliersEntity.getCompany_Name());
        tags.add(suppliersEntity.getBrand());
        tags.add(suppliersEntity.getIndustry());
        tags.add(suppliersEntity.getType());
        String[] representativeSplit = suppliersEntity.getRepresentative().split("\\s+");
        String[] positionSplit = suppliersEntity.getPosition().split("\\s+");
        String[] companySplit = suppliersEntity.getCompany_Name().split("\\s+");
        String[] brand = suppliersEntity.getBrand().split("\\s+");
        for(String values : representativeSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : positionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : companySplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : brand)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
    
    public ArrayList<String> contractors_extractStringsToTags(ContractorsEntity contractorsEntity)
    {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(contractorsEntity.getRepresentative());
        tags.add(contractorsEntity.getPosition());
        tags.add(contractorsEntity.getCompanyName());
        tags.add(contractorsEntity.getSpecialization());
        tags.add(contractorsEntity.getIndustry());
        tags.add(contractorsEntity.getClassification());
        String[] representativeSplit = contractorsEntity.getRepresentative().split("\\s+");
        String[] positionSplit = contractorsEntity.getPosition().split("\\s+");
        String[] companySplit = contractorsEntity.getCompanyName().split("\\s+");
        String[] specialization = contractorsEntity.getSpecialization().split("\\s+");
        for(String values : representativeSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : positionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : companySplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : specialization)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
    public ArrayList<String> consultants_extractStringsToTags(ConsultantsEntity consultantsEntity)
    {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(consultantsEntity.getRepresentative());
        tags.add(consultantsEntity.getPosition());
        tags.add(consultantsEntity.getCompanyName());
        tags.add(consultantsEntity.getSpecialization());
        tags.add(consultantsEntity.getIndustry());
        tags.add(consultantsEntity.getClassification());
        String[] representativeSplit = consultantsEntity.getRepresentative().split("\\s+");
        String[] positionSplit = consultantsEntity.getPosition().split("\\s+");
        String[] companySplit = consultantsEntity.getCompanyName().split("\\s+");
        String[] specialization = consultantsEntity.getSpecialization().split("\\s+");
        for(String values : representativeSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : positionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : companySplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : specialization)
        {
            tags.add(values.toUpperCase());
        }
        return tags;
    }
    
    public ArrayList<String> speicifications_extractStringsToTags(SpecificationsEntity specificationsEntity)
    {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(specificationsEntity.getTitle());
        tags.add(specificationsEntity.getDivision());
        tags.add(specificationsEntity.getSection());
        tags.add(specificationsEntity.getType());
        tags.add(specificationsEntity.getKeywords());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(specificationsEntity.getDivision().trim());
        stringBuilder.append("-");
        stringBuilder.append(specificationsEntity.getSection().trim());
        stringBuilder.append("-");
        stringBuilder.append(specificationsEntity.getType().trim());
        tags.add(stringBuilder.toString());
        String[] titleSplit = specificationsEntity.getTitle().split("\\s+");
        String[] divisionSplit = specificationsEntity.getDivision().split("\\s+");
        String[] sectionSplit = specificationsEntity.getSection().split("\\s+");
        String[] typeSplit = specificationsEntity.getType().split("\\s+");
        if(specificationsEntity.getKeywords() != null)
        {
            String[] keywordSplit = specificationsEntity.getKeywords().split(",");
            for(String values : titleSplit)
            {
                tags.add(values.toUpperCase());
            }
            for(String values : keywordSplit)
            {
                tags.add(values.toUpperCase());
            }
        }
        for(String values : divisionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : sectionSplit)
        {
            tags.add(values.toUpperCase());
        }
        for(String values : typeSplit)
        {
            tags.add(values.toUpperCase());
        }
        
        return tags;
    }
    public String getFileType(String filePath)
    {
        String type = "";
        String extension = FilenameUtils.getExtension(filePath).toLowerCase();
        if(extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("jpeg"))
        {
            type = "Image";
        }
        else if(extension.equalsIgnoreCase("pdf"))
        {
            type = "pdf";
        }
        return type;
    }
    public ArrayList<NotesEntity> extractNotes(String raw_notes)
    {
        String[] notes = raw_notes.split(",");
        ArrayList<NotesEntity> result = new ArrayList<>();
        for(String note : notes)
        {
            NotesEntity notesEntity = new NotesEntity();
            notesEntity.setRemarks(note);
            result.add(notesEntity);
        }
        return result;
    }
    public String buildBody(String url, String name, String fileName, String pointerClass, String pointer, String objectId)
    {
        JSONObject json = new JSONObject();
        JSONObject pointerField = new JSONObject();
        JSONObject file = new JSONObject();
        pointerField.put("__type", "Pointer");
        pointerField.put("className", pointerClass);
        pointerField.put("objectId", objectId);
        file.put("__type", "File");
        file.put("url", url);
        file.put("name", name);
        json.put("Name", fileName);
        json.put("Files", file);
        json.put(pointer, pointerField);
        
        return json.toString();
    }
    public String buildBodyNoteClass(String remark, String searchClass, String pointerName, String objectId)
    {
        JSONObject json = new JSONObject();
        JSONObject pointer = new JSONObject();
        pointer.put("__type", "Pointer");
        pointer.put("className", searchClass);
        pointer.put("objectId", objectId);
        json.put("Remark", remark.trim().toUpperCase());
        json.put(pointerName, pointer);
        return json.toString();
    }
    public void initializeTable(String searchClass, TableView tableview_searchinrecord)
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
            
            representative.setCellFactory(TextFieldTableCell.forTableColumn());
            position.setCellFactory(TextFieldTableCell.forTableColumn());
            company.setCellFactory(TextFieldTableCell.forTableColumn());
            industry.setCellFactory(ComboBoxTableCell.forTableColumn(LocalStorage.getInstance().retrieve_combobox("CLIENT", "INDUSTRY")));
            type.setCellFactory(ComboBoxTableCell.forTableColumn(LocalStorage.getInstance().retrieve_combobox("CLIENT", "TYPE")));
            
            representative.setOnEditCommit(EventHandlers.getInstance().clientTableHandler());
            position.setOnEditCommit(EventHandlers.getInstance().clientTableHandler());
            company.setOnEditCommit(EventHandlers.getInstance().clientTableHandler());
            industry.setOnEditCommit(EventHandlers.getInstance().clientTableHandler());
            type.setOnEditCommit(EventHandlers.getInstance().clientTableHandler());
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
            
            representative.setCellFactory(TextFieldTableCell.forTableColumn());
            position.setCellFactory(TextFieldTableCell.forTableColumn());
            company.setCellFactory(TextFieldTableCell.forTableColumn());
            brand.setCellFactory(TextFieldTableCell.forTableColumn());
            industry.setCellFactory(ComboBoxTableCell.forTableColumn(LocalStorage.getInstance().retrieve_combobox("SUPPLIERS", "INDUSTRY")));
            type.setCellFactory(ComboBoxTableCell.forTableColumn(LocalStorage.getInstance().retrieve_combobox("SUPPLIERS", "TYPE")));
            
            representative.setOnEditCommit(EventHandlers.getInstance().suppliersTableHandler());
            position.setOnEditCommit(EventHandlers.getInstance().suppliersTableHandler());
            company.setOnEditCommit(EventHandlers.getInstance().suppliersTableHandler());
            brand.setOnEditCommit(EventHandlers.getInstance().suppliersTableHandler());
            industry.setOnEditCommit(EventHandlers.getInstance().suppliersTableHandler());
            type.setOnEditCommit(EventHandlers.getInstance().suppliersTableHandler());
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
            
            representative.setCellFactory(TextFieldTableCell.forTableColumn());
            position.setCellFactory(TextFieldTableCell.forTableColumn());
            company.setCellFactory(TextFieldTableCell.forTableColumn());
            specialization.setCellFactory(TextFieldTableCell.forTableColumn());
            industry.setCellFactory(ComboBoxTableCell.forTableColumn(LocalStorage.getInstance().retrieve_combobox("CONTRACTORS", "INDUSTRY")));
            classification.setCellFactory(ComboBoxTableCell.forTableColumn(LocalStorage.getInstance().retrieve_combobox("CONTRACTORS", "CLASSIFICATION")));
            
            representative.setOnEditCommit(EventHandlers.getInstance().contractorsTableHandler());
            position.setOnEditCommit(EventHandlers.getInstance().contractorsTableHandler());
            company.setOnEditCommit(EventHandlers.getInstance().contractorsTableHandler());
            specialization.setOnEditCommit(EventHandlers.getInstance().contractorsTableHandler());
            industry.setOnEditCommit(EventHandlers.getInstance().contractorsTableHandler());
            classification.setOnEditCommit(EventHandlers.getInstance().contractorsTableHandler());
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
            
            representative.setCellFactory(TextFieldTableCell.forTableColumn());
            position.setCellFactory(TextFieldTableCell.forTableColumn());
            company.setCellFactory(TextFieldTableCell.forTableColumn());
            specialization.setCellFactory(TextFieldTableCell.forTableColumn());
            industry.setCellFactory(ComboBoxTableCell.forTableColumn(LocalStorage.getInstance().retrieve_combobox("CONSULTANTS", "INDUSTRY")));
            classification.setCellFactory(ComboBoxTableCell.forTableColumn(LocalStorage.getInstance().retrieve_combobox("CONSULTANTS", "CLASSIFICATION")));
            
            representative.setOnEditCommit(EventHandlers.getInstance().consultantsTableHandler());
            position.setOnEditCommit(EventHandlers.getInstance().consultantsTableHandler());
            company.setOnEditCommit(EventHandlers.getInstance().consultantsTableHandler());
            specialization.setOnEditCommit(EventHandlers.getInstance().consultantsTableHandler());
            industry.setOnEditCommit(EventHandlers.getInstance().consultantsTableHandler());
            classification.setOnEditCommit(EventHandlers.getInstance().consultantsTableHandler());
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
            
            title.setCellFactory(TextFieldTableCell.forTableColumn());
            division.setCellFactory(TextFieldTableCell.forTableColumn());
            section.setCellFactory(TextFieldTableCell.forTableColumn());
            type.setCellFactory(TextFieldTableCell.forTableColumn());
            
            title.setOnEditCommit(EventHandlers.getInstance().specificationsTableHandler());
            division.setOnEditCommit(EventHandlers.getInstance().specificationsTableHandler());
            section.setOnEditCommit(EventHandlers.getInstance().specificationsTableHandler());
            type.setOnEditCommit(EventHandlers.getInstance().specificationsTableHandler());
        }
        
    }
    public void openNewWindow(String fileName, String title)
    {
        try 
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/" + fileName));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(root1));            
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
            {
                @Override
                public void handle(WindowEvent event) 
                {
                    if(fileName == "LoginRegister.fxml")
                    {
                       Platform.exit();
                        System.exit(0); 
                    }
                }
            });
            stage.show();
        } 
        catch (IOException iOException) {iOException.printStackTrace();}
    }
    public void bindBtn(Button update, Button delete,Button showremarks,Button showfiles, ReadOnlyObjectProperty property)
    {
        update.disableProperty().unbind();
        delete.disableProperty().unbind();
        showremarks.disableProperty().unbind();
        showfiles.disableProperty().unbind();
        update.disableProperty().bind(property.isNull());
        delete.disableProperty().bind(property.isNull());
        showremarks.disableProperty().bind(property.isNull());
        showfiles.disableProperty().bind(property.isNull());
    }
    public void bindSearchNProgress(Button search, ProgressIndicator progressIndicator,ReadOnlyBooleanProperty property)
    {
        search.disableProperty().unbind();
        progressIndicator.visibleProperty().unbind();
        search.disableProperty().bind(property);
        progressIndicator.visibleProperty().bind(property);
    }
    
}
