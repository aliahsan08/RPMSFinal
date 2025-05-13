package oopproject.rpmsfinal.AdminControllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oopproject.rpmsfinal.DBConnection;
import oopproject.rpmsfinal.UserRow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ViewUsersController {
    @FXML private TableView<UserRow> usersTable;
    @FXML private TableColumn<UserRow, String> userIdCol;
    @FXML private TableColumn<UserRow, String> nameCol;
    @FXML private TableColumn<UserRow, String> phoneCol;
    @FXML private TableColumn<UserRow, String> emailCol;
    @FXML private TableColumn<UserRow, String> roleCol;

    @FXML
    private Button backButton;

    @FXML
    public void initialize() {
        userIdCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getUserId()));
        nameCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getName()));
        phoneCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getPhone()));
        emailCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getEmail()));
        roleCol.setCellValueFactory(r -> new ReadOnlyStringWrapper(r.getValue().getRole()));

        String query = """
        SELECT user_id, name, phone, email, 'Patient' AS role FROM patients
        UNION
        SELECT user_id, name, phone, email, 'Doctor' AS role FROM doctors
        UNION
        SELECT user_id, name, phone, email, 'Admin' AS role FROM administrators
    """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<UserRow> users = new ArrayList<>();

            while (rs.next()) {
                String id = rs.getString("user_id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String role = rs.getString("role");

                users.add(new UserRow(id, name, phone, email, role));
            }

            usersTable.getItems().setAll(users);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/oopproject/rpmsfinal/AdminFiles/Users.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
