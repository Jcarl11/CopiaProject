package UploadProcess;

import DatabaseOperations.SendPostContractors;
import Entities.ContractorsEntity;
import javax.swing.JOptionPane;

public class ContractorsUpload 
{
    ContractorsEntity contractorsEntity;
    public ContractorsUpload(ContractorsEntity contractors)
    {
        this.contractorsEntity = contractors;
    }
    
    public void upload()
    {
        SendPostContractors sendPost = new SendPostContractors(contractorsEntity, "Contractors");
        Thread sendPostThread = new Thread(sendPost);
        sendPostThread.start();
        try{sendPostThread.join();}catch(Exception ex){ex.printStackTrace();}
        if(sendPost.getResult() == "Successful")
            JOptionPane.showMessageDialog(null, "Successful");
        else
            JOptionPane.showMessageDialog(null, "Upload failed");
    }
}
