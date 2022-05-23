package com.example.warcaby.Controller;

import com.example.warcaby.MainHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StopController {
    @FXML
    private Label winPlayer;
    @FXML
    private Label reason;

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    public final Label getWinLabel(){
        return winPlayer;
    }

    public final Label getReasonLabel(){
        return reason;
    }
}
