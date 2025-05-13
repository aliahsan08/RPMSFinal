package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecificUserController {

    EmailNotification emailer = EmailNotification.getInstance();
    private UserManager userManager = UserManager.getInstance();
    @FXML private TextField userIdField;
    @FXML private TextField messageArea;
    @FXML private Text notification;
    @FXML private Button notifyButton;
    @FXML private Button backButton;

    @FXML
    private void handleNotifyButton() {
        String userId = userIdField.getText().trim();
        String message = messageArea.getText().trim();

        if (userId.isEmpty() || message.isEmpty()) {
            notification.setText("Please fill all fields.");
            return;
        }

        String email = fetchUserEmail(userId);
        if (email == null) {
            notification.setText("User not found.");
            return;
        }

        emailer.sendNotification(email, "Notification from Admin", message);
        notification.setText("Notification sent to user ID: " + userId);
    }

    private String fetchUserEmail(String userId) {
        User user = userManager.getUserById(userId);
        return user.getEmail();
    }

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
}
