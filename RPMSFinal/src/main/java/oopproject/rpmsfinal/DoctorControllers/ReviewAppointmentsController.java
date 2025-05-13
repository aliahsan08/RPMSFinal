package oopproject.rpmsfinal.DoctorControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReviewAppointmentsController {

    @FXML
    private Button backButton;
    @FXML
    private Button acceptButton;
    @FXML
    private Button rejectButton;
    @FXML
    private TextField appointmentId;
    @FXML
    private Text infoText;
    @FXML
    private Text appointmentsText;
    @FXML
    private TableView<Appointment> appointmentsTable;
    @FXML
    private TableColumn<Appointment, String> apptIdCol;
    @FXML
    private TableColumn<Appointment, String> patientIdCol;
    @FXML
    private TableColumn<Appointment, String> dateTimeCol;
    @FXML
    private TableColumn<Appointment, String> statusCol;

    private final Doctor doctor = (Doctor) SessionNative.getCurrentUser();

    @FXML
    public void initialize() {
        apptIdCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getAppointmentId()));
        patientIdCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getPatientId()));
        dateTimeCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getDateTime().toString()));
        statusCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getStatus().toString()));

        List<Appointment> appointments = new ArrayList<>();
        String doctorId = doctor.getUserId();

        String query = "SELECT * FROM appointments WHERE doctor_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String apptId = rs.getString("appointment_id");
                String patId = rs.getString("patient_id");
                String docId = rs.getString("doctor_id");
                LocalDateTime dateTime = rs.getTimestamp("appointment_datetime").toLocalDateTime();
                String purpose = rs.getString("purpose");
                String statusStr = rs.getString("status");
                Appointment.AppointmentStatus status = Appointment.AppointmentStatus.valueOf(statusStr);
                Appointment appointment = new Appointment(apptId,patId,docId,dateTime,purpose);
                appointment.setStatus(status);
                appointments.add(appointment);
            }

            appointmentsTable.getItems().setAll(appointments);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAcceptButton() {
        updateAppointmentStatus("CONFIRMED");
    }

    @FXML
    private void handleRejectButton() {
        updateAppointmentStatus("CANCELLED");
    }

    private void updateAppointmentStatus(String newStatus) {
        String apptId = appointmentId.getText().trim();

        if (apptId.isEmpty()) {
            infoText.setText("Please enter an Appointment ID.");
            return;
        }

        String updateQuery = "UPDATE appointments SET status = ? WHERE appointment_id = ? AND doctor_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, newStatus);
            stmt.setString(2, apptId);
            stmt.setString(3, doctor.getUserId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                appointmentsText.setText("Appointment " + apptId + " marked as " + newStatus + ".");

            } else {
                appointmentsText.setText("Invalid Appointment ID or you are not assigned to it.");
            }

        } catch (Exception e) {
            infoText.setText("Failed to update appointment.");
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Appointments.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
