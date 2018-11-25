package UploadProcess;

import Entities.ClientEntity;
import DatabaseOperations.SendPost;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Joey Francisco
 */
public class ClientUpload 
{
    GridPane gridPane;
    ClientEntity clientEntity;
    public ClientUpload(ClientEntity client)
    {
        this.clientEntity = client;
    }
    
    public void upload()
    {
        SendPost sendPost = new SendPost(clientEntity, "Client");
        Thread sendPostThread = new Thread(sendPost);
        sendPostThread.start();
        try{sendPostThread.join();}catch(Exception ex){ex.printStackTrace();}
        if(sendPost.getResult() == "Successful")
            JOptionPane.showMessageDialog(null, "Successful");
        else
            JOptionPane.showMessageDialog(null, "Upload failed");
    }
}
