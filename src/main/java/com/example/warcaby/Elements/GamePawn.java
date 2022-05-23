package com.example.warcaby.Elements;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static com.example.warcaby.MainHandler.PAWN_SIZE;


public class GamePawn extends StackPane {

    private final GamePawnType type;

    private boolean bonus = false;

    private double mouseX, mouseY;
    private double oldX, oldY;

    private final Ellipse ellipse = new Ellipse(PAWN_SIZE * 0.3125, PAWN_SIZE * 0.26);

    public GamePawnType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public boolean getBonus(){return bonus;}

    public void setBonus(boolean bonus){
        ellipse.setFill(type == GamePawnType.PURPLE ? Color.valueOf("#1a001a") : Color.valueOf("#e6b800"));
        this.bonus = bonus;
    }

    public GamePawn(GamePawnType type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse bg = new Ellipse(PAWN_SIZE * 0.3125, PAWN_SIZE * 0.26);
        bg.setFill(Color.DARKGRAY);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(PAWN_SIZE * 0.01);

        bg.setTranslateX((PAWN_SIZE - PAWN_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((PAWN_SIZE - PAWN_SIZE * 0.26 * 2) / 2 + PAWN_SIZE * 0.07);


        ellipse.setFill(type == GamePawnType.PURPLE ? Color.valueOf("#594399") : Color.valueOf("#fcfcfc"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(PAWN_SIZE * 0.01);

        ellipse.setTranslateX((PAWN_SIZE - PAWN_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((PAWN_SIZE - PAWN_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * PAWN_SIZE;
        oldY = y * PAWN_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}