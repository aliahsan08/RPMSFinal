package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.EmailNotification;
import oopproject.rpmsfinal.SessionNative;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MultipleUsersController {
    EmailNotification emailer = EmailNotification.getInstance();
    @FXML private Button backButton;
    @FXML private Button notifyButton;
    @FXML private Text notification;
    @FXML private TextField messageArea;

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Notifications.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNotifyButton() {
        String userType = SessionNative.getNotifyUserType();  // "patient", "doctor", or "user"
        String message = messageArea.getText();

        if (message == null || message.trim().isEmpty()) {
            notification.setText("Message cannot be empty.");
            return;
        }

        List<String> emails = fetchEmails(userType);
        if (emails.isEmpty()) {
            notification.setText("No users found to notify.");
            return;
        }

        for (String email : emails) {
            emailer.sendNotification(email, "Notification from Admin", message);
        }

        notification.setText("Notification sent to " + emails.size() + " " + userType.toLowerCase() + "(s).");
    }

    private List<String> fetchEmails(String userType) {
        List<String> emails = new ArrayList<>();
        String query;
        switch (userType) {
            case "patient":
                query= "SELECT email FROM patients";
                break;
            case "doctor":
                query= "SELECT email FROM doctors";
                break;

            case "user":
               query = "SELECT email FROM patients UNION SELECT email FROM doctors UNION SELECT email FROM administrators";
               break;
            default:
                query = "";
        };

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;
    }
}
