package com.example.warcaby;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static com.example.warcaby.MainHandler.PAWN_SIZE;

public class Quads extends Rectangle {

    private GamePawn gamePawn;


    public boolean hasPiece() {
        return gamePawn != null;
    }

    public GamePawn getPiece() {
        return gamePawn;
    }

    public void setPiece(GamePawn gamePawn) {
        this.gamePawn = gamePawn;
    }

    public Quads(boolean light, int x, int y) {
        setWidth(PAWN_SIZE);
        setHeight(PAWN_SIZE);

        relocate(x * PAWN_SIZE, y * PAWN_SIZE);

        setFill(light ? Color.valueOf("#ffc2ea") : Color.valueOf("#e8e1e6"));
    }                               //#ffcced
}