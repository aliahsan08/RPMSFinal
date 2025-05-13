package oopproject.rpmsfinal.DoctorControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatientsController {
    @FXML private TableView<PatientSummary> patientsTable;
    @FXML private TableColumn<PatientSummary, String> pidCol;
    @FXML private TableColumn<PatientSummary, String> pinCol;
    @FXML
    private Button backButton;

    @FXML
    private Text patientsText;

    Doctor doctor = (Doctor) SessionNative.getCurrentUser();

    @FXML
    public void initialize() {

        pidCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getUserId()));
        pinCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getName()));
        viewAssignedPatients(doctor);
    }

    private void viewAssignedPatients(Doctor doctor) {
        List<PatientSummary> summaries = new ArrayList<>();

        String query = """
        SELECT p.user_id, p.name
        FROM doctor_patient_assignments dpa
        JOIN patients p ON dpa.patient_id = p.user_id
        WHERE dpa.doctor_id = ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, doctor.getUserId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String patientId = rs.getString("user_id");
                String patientName = rs.getString("name");
                summaries.add(new PatientSummary(patientId, patientName));
            }

            patientsTable.getItems().setAll(summaries);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goBackHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
