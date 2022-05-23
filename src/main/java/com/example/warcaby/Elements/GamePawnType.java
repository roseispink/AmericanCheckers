package com.example.warcaby.Elements;

public enum GamePawnType {
    PURPLE(1), WHITE(-1);

    public final int moveDir;
    GamePawnType(int moveDir) {
        this.moveDir = moveDir;
    }
}
