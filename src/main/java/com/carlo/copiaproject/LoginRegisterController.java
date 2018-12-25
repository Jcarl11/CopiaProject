package com.carlo.copiaproject;

import DatabaseOperations.TaskExecute;
import MiscellaneousClasses.MyUtils;
import MiscellaneousClasses.UserPreferences;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.asynchttpclient.Response;
import org.json.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseUser;
import org.parse4j.callback.LoginCallback;

public class LoginRegisterController implements Initializable 
{
    @FXML private TextField login_textfield_username;
    @FXML private PasswordField login_textfield_password;
    @FXML private CheckBox login_checkbox;
    @FXML private Button button_login;
    @FXML private ProgressIndicator progress_indicator;

    @FXML
    void button_loginOnClick(ActionEvent event)
    {
        TaskExecute.getInstance().loginUser(login_textfield_username.getText().trim(), login_textfield_password.getText().trim());
        MyUtils.getInstance().bindSearchNProgress(button_login, progress_indicator, TaskExecute.getInstance().getTask().runningProperty());
        TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>()
        {
            @Override
            public void handle(WorkerStateEvent event) 
            {
                Response user = ((Response)TaskExecute.getInstance().getTask().getValue());
                JSONObject jsonResponse = new JSONObject(user.getResponseBody());
                if(user.getStatusCode() == 200)
                {
                    if(login_checkbox.isSelected())
                        MyUtils.REMEMBER_PASSWORD = true;
                    UserPreferences.getInstance().userData(jsonResponse);
                    MainApp.stage.show();
                    ((Stage)button_login.getScene().getWindow()).close();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Wrong username/password");
                }
                
            }
        });
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }    
    
}
