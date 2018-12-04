package UploadProcess;

import DatabaseOperations.SendPostConsultants;
import Entities.ConsultantsEntity;
import javax.swing.JOptionPane;

public class ConsultantsUpload 
{
    ConsultantsEntity consultantsEntity;
    public ConsultantsUpload(ConsultantsEntity consultantsEntity)
    {
        this.consultantsEntity = consultantsEntity;
    }
    public void upload()
    {
        SendPostConsultants sendPost = new SendPostConsultants(consultantsEntity, "Consultants");
        Thread sendPostThread = new Thread(sendPost);
        sendPostThread.start();
        try{sendPostThread.join();}catch(Exception ex){ex.printStackTrace();}
        if(sendPost.getResult() == "Successful")
            JOptionPane.showMessageDialog(null, "Successful");
        else
            JOptionPane.showMessageDialog(null, "Upload failed");
    }
}
