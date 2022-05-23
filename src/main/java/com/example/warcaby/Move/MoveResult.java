package com.example.warcaby.Move;

import com.example.warcaby.Elements.GamePawn;

public class MoveResult {
    private final MoveType type;

    public MoveType getType() {
        return type;
    }

    private final GamePawn gamePawn;

    public GamePawn getGamePawn() {
        return gamePawn;
    }

    public MoveResult(MoveType type) {
        this(type, null);
    }

    public MoveResult(MoveType type, GamePawn gamePawn) {
        this.type = type;
        this.gamePawn = gamePawn;
    }
}
