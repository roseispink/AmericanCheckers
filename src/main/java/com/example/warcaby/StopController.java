package com.example.warcaby;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class StopController {
    @FXML
    private Label winPlayer;
    @FXML
    private Label reason;

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void start(ActionEvent event) {

    }
    public final Label getWinLabel(){
        return winPlayer;
    }

    public final Label getReasonLabel(){
        return reason;
    }
}
