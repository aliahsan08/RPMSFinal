package oopproject.rpmsfinal.DoctorControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.ChatLauncher;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ChatController {
    private final ChatLauncher chatLauncher = new ChatLauncher();
    @FXML
    private Button joinButton;
    @FXML
    private Button backButton;
    @FXML
    private Text chatText;
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
        boolean success = chatLauncher.launchChat();  // opens default meeting link
        if (!success) {
            chatText.setText("Unable to launch chat");
        }
    }

}
