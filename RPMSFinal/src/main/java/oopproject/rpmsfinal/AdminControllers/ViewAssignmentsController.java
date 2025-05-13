package oopproject.rpmsfinal.AdminControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.DPAssignment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewAssignmentsController {

    @FXML private Button backButton;
    @FXML private TableView<DPAssignment> assignmentTable;
    @FXML private TableColumn<DPAssignment, Integer> doctorIdCol;
    @FXML private TableColumn<DPAssignment, Integer> patientIdCol;

    @FXML
    public void initialize() {
        doctorIdCol.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
        patientIdCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));

        ObservableList<DPAssignment> assignments = FXCollections.observableArrayList();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT doctor_id, patient_id FROM doctor_patient_assignments")) {

            while (rs.next()) {
                String doctorId = rs.getString("doctor_id");
                String patientId = rs.getString("patient_id");
                assignments.add(new DPAssignment(doctorId, patientId));
            }

            assignmentTable.setItems(assignments);

        } catch (Exception e) {
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
