
package com.carlo.copiaproject;

import DatabaseOperations.TaskExecute;
import MiscellaneousClasses.MyUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import org.asynchttpclient.Response;

public class ChangePasswordController implements Initializable 
{
    @FXML private TextField cp_textfield;

    @FXML private Button cp_button_send;

    @FXML private ProgressIndicator cp_progress;

    @FXML private Label cp_label;

    @FXML
    void cp_button_sendOnClick(ActionEvent event) 
    {
        TaskExecute.getInstance().changePass(cp_textfield.getText().trim());
        MyUtils.getInstance().bindSearchNProgress(cp_button_send, cp_progress, TaskExecute.getInstance().getTask().runningProperty());
        TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
        {
            @Override
            public void handle(WorkerStateEvent event) 
            {
                int responseCode = ((Response)TaskExecute.getInstance().getTask().getValue()).getStatusCode();
                if(responseCode == 200)
                {
                    TaskExecute.getInstance().changePass(cp_textfield.getText().trim());
                    MyUtils.getInstance().bindSearchNProgress(cp_button_send, cp_progress, TaskExecute.getInstance().getTask().runningProperty());
                    TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>()
                    {
                        @Override
                        public void handle(WorkerStateEvent event) 
                        {
                            cp_label.setText("An email was sent to your email");
                        }
                    });
                }
                else
                    cp_label.setText("This email is not associated with any accounts");
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
}
