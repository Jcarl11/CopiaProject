package com.carlo.copiaproject;

import MiscellaneousClasses.GetOtherControllerAttributesSingleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NotesViewController implements Initializable 
{
    @FXML
    private TextArea notesview_textarea;

    @FXML
    private Button notesview_button_close;

    @FXML
    void closeOnClicked(ActionEvent event) 
    {
        ((Stage)notesview_button_close.getScene().getWindow()).close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        notesview_textarea.setText(GetOtherControllerAttributesSingleton.getInstance().getNotes().getRemarks());
    }    
    
}
