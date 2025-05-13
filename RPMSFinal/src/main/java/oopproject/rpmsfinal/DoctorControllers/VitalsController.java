package oopproject.rpmsfinal.DoctorControllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oopproject.rpmsfinal.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VitalsController {

    @FXML private Button backButton;
    @FXML private Button downloadPdfButton;
    @FXML private Button fetchVitalsButton;
    @FXML private Button viewTrends;
    @FXML private TextField patientIdField;
    @FXML private Text statusText;

    @FXML private TableView<VitalRecord> vitalsTable;
    @FXML private TableColumn<VitalRecord, String> tsCol;
    @FXML private TableColumn<VitalRecord, Double> tempCol;
    @FXML private TableColumn<VitalRecord, Integer> hrCol;
    @FXML private TableColumn<VitalRecord, String> bpCol;
    @FXML private TableColumn<VitalRecord, Integer> respCol;
    @FXML private TableColumn<VitalRecord, Double> oxCol;
    @FXML private TableColumn<VitalRecord, String> statusCol;

    private final Doctor doctor = (Doctor) SessionNative.getCurrentUser();
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {
        // Wire up cell value factories
        tsCol   .setCellValueFactory(r ->
                new ReadOnlyStringWrapper(r.getValue().getTimestamp().format(DF))
        );
        tempCol .setCellValueFactory(r ->
                new ReadOnlyObjectWrapper<>(r.getValue().getTemperature())
        );
        hrCol   .setCellValueFactory(r ->
                new ReadOnlyObjectWrapper<>(r.getValue().getHeartRate())
        );
        bpCol   .setCellValueFactory(r ->
                new ReadOnlyStringWrapper(
                        r.getValue().getSystolicBP() + "/" + r.getValue().getDiastolicBP()
                )
        );
        respCol .setCellValueFactory(r ->
                new ReadOnlyObjectWrapper<>(r.getValue().getRespirationRate())
        );
        oxCol   .setCellValueFactory(r ->
                new ReadOnlyObjectWrapper<>(r.getValue().getOxygenSaturation())
        );
        statusCol.setCellValueFactory(r ->
                new ReadOnlyStringWrapper(r.getValue().isCritical() ? "CRITICAL" : "Normal")
        );
    }

    @FXML
    private void handleFetchVitals() {
        String patientId = patientIdField.getText().trim();
        statusText.setText("");

        if (patientId.isEmpty()) {
            statusText.setText("Please enter a Patient ID.");
            return;
        }

        List<VitalRecord> records = VitalRecord.getPatientVitals(patientId);

        if (records.isEmpty()) {
            statusText.setText("No vital records found.");
            vitalsTable.getItems().clear();
        } else {
            vitalsTable.setItems(FXCollections.observableArrayList(records));
        }
    }


    @FXML
    private void handleViewTrends() {
        // assume trends action is handled elsewhere…
        String pid = patientIdField.getText().trim();
        if (!pid.isEmpty()) {
            oopproject.rpmsfinal.HealthTrends.getInstance().showPatientTrends(pid);
        }
    }
    @FXML
    private void handleDownloadPdf() {
        // 1) grab what's currently in the table
        List<VitalRecord> records = new ArrayList<>(vitalsTable.getItems());
        if (records.isEmpty()) {
            statusText.setText("No vitals to export.");
            return;
        }

        // 2) ask user where to save
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Vitals Report");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = chooser.showSaveDialog(downloadPdfButton.getScene().getWindow());
        if (file == null) return;

        // 3) export via your ReportGenerator
        try {
            ReportGenerator.getInstance()
                    .exportVitalsReportPdf(patientIdField.getText(), records, file);
            statusText.setText("PDF saved: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
            statusText.setText("Failed to save PDF.");
        }
    }

    @FXML
    private void goBackHome() {
        try {
            StackPane root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
