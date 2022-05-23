package com.example.warcaby;

import com.example.warcaby.Controller.StopController;
import com.example.warcaby.Elements.GamePawnType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class StopGame extends Application {
    GamePawnType type;
    boolean reason = true;

    void setType(GamePawnType gamePawnType){
        type = gamePawnType;
    }

    void setReason(){
       reason = false;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StopGame.class.getResource("the-end.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 240);
        stage.setScene(scene);
        Paint paint;
        if(type == GamePawnType.PURPLE){
            paint = Color.valueOf("#613b87");
            ((StopController)fxmlLoader.getController()).getWinLabel().setText("FIOLETOWY");
        }else {
            paint = Color.valueOf("#7fb8b7");
            ((StopController)fxmlLoader.getController()).getWinLabel().setText("BIAŁY");
        }
        if(reason) ((StopController)fxmlLoader.getController()).getReasonLabel().setText("Wszystkie pionki zebrane");
        else ((StopController)fxmlLoader.getController()).getReasonLabel().setText("Czas na ruch upłynął");
        ((StopController)fxmlLoader.getController()).getWinLabel().setTextFill(paint);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
