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

public class UpdateUserController {

    @FXML private Button backButton;
    @FXML private Button selectButton;
    @FXML private Button updateButton;

    @FXML private TextField userType;
    @FXML private TextField userId;
    @FXML private TextField attribute;
    @FXML private TextField newAttribute;

    @FXML private Text attributeList;
    @FXML private Text updateText;

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Users.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSelectButton() {
        String type = userType.getText().trim().toLowerCase();
        String extraAttributes = "";

        if (type.equals("patient")) {
            extraAttributes = ", assigned_doctor";
        } else if (type.equals("doctor")) {
            extraAttributes = ", specialization, working_hours";
        } else if (type.equals("administrator") || type.equals("admin")) {
            extraAttributes = ", role, official_email, supervisor_name";
        }

        attributeList.setText(
                "You can update these fields:\n" +
                        "user_id, name, age, gender, phone, email, username, password" + extraAttributes
        );

    }

    @FXML
    private void handleUpdateButton() {
        String type = userType.getText().trim().toLowerCase();
        String id = userId.getText().trim();
        String column = attribute.getText().trim();
        String newValue = newAttribute.getText().trim();

        if (type.isEmpty() || id.isEmpty() || column.isEmpty() || newValue.isEmpty()) {
            updateText.setText("All fields must be filled.");
            return;
        }

        String table;
        switch (type) {
            case "patient": table = "patients"; break;
            case "doctor": table = "doctors"; break;
            case "administrator": table = "administrators"; break;
            default:
                updateText.setText("Invalid user type.");
                return;
        }

        String query = "UPDATE " + table + " SET " + column + " = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // If the attribute is "age", set as integer
            if (column.equalsIgnoreCase("age")) {
                try {
                    int age = Integer.parseInt(newValue);
                    stmt.setInt(1, age);
                } catch (NumberFormatException e) {
                    updateText.setText("Invalid age. Please enter a valid number.");
                    return;
                }
            } else {
                stmt.setString(1, newValue);
            }

            stmt.setString(2, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                updateText.setText("User updated successfully.");
            } else {
                updateText.setText("User not found or invalid attribute.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            updateText.setText("Update failed. Check attribute name and value.");
        }
    }

}
