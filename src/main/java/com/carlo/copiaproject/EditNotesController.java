package com.carlo.copiaproject;

import DatabaseOperations.TaskExecute;
import Entities.NotesEntity;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import MiscellaneousClasses.MyUtils;
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
    @FXML private Button editnotes_button_cancel,editnotes_buton_save,editnotes_button_delete;
    @FXML private ProgressIndicator editnotes_progressindicator;
    @FXML
    void cancelOnClicked(ActionEvent event) 
    {
        
    }

    @FXML
    void saveOnClicked(ActionEvent event) 
    {
        if(editnotes_textarea_remarks.getText().isEmpty() == false)
        {
            String searchClass = GetOtherControllerAttributesSingleton.getInstance().getSearchClass();
            TaskExecute.getInstance().updateNotes(editnotes_label_objectid.getText(), editnotes_textarea_remarks.getText(), searchClass, searchClass + "Pointer");
            MyUtils.getInstance().bindSearchNProgress(editnotes_buton_save, editnotes_progressindicator, TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    Response response = (Response) TaskExecute.getInstance().getTask().getValue();
                    System.out.println(response.getStatusCode());
                    if(response.getStatusCode() == 200)
                    {
                        //JOptionPane.showMessageDialog(null, "Record Updated Successfully");
                        NotesEntity entity = new NotesEntity(editnotes_label_objectid.getText(), editnotes_textfield_createdat.getText(), editnotes_textfield_updatedat.getText(), editnotes_textarea_remarks.getText().trim());
                        String selectedIndex = GetOtherControllerAttributesSingleton.getInstance().getSelectedNotesIndex();
                        GetOtherControllerAttributesSingleton.getInstance().getListviewNotes().get("ListViewNotes").getItems().set(Integer.valueOf(selectedIndex), entity);
                        ((Stage)editnotes_button_cancel.getScene().getWindow()).close();
                    }
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
    @FXML
    void deleteOnClicked(ActionEvent event) 
    {
        int result = JOptionPane.showConfirmDialog(null, "This selected record will be deleted", "Confirm Delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.OK_OPTION)
        {
            TaskExecute.getInstance().deleteSingleNote(editnotes_label_objectid.getText());
            MyUtils.getInstance().bindSearchNProgress(editnotes_button_delete, editnotes_progressindicator, TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    int selectedIndex = GetOtherControllerAttributesSingleton.getInstance().getListviewNotes().get("ListViewNotes").getSelectionModel().getSelectedIndex();
                    GetOtherControllerAttributesSingleton.getInstance().getListviewNotes().get("ListViewNotes").getItems().remove(selectedIndex);
                    ((Stage)editnotes_button_cancel.getScene().getWindow()).close();
                }
            });
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
