package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Administrator;
import oopproject.rpmsfinal.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateAdminController extends CreateUserController {

    @FXML private Button backButton;
    @FXML private Button createButton;
    @FXML private Text createText;
    @FXML private TextField roleField;
    @FXML private TextField officialEmailField;
    @FXML private TextField supervisorNameField;

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreateUser.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateButton() {
        try {
            String role = roleField.getText().trim();
            String officialEmail = officialEmailField.getText().trim();
            String supervisorName = supervisorNameField.getText().trim();

            if (role.isEmpty() || officialEmail.isEmpty() || supervisorName.isEmpty()) {
                createText.setText("All fields are required.");
                return;
            }

            // Create Administrator object
            Administrator admin = new Administrator(usernameString, passwordString, userIdString, nameString,
                    ageInt, genderString, phoneString, emailString, role, officialEmail, supervisorName);

            // Insert into administrators table
            String sql = "INSERT INTO administrators (user_id, username, password, name, age, gender, phone, email, role, official_email, supervisor_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, admin.getUserId());
                stmt.setString(2, admin.getUsername());
                stmt.setString(3, admin.getPassword());
                stmt.setString(4, admin.getName());
                stmt.setInt(5, admin.getAge());
                stmt.setString(6, admin.getGender());
                stmt.setString(7, admin.getPhone());
                stmt.setString(8, admin.getEmail());
                stmt.setString(9, admin.getRole());
                stmt.setString(10, admin.getOfficialEmail());
                stmt.setString(11, admin.getSupervisorName());

                stmt.executeUpdate();
            }

            createText.setText("Administrator created! ID: " + admin.getUserId());

        } catch (Exception e) {
            createText.setText("Error creating administrator.");
            e.printStackTrace();
        }
    }
}
