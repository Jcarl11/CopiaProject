package MiscellaneousClasses;

import DatabaseOperations.TaskExecute;
import Entities.*;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import org.json.JSONObject;

public class EventHandlers 
{
    JSONObject jsonData;
    private EventHandlers(){}
    private static EventHandlers instance = null;
    public static EventHandlers getInstance()
    {
        if(instance == null)
            instance = new EventHandlers();
        return instance;
    }
    
    public EventHandler clientTableHandler()
    {
        jsonData = new JSONObject();
        EventHandler event = new EventHandler<CellEditEvent<ClientEntity, String>>() 
        {
            @Override
            public void handle(CellEditEvent<ClientEntity, String> event) 
            {
                if(event.getTableColumn().getText().equalsIgnoreCase("Representative"))
                {
                    event.getRowValue().setRepresentative(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Position"))
                {
                    event.getRowValue().setPosition(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Company"))
                {
                    event.getRowValue().setCompany_Name(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Industry"))
                {
                    event.getRowValue().setIndustry(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Type"))
                {
                    event.getRowValue().setType(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
            }
        };
        return event;
    }
    public EventHandler suppliersTableHandler()
    {
        jsonData = new JSONObject();
        EventHandler event = new EventHandler<CellEditEvent<SuppliersEntity, String>>() 
        {
            @Override
            public void handle(CellEditEvent<SuppliersEntity, String> event) 
            {
                if(event.getTableColumn().getText().equalsIgnoreCase("Representative"))
                {
                    event.getRowValue().setRepresentative(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Position"))
                {
                    event.getRowValue().setPosition(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Company"))
                {
                    event.getRowValue().setCompany_Name(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Brand"))
                {
                    event.getRowValue().setBrand(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Industry"))
                {
                    event.getRowValue().setIndustry(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Type"))
                {
                    event.getRowValue().setType(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
            }
        };
        return event;
    }
    public EventHandler contractorsTableHandler()
    {
        jsonData = new JSONObject();
        EventHandler event = new EventHandler<CellEditEvent<ContractorsEntity, String>>() 
        {
            @Override
            public void handle(CellEditEvent<ContractorsEntity, String> event) 
            {
                if(event.getTableColumn().getText().equalsIgnoreCase("Representative"))
                {
                    event.getRowValue().setRepresentative(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Position"))
                {
                    event.getRowValue().setPosition(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Company"))
                {
                    event.getRowValue().setCompanyName(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Specialization"))
                {
                    event.getRowValue().setSpecialization(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Industry"))
                {
                    event.getRowValue().setIndustry(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Classification"))
                {
                    event.getRowValue().setClassification(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
            }
        };
        return event;
    }
    public EventHandler consultantsTableHandler()
    {
        jsonData = new JSONObject();
        EventHandler event = new EventHandler<CellEditEvent<ConsultantsEntity, String>>() 
        {
            @Override
            public void handle(CellEditEvent<ConsultantsEntity, String> event) 
            {
                if(event.getTableColumn().getText().equalsIgnoreCase("Representative"))
                {
                    event.getRowValue().setRepresentative(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Position"))
                {
                    event.getRowValue().setPosition(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Company"))
                {
                    event.getRowValue().setCompanyName(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Specialization"))
                {
                    event.getRowValue().setSpecialization(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Industry"))
                {
                    event.getRowValue().setIndustry(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Classification"))
                {
                    event.getRowValue().setClassification(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
            }
        };
        return event;
    }
    public EventHandler specificationsTableHandler()
    {
        jsonData = new JSONObject();
        EventHandler event = new EventHandler<CellEditEvent<SpecificationsEntity, String>>() 
        {
            @Override
            public void handle(CellEditEvent<SpecificationsEntity, String> event) 
            {
                if(event.getTableColumn().getText().equalsIgnoreCase("Title"))
                {
                    event.getRowValue().setTitle(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Division"))
                {
                    event.getRowValue().setDivision(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Section"))
                {
                    event.getRowValue().setSection(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Type"))
                {
                    event.getRowValue().setType(event.getNewValue().toUpperCase().trim());
                    jsonData = event.getRowValue().buildJSON();
                }
                
            }
        };
        return event;
    }
    public EventHandler taskSetOnSucceededEvent(ListView<NotesEntity> listView,Task<?> task)
    {
        EventHandler event = new EventHandler<WorkerStateEvent>() 
        {
            @Override
            public void handle(WorkerStateEvent event) 
            {
                listView.setItems((ObservableList<NotesEntity>)task.getValue());
                listView.setCellFactory(cellFactoryCallBack(listView));
            }
        };
        return event;
    }
    private Callback cellFactoryCallBack(ListView<NotesEntity> listView)
    {
        Callback callback = new Callback<ListView<NotesEntity>, ListCell<NotesEntity>>() 
        {
            @Override
            public ListCell<NotesEntity> call(ListView<NotesEntity> param) 
            {
                listView.setOnMouseClicked(notesEditEventHandler(listView));
                return new CustomCell();
            }
        };
        return callback;
    }
    private EventHandler notesEditEventHandler(ListView<NotesEntity> listView)
    {
        EventHandler event = new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                if (event.getClickCount() == 2) 
                {
                    GetOtherControllerAttributesSingleton.getInstance().setSelectedNotesIndex(String.valueOf(listView.getSelectionModel().getSelectedIndex()));
                    GetOtherControllerAttributesSingleton.getInstance().setNotes(listView.getSelectionModel().getSelectedItem());
                    MyUtils.getInstance().openNewWindow("NotesView.fxml", "Show");
                }
            }
        };
        return event;
    }
    public EventHandler updateTaskEventHandler()
    {
        EventHandler event = new EventHandler<WorkerStateEvent>() 
        {
            @Override
            public void handle(WorkerStateEvent event) 
            {
                JOptionPane.showMessageDialog(null, "1 RECORD UPDATED");
            }
        };
        return event;
    }
    public EventHandler deleteTaskEventHandler(TableView tableView, ListView<String> files, ListView<NotesEntity> notes,int selectedIndex)
     {
         EventHandler event = new EventHandler<WorkerStateEvent>() 
         {
             @Override
             public void handle(WorkerStateEvent event) 
             {
                 HashMap<String, String> result = (HashMap<String, String>)TaskExecute.getInstance().getTask().getValue();
                    if(result.get("deleteRecord").equalsIgnoreCase("Successful"))
                    {
                        tableView.getItems().remove(selectedIndex);
                        files.getItems().clear();
                        notes.getItems().clear();
                        JOptionPane.showMessageDialog(null, "Record deleted successfully");
                    }
             }
         };
         return event;
     }
    public JSONObject getJsonData(){return jsonData;}
}
