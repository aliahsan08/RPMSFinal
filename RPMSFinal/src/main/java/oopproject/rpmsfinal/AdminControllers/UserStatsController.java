package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import oopproject.rpmsfinal.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserStatsController {

    @FXML
    private Button backButton;

    @FXML
    private Text reportText;

    @FXML
    public void initialize() {
        String report = "";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            int patients = 0, doctors = 0, admins = 0;

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM patients");
            if (rs.next()) patients = rs.getInt("count");
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM doctors");
            if (rs.next()) doctors = rs.getInt("count");
            rs.close();

            rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM administrators");
            if (rs.next()) admins = rs.getInt("count");
            rs.close();

            int totalUsers = patients + doctors + admins;

            report = "Total Users: " + totalUsers + "\n"
                    + "Patients: " + patients + "\n"
                    + "Doctors: " + doctors + "\n"
                    + "Administrators: " + admins;

            reportText.setText(report);

        } catch (Exception e) {
            reportText.setText("Error generating user stats.");
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

