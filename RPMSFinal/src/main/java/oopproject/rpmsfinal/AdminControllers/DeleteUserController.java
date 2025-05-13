package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteUserController {

    @FXML private Button backButton;
    @FXML private Button deleteButton;
    @FXML private TextField userIdField;
    @FXML private Text userText;

    @FXML
    private void goBackHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Users.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButton() {
        String userId = userIdField.getText().trim();
        if (userId.isEmpty()) {
            userText.setText("Please enter a user ID.");
            return;
        }

        String[] tables = {"patients", "doctors", "administrators"};
        boolean userFound = false;

        try (Connection conn = DBConnection.getConnection()) {
            for (String table : tables) {
                String checkQuery = "SELECT user_id FROM " + table + " WHERE user_id = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                    checkStmt.setString(1, userId);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next()) {
                        String deleteQuery = "DELETE FROM " + table + " WHERE user_id = ?";
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                            deleteStmt.setString(1, userId);
                            deleteStmt.executeUpdate();
                            userText.setText("User with ID " + userId + " deleted from " + table + ".");
                            userFound = true;
                            break;
                        }
                    }
                }
            }

            if (!userFound) {
                userText.setText("User ID not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            userText.setText("Error deleting user.");
        }
    }
}
