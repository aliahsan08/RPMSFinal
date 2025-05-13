module oopproject.rpmsfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.desktop;
    requires java.mail;
    requires java.sql;
    requires org.apache.pdfbox;

    exports oopproject.rpmsfinal.PatientControllers to javafx.fxml;
    exports oopproject.rpmsfinal;
    exports oopproject.rpmsfinal.DoctorControllers to javafx.fxml;

    opens oopproject.rpmsfinal to javafx.fxml;
    opens oopproject.rpmsfinal.PatientControllers to javafx.fxml;
    opens oopproject.rpmsfinal.DoctorControllers to javafx.fxml;
    exports oopproject.rpmsfinal.AdminControllers to javafx.fxml;
    opens oopproject.rpmsfinal.AdminControllers to javafx.fxml;
}
