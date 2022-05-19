module com.example.warcaby {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.warcaby to javafx.fxml;
    exports com.example.warcaby;
}