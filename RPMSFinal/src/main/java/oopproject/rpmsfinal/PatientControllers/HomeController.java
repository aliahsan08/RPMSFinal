package oopproject.rpmsfinal.PatientControllers;

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
    private Button logoutButton;

    @FXML
    private Button videoCallButton;

    @FXML
    private Button panicButton;

    @FXML
    private Button chatButton;
    @FXML
    private Button vitalsButton;
    @FXML
    private Button feedbackButton;


    @FXML
    private void handleProfileButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Profile.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Appointments.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) appointmentsButton.getScene().getWindow();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/VideoCall.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) videoCallButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePanicButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Panic.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) panicButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleChatButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Chat.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) chatButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleFeedbackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Feedback.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) feedbackButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleVitalsButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Vitals.fxml"));
            StackPane root = loader.load();
            Stage stage = (Stage) vitalsButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
