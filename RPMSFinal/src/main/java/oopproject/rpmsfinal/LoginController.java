package oopproject.rpmsfinal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.sql.*;
import java.io.IOException;

public class LoginController {
    private static final UserManager userManager = UserManager.getInstance();
    @FXML
    private Text loginText;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    @FXML
    private Button backButton;
    private String userType;

    @FXML
    public void initialize() {
        userType = SessionNative.getUserType();
    }
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        SessionNative.setUsername(username);

        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

        String table = "";
        if ("Patient".equals(userType)) {
            table = "patients";
        } else if ("Doctor".equals(userType)) {
            table = "doctors";
        } else if ("Administrator".equals(userType)) {
            table = "administrators";
        }

        String query = "SELECT * FROM " + table + " WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userId = rs.getString("user_id");
                SessionNative.setUserId(userId);

                // Common fields
                String name = rs.getString("name");  // Replace with your actual column name
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                String email = rs.getString("email");

                // Create user object based on type
                if ("Patient".equals(userType)) {
                    String assignedDoctor = rs.getString("assigned_doctor");
                    Patient patient = new Patient(username, password, userId, name, age, gender, phone, email, assignedDoctor);
                    SessionNative.setCurrentUser(patient);
                } else if ("Doctor".equals(userType)) {
                    String specialization = rs.getString("specialization");
                    String workingHours = rs.getString("working_hours");
                    Doctor doctor = new Doctor(username, password, userId, name, age, gender, phone, email, specialization, workingHours);
                    SessionNative.setCurrentUser(doctor);
                } else if ("Administrator".equals(userType)) {
                    String role = rs.getString("role");
                    String officialEmail = rs.getString("official_email");
                    String supervisorName = rs.getString("supervisor_name");
                    Administrator admin = new Administrator(username, password, userId, name, age, gender, phone, email, role, officialEmail, supervisorName);
                    SessionNative.setCurrentUser(admin);
                }

                // Redirect to appropriate home page
                String fxmlPath = null;
                if ("Patient".equals(userType)) {
                    fxmlPath = "PatientFiles/Home.fxml";
                } else if ("Doctor".equals(userType)) {
                    fxmlPath = "DoctorFiles/Home.fxml";
                } else if ("Administrator".equals(userType)) {
                    fxmlPath = "AdminFiles/Home.fxml";
                }

                if (fxmlPath != null) {
                    Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.setScene(new Scene(root));
                }

            } else {
                System.out.println("Invalid credentials.");
                // Optionally show error on UI
            }

        } catch (Exception e) {
            e.printStackTrace();
            loginText.setText("Invalid Credentials");
        }
    }


    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("UserType.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
