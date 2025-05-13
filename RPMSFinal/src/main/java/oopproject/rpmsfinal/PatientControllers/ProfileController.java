package oopproject.rpmsfinal.PatientControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Patient;
import oopproject.rpmsfinal.SessionNative;

public class ProfileController {

    @FXML
    private Button backButton;

    @FXML
    private Text profileText;

    @FXML
    public void initialize() {
        Patient patient = (Patient) SessionNative.getCurrentUser();
        profileText.setText(patient.toString());
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
