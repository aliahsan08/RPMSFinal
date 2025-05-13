package oopproject.rpmsfinal.PatientControllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import oopproject.rpmsfinal.DoctorPatient.DoctorFeedback;

import javax.print.Doc;

public class FeedbackController {
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    @FXML
    private Button backButton;

    @FXML
    private Text feedbackText;
    @FXML private TableView<DoctorFeedback> feedbackTable;
    @FXML private TableColumn<DoctorFeedback, String> drCol;
    @FXML private TableColumn<DoctorFeedback, String> tsCol;
    @FXML private TableColumn<DoctorFeedback, String> recCol;
    @FXML private TableColumn<DoctorFeedback, String> presCol;
    @FXML private TableColumn<DoctorFeedback, String> dgCol;
    @FXML
    public void initialize() {
        List<DoctorFeedback> feedbacks = new ArrayList<>();
        drCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getDoctorId()));
        tsCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getTimestamp().format(DF)));
        dgCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getDiagnosis()));
        recCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getRecommendations()));
        presCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getPrescription()));
        String patientId = SessionNative.getUserId();
        DoctorFeedback feedback = null;

        String query = "SELECT * FROM doctor_feedback WHERE patient_id = ? ";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                String doctorId = rs.getString("doctor_id");
                String diagnosis = rs.getString("diagnosis");
                String recommendations = rs.getString("recommendations");
                String prescription = rs.getString("prescription");
                LocalDateTime timestamp = rs.getTimestamp("timestamp").toLocalDateTime();

                feedbacks.add(new DoctorFeedback(doctorId, patientId, diagnosis, recommendations, prescription));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (feedbacks.isEmpty()) {

            feedbackText.setText("No vital records found.");
            feedbackTable.getItems().clear();
        } else {
            feedbackTable.setItems(FXCollections.observableArrayList(feedbacks));
        }
    }

    @FXML
    private void goBackHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/PatientFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
