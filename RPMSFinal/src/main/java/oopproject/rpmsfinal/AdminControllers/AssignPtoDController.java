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

public class AssignPtoDController {

    @FXML
    private Button backButton;
    @FXML
    private Button assignButton;
    @FXML
    private TextField patientIdField;
    @FXML
    private TextField doctorIdField;
    @FXML
    private Text assignment;

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/PatientDoctorAssignment.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAssignButton() {
        String patientId = patientIdField.getText();
        String doctorId = doctorIdField.getText();

        if (patientId.isEmpty() || doctorId.isEmpty()) {
            assignment.setText("Both Patient ID and Doctor ID must be provided.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO doctor_patient_assignments (doctor_id, patient_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, doctorId);
            stmt.setString(2, patientId);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                assignment.setText("Patient assigned to doctor successfully.");
            } else {
                assignment.setText("Failed to assign patient.");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            assignment.setText("Error assigning patient to doctor.");
        }
    }
}
