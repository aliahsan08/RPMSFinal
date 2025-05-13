package oopproject.rpmsfinal.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oopproject.rpmsfinal.SessionNative;

import java.io.IOException;

public class NotificationsController {

    @FXML
    private Button backButton;
    @FXML
    private Button usersButton;
    @FXML
    private Button patientsButton;
    @FXML
    private Button doctorsButton;
    @FXML
    private Button specificUserButton;


    @FXML
    private void goBackHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUsersButton() {
        try {
            SessionNative.setNotifyUserType("user");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/MultipleUsers.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) usersButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handlePatientsButton() {
        try {
            SessionNative.setNotifyUserType("patient");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/MultipleUsers.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) patientsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleDoctorsButton() {
        try {
            SessionNative.setNotifyUserType("doctor");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/MultipleUsers.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) doctorsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleSpecificUserButton() {
        try {
            SessionNative.setNotifyUserType("specificUser");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/specificUser.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) specificUserButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

