package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Doctor;
import oopproject.rpmsfinal.SessionNative;

public class ProfileController {

    @FXML
    private Button backButton;

    @FXML
    private Text profileText;

    @FXML
    public void initialize() {
        Doctor doctor = (Doctor) SessionNative.getCurrentUser();
        profileText.setText(doctor.toString());
    }

    @FXML
    private void goBackHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/DoctorFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

