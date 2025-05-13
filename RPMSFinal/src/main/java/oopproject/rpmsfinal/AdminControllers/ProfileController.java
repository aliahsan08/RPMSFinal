package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.Administrator;
import oopproject.rpmsfinal.Patient;
import oopproject.rpmsfinal.SessionNative;

public class ProfileController {

    @FXML
    private Button backButton;

    @FXML
    private Text profileText;

    @FXML
    public void initialize() {
        Administrator admin = (Administrator) SessionNative.getCurrentUser();
        profileText.setText(admin.toString());
    }

    @FXML
    private void goBackHome() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Home.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
