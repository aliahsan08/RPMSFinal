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
import oopproject.rpmsfinal.Patient;

import java.sql.*;

public class CreatePatientController extends CreateUserController {

    @FXML private Button backButton;
    @FXML private Button createButton;
    @FXML private Text createText;
    @FXML private TextField assignedDoctorField;
    @FXML private Text displayOptions;
    @FXML
    public void initialize() {
        String options = "";

        String query = "SELECT user_id, name, specialization FROM doctors";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int i = 1;
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");

                options += String.format("%s Dr.  %s (%s)\n", userId, name, specialization);
            }

            displayOptions.setText(options);

        } catch (SQLException e) {
            e.printStackTrace();
            displayOptions.setText("Error loading doctor list.");
        }
    }

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
            String assignedDoctor = assignedDoctorField.getText().trim();
            if (assignedDoctor.isEmpty()) {
                createText.setText("Assigned doctor is required.");
                return;
            }

            // Create Patient object
            Patient patient = new Patient(usernameString, passwordString, userIdString, nameString,
                    ageInt, genderString, phoneString, emailString, assignedDoctor);

            try (Connection conn = DBConnection.getConnection()) {
                // Insert into patients table
                String sqlPatient = "INSERT INTO patients (user_id, username, password, name, age, gender, phone, email, assigned_doctor) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement stmt = conn.prepareStatement(sqlPatient)) {
                    stmt.setString(1, patient.getUserId());
                    stmt.setString(2, patient.getUsername());
                    stmt.setString(3, patient.getPassword());
                    stmt.setString(4, patient.getName());
                    stmt.setInt(5, patient.getAge());
                    stmt.setString(6, patient.getGender());
                    stmt.setString(7, patient.getPhone());
                    stmt.setString(8, patient.getEmail());
                    stmt.setString(9, patient.getAssignedDoctor());
                    stmt.executeUpdate();
                }

                // Insert into doctor_patient_assignments table
                String sqlAssignment = "INSERT INTO doctor_patient_assignments (doctor_id, patient_id) VALUES (?, ?)";
                try (PreparedStatement assignStmt = conn.prepareStatement(sqlAssignment)) {
                    assignStmt.setString(1, assignedDoctor);
                    assignStmt.setString(2, patient.getUserId());
                    assignStmt.executeUpdate();
                }
            }

            createText.setText("Patient created! ID: " + patient.getUserId());

        } catch (Exception e) {
            createText.setText("Error creating patient.");
            e.printStackTrace();
        }
    }
}
