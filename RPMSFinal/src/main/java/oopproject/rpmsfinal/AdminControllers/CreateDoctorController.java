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
import oopproject.rpmsfinal.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateDoctorController extends CreateUserController {

    @FXML private Button backButton;
    @FXML private Button createButton;
    @FXML private Text createText;
    @FXML private TextField specializationField;
    @FXML private TextField workingHoursField;

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
            String specialization = specializationField.getText().trim();
            String workingHours = workingHoursField.getText().trim();

            if (specialization.isEmpty() || workingHours.isEmpty()) {
                createText.setText("Specialization and working hours are required.");
                return;
            }

            // Create Doctor object
            Doctor doctor = new Doctor(usernameString, passwordString, userIdString, nameString,
                    ageInt, genderString, phoneString, emailString, specialization, workingHours);

            // Insert into doctors table
            String sql = "INSERT INTO doctors (user_id, username, password, name, age, gender, phone, email, specialization, working_hours) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, doctor.getUserId());
                stmt.setString(2, doctor.getUsername());
                stmt.setString(3, doctor.getPassword());
                stmt.setString(4, doctor.getName());
                stmt.setInt(5, doctor.getAge());
                stmt.setString(6, doctor.getGender());
                stmt.setString(7, doctor.getPhone());
                stmt.setString(8, doctor.getEmail());
                stmt.setString(9, doctor.getSpecialization());
                stmt.setString(10, doctor.getWorkingHours());

                stmt.executeUpdate();
            }

            createText.setText("Doctor created! ID: " + doctor.getUserId());

        } catch (Exception e) {
            createText.setText("Error creating doctor.");
            e.printStackTrace();
        }
    }
}
