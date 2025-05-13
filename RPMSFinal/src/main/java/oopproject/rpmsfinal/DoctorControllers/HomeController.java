package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button profileButton;

    @FXML
    private Button appointmentsButton;

    @FXML
    private Button vitalsButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button videoCallButton;

    @FXML
    private Button patientsButton;

    @FXML
    private Button addFeedbackButton;

    @FXML
    private Button chatButton;


    @FXML
    private void handleProfileButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Profile.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) profileButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAppointmentsButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Appointments.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) appointmentsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVitalsButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Vitals.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) vitalsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/Login.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVideoCallButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/VideoCall.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) videoCallButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePatientsButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Patients.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) patientsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddFeedbackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/AddFeedback.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) addFeedbackButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleChatButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Chat.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) chatButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
