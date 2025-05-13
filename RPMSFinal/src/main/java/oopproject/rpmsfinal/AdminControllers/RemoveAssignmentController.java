package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;

import oopproject.rpmsfinal.DBConnection;

public class RemoveAssignmentController {

    @FXML
    private Button backButton;

    @FXML
    private TextField patientIdField;

    @FXML
    private TextField doctorIdField;

    @FXML
    private Text assignment;

    @FXML
    private Button removeButton;

    @FXML
    private void handleRemoveButton() {
        String patientId = patientIdField.getText().trim();
        String doctorId = doctorIdField.getText().trim();

        if (patientId.isEmpty() || doctorId.isEmpty()) {
            assignment.setText("Please enter both Patient ID and Doctor ID.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM doctor_patient_assignments WHERE doctor_id = ? AND patient_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, doctorId);
            pstmt.setString(2, patientId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                assignment.setText("Assignment removed successfully.");
            } else {
                assignment.setText("No assignment found for the given IDs.");
            }
            
        } catch (Exception e) {
            assignment.setText("Error removing assignment: " + e.getMessage());
            e.printStackTrace();
        }
    }

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
}
