package com.example.warcaby;

public enum GamePawnType {
    PURPLE(1), WHITE(-1);

    final int moveDir;
    GamePawnType(int moveDir) {
        this.moveDir = moveDir;
    }
}
