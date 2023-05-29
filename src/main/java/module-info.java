module org.example.fxapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires bcrypt;
    requires java.sql;
    requires gson;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.desktop;


    opens org.example.fxapplication to javafx.fxml;
    opens org.example.fxapplication.Models to javafx.fxml, javafx.base;
    opens org.example.fxapplication.Controllers to javafx.fxml;
    opens org.example.fxapplication.Views to javafx.fxml;
    opens org.example.fxapplication.Services to javafx.base, javafx.fxml;

    exports org.example.fxapplication;
    exports org.example.fxapplication.Controllers;
    exports org.example.fxapplication.Services;
    exports org.example.fxapplication.Views;
}