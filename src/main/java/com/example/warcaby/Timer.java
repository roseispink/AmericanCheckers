package com.example.warcaby;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Timer extends AnimationTimer {

    private long startTime;
    private boolean isRunning = false;
    private final long TIMELIMIT = 5000;
    BooleanProperty running = new SimpleBooleanProperty();
    static DoubleProperty time1 = new SimpleDoubleProperty();
    static DoubleProperty time2 = new SimpleDoubleProperty();
    Label l1;
    Label l2;
    GamePawnType gamePawn;
    Timer(Label label1, Label label2) {
        l1 = label1;
        l2 = label2;
        l1.textProperty().bind(time1.asString("%.3f s"));
        l2.textProperty().bind(time2.asString("%.3f s"));
    }
    void setGamePawn(GamePawnType pawn){
        gamePawn = pawn;
    }
    Double returnTime(){
        return gamePawn==GamePawnType.PURPLE ? time1.doubleValue() : time2.doubleValue();
    }
    GamePawnType getGamePawnType(){
        return this.gamePawn;
    }
    boolean isRunning(){
        return this.isRunning;
    }
    boolean isStopGame(){return true;}
    @Override
    public void start() {
        isRunning = true;
        startTime = System.currentTimeMillis();
        running.set(true);
        super.start();
    }

    @Override
    public void stop() {
        isRunning = false;
        running.set(false);
        super.stop();
    }

    @Override
    public void handle(long timestamp) {
        long now = System.currentTimeMillis();
        if(now - startTime >= TIMELIMIT) {
            Stage stage = new Stage();
            try {
                StopGame stopGame = new StopGame();
                stopGame.setReason();
                stopGame.setType(gamePawn == GamePawnType.PURPLE ? GamePawnType.WHITE : GamePawnType.PURPLE);
                stopGame.start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stop();
        }
        if(gamePawn==GamePawnType.PURPLE) time1.set(((now - startTime) / 1000.0));
        else if(gamePawn==GamePawnType.WHITE) time2.set(((now - startTime) / 1000.0));
    }
}
