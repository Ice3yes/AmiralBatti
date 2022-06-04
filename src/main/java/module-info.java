module com.hakan.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.hakan.project to javafx.fxml;
    exports com.hakan.project;
}