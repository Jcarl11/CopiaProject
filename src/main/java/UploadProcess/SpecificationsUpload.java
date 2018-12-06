package UploadProcess;

import DatabaseOperations.SendPostSpecifications;
import Entities.SpecificationsEntity;
import javax.swing.JOptionPane;

public class SpecificationsUpload 
{
    SpecificationsEntity specificationsEntity;
    public SpecificationsUpload(SpecificationsEntity specificationsEntity)
    {
        this.specificationsEntity = specificationsEntity;
    }
    public void upload()
    {
        SendPostSpecifications sendPost = new SendPostSpecifications(specificationsEntity, "Specifications");
        Thread sendPostThread = new Thread(sendPost);
        sendPostThread.start();
        try{sendPostThread.join();}catch(Exception ex){ex.printStackTrace();}
        if(sendPost.getResult() == "Successful")
            JOptionPane.showMessageDialog(null, "Successful");
        else
            JOptionPane.showMessageDialog(null, "Upload failed");
    }
}
