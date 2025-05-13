package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.MeetingLauncher;

public class VideoCallController {

    private final MeetingLauncher meetingLauncher = new MeetingLauncher();
    @FXML
    private Button backButton;
    @FXML
    private Button joinButton;
    @FXML
    private Text videoCallText;

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
    @FXML
    private void handleJoinButton() {
        boolean success = meetingLauncher.launchMeeting();  // opens default meeting link
        if (!success) {
            videoCallText.setText("Unable to launch meeting");
        }
    }

}
