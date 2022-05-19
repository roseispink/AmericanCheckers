package src;

import javax.swing.*;
import java.awt.*;

public class Board extends JFrame {
    public static final int BOARD_DIMENTION = 8;
    Rectangle rec = new Rectangle(100, 80, 90, 90);
    int [][] quadBoardX = new int[BOARD_DIMENTION][BOARD_DIMENTION];
    int [][] quadBoardY = new int[BOARD_DIMENTION][BOARD_DIMENTION];
    boolean [][] isBusy = new boolean[BOARD_DIMENTION][BOARD_DIMENTION];
    int PAWN_SIZE = 35;
    Board(){
        initComponents();
        initBoard();
        setVisible(true);

    }
    void initComponents(){
        this.setLocation(300, 0);
        this.setSize(BOARD_DIMENTION *rec.width+2*rec.x, BOARD_DIMENTION *rec.height+2*rec.y);
        this.getContentPane();
        this.setTitle("Warcaby");
        this.setBackground(Color.LIGHT_GRAY);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void initBoard(){
        for(int i = 0; i < BOARD_DIMENTION; i++){
            for(int j = 0; j < BOARD_DIMENTION; j++){
                quadBoardX[i][j] = rec.x + j * rec.width;
                quadBoardY[i][j] = rec.y + i * rec.height;
                if(i<3 || i>4){
                    isBusy[i][j] = true;
                }
            }
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < BOARD_DIMENTION; i++) {
            for(int j = 0; j< BOARD_DIMENTION; j++){
                if(i%2==0) {
                    if (j % 2 == 0) g.setColor(Color.PINK);
                    else g.setColor(Color.DARK_GRAY);
                }else{
                    if (j % 2 == 0) g.setColor(Color.DARK_GRAY);
                    else g.setColor(Color.PINK);
                }
                g.fillRect(quadBoardX[i][j], quadBoardY[i][j], rec.width, rec.height);
                if(i<3) {
                    g.setColor(Color.BLACK);
                    g.fillOval((rec.x + j * rec.width)+18, (rec.y + i * rec.height)+18, rec.width-PAWN_SIZE, rec.height-PAWN_SIZE);
                    g.setColor(Color.WHITE);
                    g.drawOval((rec.x + j * rec.width)+18, (rec.y + i * rec.height)+18, rec.width-PAWN_SIZE, rec.height-PAWN_SIZE);
                }else if(i>4){
                    g.setColor(Color.WHITE);
                    g.fillOval((rec.x + j * rec.width)+18, (rec.y + i * rec.height)+18, rec.width-PAWN_SIZE, rec.height-PAWN_SIZE);
                    g.setColor(Color.BLACK);
                    g.drawOval((rec.x + j * rec.width)+18, (rec.y + i * rec.height)+18, rec.width-PAWN_SIZE, rec.height-PAWN_SIZE);
                }
            }
        }

    }
}
