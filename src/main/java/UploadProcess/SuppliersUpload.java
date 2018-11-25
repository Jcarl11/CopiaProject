package UploadProcess;

import Entities.ClientEntity;
import DatabaseOperations.SendPost;
import DatabaseOperations.SendPostSuppliers;
import Entities.SuppliersEntity;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;

/**
 *
 * @author Joey Francisco
 */
public class SuppliersUpload 
{
    GridPane gridPane;
    SuppliersEntity suppliersEntity;
    public SuppliersUpload(SuppliersEntity suppliersEntity)
    {
        this.suppliersEntity = suppliersEntity;
    }
    
    public void upload()
    {
        SendPostSuppliers sendPost = new SendPostSuppliers(suppliersEntity, "Suppliers");
        Thread sendPostThread = new Thread(sendPost);
        sendPostThread.start();
        try{sendPostThread.join();}catch(Exception ex){ex.printStackTrace();}
        if(sendPost.getResult() == "Successful")
            JOptionPane.showMessageDialog(null, "Successful");
        else
            JOptionPane.showMessageDialog(null, "Upload failed");
    }
}
