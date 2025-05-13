package oopproject.rpmsfinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;


public class UserTypeController {

    @FXML
    private Button patientButton;
    @FXML
    private Button doctorButton;
    @FXML
    private Button adminButton;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handlePatientButton() {
        try {
            SessionNative.setUserType("Patient");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) patientButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDoctorButton() {
        try {
            SessionNative.setUserType("Doctor");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) doctorButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleAdminButton() {
        try {
            SessionNative.setUserType("Administrator");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) adminButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

