module com.example.womenshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens com.example.womenshop to javafx.fxml;
    exports com.example.womenshop;
}