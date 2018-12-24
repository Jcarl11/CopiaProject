package com.carlo.copiaproject;

import DatabaseOperations.TaskExecute;
import Entities.NotesEntity;
import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import MiscellaneousClasses.MyUtils;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.asynchttpclient.Response;
import org.json.JSONObject;

public class AddNotesViewController implements Initializable 
{
    @FXML private Button addnotes_save,addnotes_cancel;
    @FXML private TextArea addnotes_textarea;
    @FXML private ProgressIndicator progress_indicator;
    @FXML
    void addnotes_cancelOnClick(ActionEvent event) 
    {
        ((Stage)addnotes_cancel.getScene().getWindow()).close();
    }

    @FXML
    void addnotes_saveOnClick(ActionEvent event) 
    {
        if(!addnotes_textarea.getText().trim().isEmpty())
        {
            ArrayList<NotesEntity> notesList = new ArrayList<>();
            String[] notes = addnotes_textarea.getText().trim().toUpperCase().split(",");
            for(String note : notes)
            {
                NotesEntity notesEntity = new NotesEntity();
                notesEntity.setRemarks(note);
                notesList.add(notesEntity);
            }
            TaskExecute.getInstance().addNotes(GetOtherControllerAttributesSingleton.getInstance().getSelectedObjectId(), notesList, GetOtherControllerAttributesSingleton.getInstance().getSearchClass());
            MyUtils.getInstance().bindSearchNProgress(addnotes_save, progress_indicator, TaskExecute.getInstance().getTask().runningProperty());
            TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
            {
                @Override
                public void handle(WorkerStateEvent event) 
                {
                    for(Future<Response> responses : (List<Future<Response>>)TaskExecute.getInstance().getTask().getValue())
                    {
                        try {
                            JSONObject json = new JSONObject(responses.get().getResponseBody());
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                            Date date = dateFormat.parse(json.getString("createdAt"));
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
                            String dateString = formatter.format(date);
                            NotesEntity entity = new NotesEntity(json.getString("objectId"), dateString, dateString, addnotes_textarea.getText().trim());
                            GetOtherControllerAttributesSingleton.getInstance().getListviewNotes().get("ListViewNotes").getItems().add(entity);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(AddNotesViewController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(AddNotesViewController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ParseException ex) {
                            Logger.getLogger(AddNotesViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    ((Stage)addnotes_cancel.getScene().getWindow()).close();
                }
            });
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }   
}
