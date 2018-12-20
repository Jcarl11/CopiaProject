package MiscellaneousClasses;

import Entities.ClientEntity;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import org.json.JSONObject;

public class EventHandlers 
{

    public JSONObject getClient() {
        return client;
    }
    JSONObject client = new JSONObject();
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
    
    private ClientEntity clientEntity = new ClientEntity();
    public void setSaveBtn(Button btn)
    {
        savebtn = btn;
    }
   
    public EventHandler clientTableHandler()
    {
        EventHandler event = new EventHandler<CellEditEvent<ClientEntity, String>>() 
        {
            @Override
            public void handle(CellEditEvent<ClientEntity, String> event) 
            {
                if(event.getTableColumn().getText().equalsIgnoreCase("Representative"))
                {
                    //clientEntity.setRepresentative(event.getNewValue().toUpperCase().trim());
                    client.put("Representative", event.getNewValue().toUpperCase().trim());
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Position"))
                {
                    //clientEntity.setPosition(event.getNewValue().toUpperCase().trim());
                    client.put("Position", event.getNewValue().toUpperCase().trim());
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Company"))
                {
                    //clientEntity.setCompany_Name(event.getNewValue().toUpperCase().trim());
                    client.put("Company_Name", event.getNewValue().toUpperCase().trim());
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Industry"))
                {
                    //clientEntity.setIndustry(event.getNewValue().toUpperCase().trim());
                    client.put("Industry", event.getNewValue().toUpperCase().trim());
                }
                else if(event.getTableColumn().getText().equalsIgnoreCase("Type"))
                {
                    //clientEntity.setType(event.getNewValue().toUpperCase().trim());
                    client.put("Type", event.getNewValue().toUpperCase().trim());
                }
            }
        };
        return event;
    }
    public ClientEntity getClientEntity() 
    {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) 
    {
        this.clientEntity = clientEntity;
    }
}
