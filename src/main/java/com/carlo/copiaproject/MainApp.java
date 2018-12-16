package com.carlo.copiaproject;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.parse4j.Parse;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
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
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();
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
