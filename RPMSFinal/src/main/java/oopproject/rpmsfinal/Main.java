package oopproject.rpmsfinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {
            // Correctly load Login.fxml from the classpath
            Parent root = FXMLLoader.load(getClass().getResource("UserType.fxml"));

            // Set up the scene and stage
            Scene scene = new Scene(root);
            primaryStage.setTitle("Remote Patient Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);  // Launch the JavaFX application
    }
}
