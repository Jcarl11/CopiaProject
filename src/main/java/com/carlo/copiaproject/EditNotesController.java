package com.carlo.copiaproject;

import DatabaseOperations.TaskExecute;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.asynchttpclient.Response;

public class EditNotesController implements Initializable 
{
    @FXML private TextField editnotes_textfield_createdat,editnotes_textfield_updatedat;

    @FXML private TextArea editnotes_textarea_remarks;

    @FXML private Label editnotes_label_objectid;

    @FXML private Button editnotes_button_cancel,editnotes_buton_save;

    @FXML private ProgressIndicator editnotes_progressindicator;

    @FXML
    void cancelOnClicked(ActionEvent event) 
    {
        ((Stage)editnotes_button_cancel.getScene().getWindow()).close();
    }

    @FXML
    void saveOnClicked(ActionEvent event) 
    {
        if(editnotes_textarea_remarks.getText().isEmpty() == false)
        {
            TaskExecute.getInstance().updateNotes(editnotes_label_objectid.getText(), editnotes_textarea_remarks.getText(), "Client", "ClientPointer");
            editnotes_buton_save.disableProperty().unbind();
            editnotes_progressindicator.visibleProperty().unbind();
            editnotes_progressindicator.visibleProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            editnotes_buton_save.disableProperty().bind(TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    Response response = (Response) TaskExecute.getInstance().getTask().getValue();
                    System.out.println(response.getStatusCode());
                    if(response.getStatusCode() == 200)
                        JOptionPane.showMessageDialog(null, "Record Updated Successfully");
                    else
                        JOptionPane.showMessageDialog(null, "Update Failed");
                }
            });
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Cannot be empty");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        editnotes_textfield_createdat.setText(GetOtherControllerAttributesSingleton.getInstance().getNotes().getCreatedAt());
        editnotes_textfield_updatedat.setText(GetOtherControllerAttributesSingleton.getInstance().getNotes().getUpdatedAt());
        editnotes_textarea_remarks.setText(GetOtherControllerAttributesSingleton.getInstance().getNotes().getRemarks());
        editnotes_label_objectid.setText(GetOtherControllerAttributesSingleton.getInstance().getNotes().getObjectId());
    }    
}
