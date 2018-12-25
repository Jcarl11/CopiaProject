package com.carlo.copiaproject;

import DatabaseOperations.TaskExecute;
import MiscellaneousClasses.MyUtils;
import MiscellaneousClasses.UserPreferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.parse4j.Parse;
import org.parse4j.ParseUser;


public class MainApp extends Application 
{

    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parse.initialize("4GCD5XK7GucFbTKnJa0fonFEBlAh3azBS3Gh0NNd", "RYznH1yrJ3DVly2f02aEMkZJNwmPVdDBUQyqRT6H","https://concipiotektura.back4app.io");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
        {
            @Override
            public void handle(WindowEvent event) 
            {
                
                if(UserPreferences.getInstance().getPreference().getBoolean("rememberpassword", false) == false)
                {
                    event.consume();
                    TaskExecute.getInstance().logout(UserPreferences.getInstance().getPreference().get("sessionToken", null));
                    TaskExecute.getInstance().getTask().setOnSucceeded(new EventHandler<WorkerStateEvent>() 
                    {
                        @Override
                        public void handle(WorkerStateEvent event) 
                        {
                            UserPreferences.getInstance().clearPreference();
                            Platform.exit();
                            System.exit(0);
                        }
                    });
                }
                else
                {
                    Platform.exit();
                    System.exit(0);
                }
                
            }
        });
        if(UserPreferences.getInstance().getPreference().get("sessionToken", null) != null)
        {
            
            stage.show();
        }
        else
        {
            MyUtils.getInstance().openNewWindow("LoginRegister.fxml", "Login");
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
