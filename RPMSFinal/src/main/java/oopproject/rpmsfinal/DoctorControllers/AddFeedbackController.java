package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.Doctor;
import oopproject.rpmsfinal.DoctorPatient.DoctorFeedback;
import oopproject.rpmsfinal.DoctorPatient;
import oopproject.rpmsfinal.SessionNative;
import java.sql.*;

public class AddFeedbackController {

    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField patientId;
    @FXML
    private TextField prescription;
    @FXML
    private TextField recommendation;
    @FXML
    private TextField diagnosis;

    @FXML
    private Text feedbackText;

    Doctor doctor = (Doctor) SessionNative.getCurrentUser();

    @FXML
    public void initialize() {
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButton() {

        String doctorId = doctor.getUserId();
        String patientIdText = patientId.getText();

        String checkQuery = "SELECT * FROM doctor_patient_assignments WHERE doctor_id = ? AND patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, doctorId);
            checkStmt.setString(2, patientIdText);

            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                feedbackText.setText("Doctor is not assigned to this patient.");
                return;
            }

            // 2. Create feedback object
            DoctorFeedback feedback = new DoctorFeedback(
                    doctorId,
                    patientIdText,
                    diagnosis.getText(),
                    recommendation.getText(),
                    prescription.getText()
            );

            // 3. Insert feedback into DB
            String insertQuery = "INSERT INTO doctor_feedback (feedback_id, doctor_id, patient_id, timestamp, diagnosis, recommendations, prescription) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, feedback.getFeedbackId());
                insertStmt.setString(2, feedback.getDoctorId());
                insertStmt.setString(3, feedback.getPatientId());
                insertStmt.setTimestamp(4, Timestamp.valueOf(feedback.getTimestamp()));
                insertStmt.setString(5, feedback.getDiagnosis());
                insertStmt.setString(6, feedback.getRecommendations());
                insertStmt.setString(7, feedback.getPrescription());

                insertStmt.executeUpdate();
                feedbackText.setText("Feedback added successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            feedbackText.setText("Error occurred while submitting feedback.");
        }
    }
}
