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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.*;

public class AppointmentsController {
    private final UserManager userManager = UserManager.getInstance();
    Patient patient = (Patient) SessionNative.getCurrentUser();
    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Text appointmentsText;
    @FXML
    private TextField doctorId;
    @FXML
    private TextField purpose;
    @FXML
    private TextField date;
    @FXML
    private TextField time;
    @FXML
    private Text displayOptions;

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

                options += String.format("%d. Dr. %s %s (%s)\n", i++, userId, name, specialization);
            }

            displayOptions.setText(options);

        } catch (SQLException e) {
            e.printStackTrace();
            displayOptions.setText("Error loading doctor list.");
        }
    }




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
            LocalDateTime dateTime = LocalDateTime.parse(
                    date.getText() + " " + time.getText(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            );



            Appointment appointment = new Appointment(
                    patient.getUserId(),
                    doctorId.getText(),
                    dateTime,
                    purpose.getText()
            );

            // Save to database
            String query = "INSERT INTO appointments (appointment_id, patient_id, doctor_id, appointment_datetime, purpose, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, appointment.getAppointmentId());
                stmt.setString(2, appointment.getPatientId());
                stmt.setString(3, appointment.getDoctorId());
                stmt.setTimestamp(4, Timestamp.valueOf(appointment.getDateTime()));
                stmt.setString(5, appointment.getPurpose());
                stmt.setString(6, String.valueOf(appointment.getStatus())); // Should be "REQUESTED" by default

                stmt.executeUpdate();
                appointmentsText.setText("Appointment Added Successfully");

            } catch (Exception e) {
                e.printStackTrace();
                appointmentsText.setText("Database error occurred.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            appointmentsText.setText("Invalid input. Use dd/MM/yyyy for date and HH:mm for time.");
        }
    }

}
