module com.example.gestion_des_evenements {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;

    opens com.example.gestion_des_evenements to javafx.fxml;
    opens com.example.gestion_des_evenements.Evenement to javafx.fxml;
    exports com.example.gestion_des_evenements.Controller;
}
