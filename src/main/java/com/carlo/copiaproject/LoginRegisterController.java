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
import jdk.nashorn.internal.scripts.JO;
import org.asynchttpclient.Response;
import org.json.JSONObject;
import org.parse4j.ParseException;
import org.parse4j.ParseUser;
import org.parse4j.callback.LoginCallback;

public class LoginRegisterController implements Initializable 
{
    @FXML private TextField login_textfield_username,register_username,register_email;
    @FXML private PasswordField login_textfield_password,register_password,register_confirmpassword;
    @FXML private CheckBox login_checkbox;
    @FXML private Button button_login,register_submit;
    @FXML private ProgressIndicator progress_indicator,register_progress;

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
                    if(jsonResponse.getBoolean("emailVerified") == true)
                    {
                        if(login_checkbox.isSelected())
                            jsonResponse.put("rememberpassword", true);
                        else
                            jsonResponse.put("rememberpassword", false);
                        UserPreferences.getInstance().userData(jsonResponse);
                        MainApp.stage.show();
                        ((Stage)button_login.getScene().getWindow()).close();
                    }
                    else
                    {
                        TaskExecute.getInstance().logout(jsonResponse.getString("sessionToken"));
                        TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>()
                        {
                            @Override
                            public void handle(WorkerStateEvent event) 
                            {
                                Response response = (Response)TaskExecute.getInstance().getTask().getValue();
                                System.out.println(response.getStatusCode());
                                if(response.getStatusCode() == 200)
                                {
                                    JOptionPane.showMessageDialog(null, "Please verify your email to continue");
                                }
                                else
                                    JOptionPane.showMessageDialog(null, "Failed");
                            }
                        });
                        
                    }
                        
                    
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Wrong username/password");
                }
                
            }
        });
        
    }
    @FXML
    void register_submitOnClick(ActionEvent event) 
    {
        if(!register_username.getText().trim().isEmpty() && !register_email.getText().trim().isEmpty() && !register_password.getText().trim().isEmpty() && !register_confirmpassword.getText().trim().isEmpty())
        {
            if(register_password.getText().trim().equals(register_confirmpassword.getText().trim()))
            {
                TaskExecute.getInstance().registerUser(register_username.getText().trim(), register_password.getText().trim(), register_email.getText().trim());
                MyUtils.getInstance().bindSearchNProgress(register_submit, register_progress, TaskExecute.getInstance().getTask().runningProperty());
                TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
                {
                    @Override
                    public void handle(WorkerStateEvent event) 
                    {
                        
                        if(((Response)TaskExecute.getInstance().getTask().getValue()).getStatusCode() == 200)
                        {
                            JOptionPane.showConfirmDialog(null, "A confirmation email was sent to your email address.\n Please confirm to complete your registration", "Your registration is almost complete", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                            System.out.println(((Response)TaskExecute.getInstance().getTask().getValue()).getStatusCode());
                    }
                });
            }
            else
                JOptionPane.showMessageDialog(null, "Passwords does not match");
        }
        else
            JOptionPane.showMessageDialog(null, "Don't leave blank fields");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }    
    
}
