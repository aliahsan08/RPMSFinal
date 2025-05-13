package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.EmailNotification;
import oopproject.rpmsfinal.Patient;
import oopproject.rpmsfinal.UserManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReminderAppointmentsController {

    EmailNotification emailer = EmailNotification.getInstance();
    private UserManager userManager = UserManager.getInstance();
    @FXML private TextField userIdField;
    @FXML private TextField messageArea;
    @FXML private Text reminderText;
    @FXML private Button remindButton;
    @FXML private Button backButton;

    @FXML
    private void handleRemindButton() {
        String userId = userIdField.getText().trim();
        String message = messageArea.getText().trim();

        if (userId.isEmpty() || message.isEmpty()) {
            reminderText.setText("Please fill all fields.");
            return;
        }

        String email = fetchUserEmail(userId);
        if (email == null) {
            reminderText.setText("User not found.");
            return;
        }
        emailer.sendNotification(email, "Reminder for Appointment", message);
        reminderText.setText("Reminder sent to patient ID: " + userId);
    }

    private String fetchUserEmail(String userId) {
        Patient patient = userManager.getPatientById(userId);
        return patient.getEmail();
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Appointments.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
