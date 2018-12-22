package MiscellaneousClasses;

import Entities.ClientEntity;
import Entities.NotesEntity;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.json.JSONObject;

public class EventHandlers 
{
    JSONObject client;
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
        client = new JSONObject();
        EventHandler event = new EventHandler<CellEditEvent<ClientEntity, String>>() 
        {
            @Override
            public void handle(CellEditEvent<ClientEntity, String> event) 
            {
                if(event.getTableColumn().getText().equalsIgnoreCase("Representative"))
                {
                    event.getRowValue().setRepresentative(event.getNewValue().toUpperCase().trim());
                    client = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Position"))
                {
                    event.getRowValue().setPosition(event.getNewValue().toUpperCase().trim());
                    client = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Company"))
                {
                    event.getRowValue().setCompany_Name(event.getNewValue().toUpperCase().trim());
                    client = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Industry"))
                {
                    event.getRowValue().setIndustry(event.getNewValue().toUpperCase().trim());
                    client = event.getRowValue().buildJSON();
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Type"))
                {
                    event.getRowValue().setType(event.getNewValue().toUpperCase().trim());
                    client = event.getRowValue().buildJSON();
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
    
    public void setClient(JSONObject client){this.client = client;}
    public JSONObject getClient(){return client;}
}
