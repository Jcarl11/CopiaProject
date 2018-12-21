package MiscellaneousClasses;

import Entities.ClientEntity;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.CellEditEvent;
import org.json.JSONObject;

public class EventHandlers 
{
    JSONObject client;
    volatile boolean hasChange = false;
    Button savebtn;
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
    public void setClient(JSONObject client){this.client = client;}
    public JSONObject getClient(){return client;}
}
