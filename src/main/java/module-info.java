module com.example.warcaby {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.warcaby to javafx.fxml;
    exports com.example.warcaby;
    exports com.example.warcaby.Elements;
    opens com.example.warcaby.Elements to javafx.fxml;
    exports com.example.warcaby.Controller;
    opens com.example.warcaby.Controller to javafx.fxml;
    exports com.example.warcaby.Move;
    opens com.example.warcaby.Move to javafx.fxml;
}