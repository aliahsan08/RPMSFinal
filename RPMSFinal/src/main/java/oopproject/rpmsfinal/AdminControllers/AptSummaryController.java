package oopproject.rpmsfinal.AdminControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Appointment;
import oopproject.rpmsfinal.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AptSummaryController {
    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment, String> apptIdCol;
    @FXML private TableColumn<Appointment, String> patientIdCol;
    @FXML private TableColumn<Appointment, String> doctorIdCol;
    @FXML private TableColumn<Appointment, String> purposeCol;
    @FXML private TableColumn<Appointment, String> statusCol;
    @FXML private TableColumn<Appointment, String> notesCol;
    @FXML private TableColumn<Appointment, String> dateTimeCol;

    @FXML
    private Button backButton;

    @FXML
    private Text reportText;

    @FXML
    public void initialize() {
        apptIdCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getAppointmentId()));
        patientIdCol.setCellValueFactory(a -> new  ReadOnlyStringWrapper(a.getValue().getPatientId()));
        doctorIdCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getDoctorId()));
        purposeCol.setCellValueFactory(a -> new ReadOnlyStringWrapper(a.getValue().getPurpose()));
        statusCol.setCellValueFactory(a -> new  ReadOnlyStringWrapper(a.getValue().getStatus().toString()));
        notesCol.setCellValueFactory(a -> new  ReadOnlyStringWrapper(a.getValue().getNotes()));
        dateTimeCol.setCellValueFactory(a -> new  ReadOnlyStringWrapper(a.getValue().getDateTime().toString()));

        List<Appointment> appointments = new ArrayList<>();

        String query = "SELECT * FROM appointments";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String apptId = rs.getString("appointment_id");
                String patientId = rs.getString("patient_id");
                String doctorId = rs.getString("doctor_id");
                String purpose = rs.getString("purpose");
                LocalDateTime dateTime = rs.getTimestamp("appointment_datetime").toLocalDateTime();

                // Construct using your full constructor
                Appointment appt = new Appointment(apptId, patientId, doctorId, dateTime, purpose);

                // Manually override status and notes since constructor sets defaults
                appt.setStatus(Appointment.AppointmentStatus.valueOf(rs.getString("status")));

                appointments.add(appt);
            }

            appointmentsTable.getItems().setAll(appointments);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Reports.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

