package com.example.warcaby;

import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;

import java.io.IOException;


public class MainHandler extends Application {

    public static final int PAWN_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private final Quads[][] board = new Quads[WIDTH][HEIGHT];

    private final Group quadGroup = new Group();
    private final Group pawnGroup = new Group();

    private final Label player1= new Label("FIOLETOWY: ");
    private final Label totalTime = new Label("Czas całkowity:");
    private final Label roundTime = new Label("Obecna runda:");
    private final Label totalTime2 = new Label("Czas całkowity:");
    private final Label roundTime2 = new Label("Obecna runda:");
    private final Label player2 = new Label("BIAŁY: ");

    private final Button setClock = new Button("Zegar");
    private static final Label timeP1= new Label();
    private static final Label timeAll1 = new Label();
    private static final Label timeP2= new Label();
    private static final Label timeAll2 = new Label();
    private static Double t1 = 0.0;
    private static Double t2 = 0.0;



    private final Timer timer = new Timer(timeP1, timeP2);
    private final StopGame stopGame = new StopGame();
    Stage stage = new Stage();

    private short moveQueue = 0;
    private short whitePawns = 8;
    private short purplePawns = 8;

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * PAWN_SIZE +200, HEIGHT * PAWN_SIZE);
        root.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(quadGroup, pawnGroup,
                timeP1, timeP2,
                player1, player2,
                totalTime, roundTime,
                totalTime2, roundTime2,
                timeAll1, timeAll2, setClock);
        player1.relocate(WIDTH* PAWN_SIZE +40, 100);
        roundTime.relocate(WIDTH* PAWN_SIZE + 60, 140);
        timeP1.relocate(WIDTH* PAWN_SIZE +80, 180);
        totalTime.relocate(WIDTH* PAWN_SIZE +60, 220);
        timeAll1.relocate(WIDTH* PAWN_SIZE +85, 260);

        setClock.relocate(WIDTH*PAWN_SIZE + 85, 360);

        player2.relocate(WIDTH* PAWN_SIZE +70, 500);
        roundTime2.relocate(WIDTH* PAWN_SIZE + 60, 540);
        timeP2.relocate(WIDTH* PAWN_SIZE +80, 580);
        totalTime2.relocate(WIDTH* PAWN_SIZE +60, 620);
        timeAll2.relocate(WIDTH* PAWN_SIZE +85, 660);

        timeAll1.setText(String.format("%.2f s", t1));
        timeAll2.setText(String.format("%.2f s", t2));

        player1.setAlignment(Pos.CENTER);
        player1.setTextFill(Color.valueOf("#5834eb"));
        player1.setFont(new Font("Arial", 22));

        player2.setAlignment(Pos.CENTER);
        player2.setTextFill(Color.valueOf("#ffffff"));
        player2.setFont(new Font("Arial", 22));

        setClock.setPrefSize(70, 40);
        setClock.setBackground(new Background(new BackgroundFill(Color.LIGHTSKYBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        setClock.setFont(new Font("Arial", 13));
        setClock.setOnAction(event -> {
            if (timer.isRunning()) {
                System.out.println("Stop");
                timer.stop();
                if (timer.getGamePawnType() == GamePawnType.PURPLE) {
                    t1 += timer.returnTime();
                    timeAll1.setText(String.format("%.2f s", t1));
                } else {
                    t2 += timer.returnTime();
                    timeAll2.setText(String.format("%.2f s", t2));
                }
            }

            if (moveQueue % 2 == 0) timer.setGamePawn(GamePawnType.WHITE);
            else timer.setGamePawn(GamePawnType.PURPLE);
            timer.start();
        });


        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Quads quads = new Quads((x + y) % 2 == 0, x, y);
                board[x][y] = quads;

                quadGroup.getChildren().add(quads);

                GamePawn gamePawn = null;

                if (y <= 2 && (x + y) % 2 != 0) {
                    gamePawn = makePiece(GamePawnType.PURPLE, x, y);
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    gamePawn = makePiece(GamePawnType.WHITE, x, y);
                }

                if (gamePawn != null) {
                    quads.setPiece(gamePawn);
                    pawnGroup.getChildren().add(gamePawn);
                }
            }
        }

        return root;
    }

    private MoveResult tryMove(GamePawn gamePawn, int newX, int newY) {
        if (board[newX][newY].hasPiece()) {
            return new MoveResult(MoveType.NONE);
        }

        if((newX + newY) % 2 == 0){
            return new MoveResult(MoveType.NONE);
        }


        int x0 = toBoard(gamePawn.getOldX());
        int y0 = toBoard(gamePawn.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == gamePawn.getType().moveDir) {
            checkQueen(gamePawn, newX, newY);
            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == gamePawn.getType().moveDir * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != gamePawn.getType()) {
                checkQueen(gamePawn, newX, newY);
                if(board[x1][y1].getPiece().getType()==GamePawnType.WHITE) whitePawns--;
                else purplePawns--;
                checkEnd();
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }else if(gamePawn.getBonus() && Math.abs(newX - x0) == 1){
            return new MoveResult(MoveType.NORMAL);
        }else if(gamePawn.getBonus() && Math.abs(newX - x0) == 2) {
            int x1 = Math.abs(x0 + (newX - x0) / 2);
            int y1 = Math.abs(y0 + (newY - y0) / 2);

            if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != gamePawn.getType()) {
                if(board[x1][y1].getPiece().getType()==GamePawnType.WHITE) whitePawns--;
                else purplePawns--;
                checkEnd();
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }

        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int)(pixel + PAWN_SIZE / 2) / PAWN_SIZE;
    }

    private void checkEnd(){
        if(whitePawns==0){
            stopGame.setType(GamePawnType.PURPLE);
            try {
                stopGame.start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(purplePawns==0){
            stopGame.setType(GamePawnType.WHITE);
            try {
                stopGame.start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkQueen(GamePawn gamePawn, int newX, int newY){
        if(gamePawn.getType() == GamePawnType.WHITE && newY == 0 && (newX == 1 || newX == 3 || newX == 5 || newX == 7)) gamePawn.setBonus(true);
        else if(gamePawn.getType() == GamePawnType.PURPLE  && newY == 7 && (newX == 0 || newX == 2 || newX == 4 || newX == 6) ) {
            gamePawn.setBonus(true);
        }
    }


    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Warcaby");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GamePawn makePiece(GamePawnType type, int x, int y) {
        GamePawn gamePawn = new GamePawn(type, x, y);
            gamePawn.setOnMouseReleased(e -> {

                int newX = toBoard(gamePawn.getLayoutX());
                int newY = toBoard(gamePawn.getLayoutY());

                MoveResult result;

                if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                    result = new MoveResult(MoveType.NONE);
                } else {
                    result = tryMove(gamePawn, newX, newY);
                }

                int x0 = toBoard(gamePawn.getOldX());
                int y0 = toBoard(gamePawn.getOldY());

                if((type==GamePawnType.PURPLE && moveQueue % 2!= 0) || (type==GamePawnType.WHITE && moveQueue % 2 ==0)){
                    switch (result.getType()) {
                        case NONE:
                            gamePawn.abortMove();
                            break;
                        case NORMAL:
                            gamePawn.move(newX, newY);
                            board[x0][y0].setPiece(null);
                            board[newX][newY].setPiece(gamePawn);
                            moveQueue++;
                            break;
                        case KILL:
                            gamePawn.move(newX, newY);
                            board[x0][y0].setPiece(null);
                            board[newX][newY].setPiece(gamePawn);

                            GamePawn otherGamePawn = result.getGamePawn();
                            int otherX = toBoard(otherGamePawn.getOldX());
                            int otherY = toBoard(otherGamePawn.getOldY());
                            board[otherX][otherY].setPiece(null);
                            pawnGroup.getChildren().remove(otherGamePawn);
                            moveQueue++;
                            break;
                    }
                }else gamePawn.abortMove();

            });

        return gamePawn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}