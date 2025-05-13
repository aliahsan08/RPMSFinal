package oopproject.rpmsfinal.PatientControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.sql.*;

public class PanicController {
    Patient patient = (Patient) SessionNative.getCurrentUser();
    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Text vitalsText;
    @FXML
    private TextField temperature;
    @FXML
    private TextField heartRate;
    @FXML
    private TextField systolicBP;
    @FXML
    private TextField diastolicBP;
    @FXML
    private TextField respirationRate;
    @FXML
    private TextField oxygenSaturation;
    EmailNotification emailer = EmailNotification.getInstance();
    private UserManager userManager = UserManager.getInstance();

    @FXML
    private void goBackHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButton() {
        try {
            VitalRecord record = new VitalRecord(
                    patient.getUserId(),
                    Double.parseDouble(temperature.getText()),
                    Integer.parseInt(heartRate.getText()),
                    Integer.parseInt(systolicBP.getText()),
                    Integer.parseInt(diastolicBP.getText()),
                    Integer.parseInt(respirationRate.getText()),
                    Integer.parseInt(oxygenSaturation.getText()),
                    ""  // Notes (optional)
            );

            // Generate UUID and current timestamp
            String recordId = record.getVitalsId();
            Timestamp currentTimestamp = Timestamp.valueOf(java.time.LocalDateTime.now());

            // Save to database
            String sql = "INSERT INTO vital_records " +
                    "(record_id, user_id, timestamp, temperature, heart_rate, systolic_bp, diastolic_bp, respiration_rate, oxygen_saturation, notes, alerts, is_critical) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, recordId);
                stmt.setString(2, record.getUserId());
                stmt.setTimestamp(3, currentTimestamp);
                stmt.setDouble(4, record.getTemperature());
                stmt.setInt(5, record.getHeartRate());
                stmt.setInt(6, record.getSystolicBP());
                stmt.setInt(7, record.getDiastolicBP());
                stmt.setInt(8, record.getRespirationRate());
                stmt.setDouble(9, record.getOxygenSaturation());
                stmt.setString(10, record.getNotes());
                stmt.setString(11, String.valueOf(record.getAlerts()));
                stmt.setBoolean(12, record.isCritical());

                stmt.executeUpdate();
            }

            if (record.isCritical()) {
                vitalsText.setText("PANIC BUTTON ACTIVATED");
                String email = fetchUserEmail(patient.getAssignedDoctor());
                String message = "The health of your patient " + patient.getUserId() + " " + patient.getName() + "is unstable. Check on them as soon as possible!";
                emailer.sendNotification(email,"Patient Health Critical!",message);
            } else {
                vitalsText.setText("Vitals Are Stable");
            }

        } catch (NumberFormatException e) {
            vitalsText.setText("Please enter valid numbers.");
        } catch (Exception e) {
            vitalsText.setText("Error saving vitals.");
            e.printStackTrace();
        }
    }
    private String fetchUserEmail(String userId) {
        Doctor doctor = userManager.getDoctorById(userId);
        return doctor.getEmail();
    }
}
