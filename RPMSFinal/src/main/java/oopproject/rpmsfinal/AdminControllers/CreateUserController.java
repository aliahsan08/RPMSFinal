package oopproject.rpmsfinal.AdminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import oopproject.rpmsfinal.SessionNative;
public class CreateUserController {

    @FXML
    private Button backButton;
    @FXML
    private TextField username;
    protected String usernameString;
    @FXML
    private TextField password;
    protected String passwordString;
    @FXML
    private TextField userId;
    protected String userIdString;
    @FXML
    private TextField name;
    protected String nameString;
    @FXML
    private TextField age;
    protected int ageInt;
    @FXML
    private TextField gender;
    protected String genderString;
    @FXML
    private TextField phone;
    protected String phoneString;
    @FXML
    private TextField email;
    protected String emailString;
    @FXML
    private Button patientButton;

    @FXML
    private Button doctorButton;

    @FXML
    private Button adminButton;

    @FXML
    private void goBack() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Users.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handlePatientButton() {
        try {
            SessionNative.setCreateUserType("patient");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreatePatient.fxml"));
            StackPane root = loader.load();
            CreatePatientController controller = loader.getController();
            controller.setUserData(
                    userId.getText(),
                    username.getText(),
                    password.getText(),
                    name.getText(),
                    Integer.parseInt(age.getText()),
                    gender.getText(),
                    phone.getText(),
                    email.getText()
            );
            Stage stage = (Stage) patientButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDoctorButton() {
        try {
            SessionNative.setCreateUserType("doctor");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreateDoctor.fxml"));
            StackPane root = loader.load();
            CreateDoctorController controller = loader.getController();
            controller.setUserData(
                    userId.getText(),
                    username.getText(),
                    password.getText(),
                    name.getText(),
                    Integer.parseInt(age.getText()),
                    gender.getText(),
                    phone.getText(),
                    email.getText()
            );
            Stage stage = (Stage) doctorButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdminButton() {
        try {
            SessionNative.setCreateUserType("administrator");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/CreateAdmin.fxml"));
            StackPane root = loader.load();
            CreateAdminController controller = loader.getController();
            controller.setUserData(
                    userId.getText(),
                    username.getText(),
                    password.getText(),
                    name.getText(),
                    Integer.parseInt(age.getText()),
                    gender.getText(),
                    phone.getText(),
                    email.getText()
            );
            Stage stage = (Stage) adminButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setUserData(String userId, String username, String password, String name,
                            int age, String gender, String phone, String email) {
        this.userIdString = userId;
        this.usernameString = username;
        this.passwordString = password;
        this.nameString = name;
        this.ageInt = age;
        this.genderString = gender;
        this.phoneString = phone;
        this.emailString = email;
    }

}
