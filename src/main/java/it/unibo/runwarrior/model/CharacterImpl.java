package it.unibo.runwarrior.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.view.GameLoopPanel;

public class CharacterImpl implements Character{

    public static final int SIZE_CHARACTER = 96;
    public static final int START_Y = 387;
    public static final int START_X = 320;
    public static final int maxScreenX = 550;//x IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    public int minScreenX = 180;//y IN CUI SI FERMA IL PLAYER NELLO SCHERMO (se raggiunge l'inizio mappa diventa 0)

    private int playerX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLA MAPPA
    private int playerY = START_Y;// * VERTICALE
    private int screenX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLO SCHERMO
    private int screenY = START_Y;// * VERITCALE (NON USATA PERCHè LA POSIZIONE VERTICALE è DATA SOLO DAL SALTO)
    private boolean rightDirection;
    private int speed = 5;
    private int speedJumpUP = 13; 
    private int speedJumpDown = 8;
    private int groundX = 0;//variabile che permette lo scorrimento della mappa
    private BufferedImage right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL;

    public GameLoopPanel glp;
    public CharacterComand cmd;

    public CharacterImpl(GameLoopPanel panel){
        this.glp = panel;
        playerImage();
    }

    @Override
    public void update() {
        if(cmd.getRight()){
            rightDirection = true;
            playerX += speed;
            if(screenX < maxScreenX){
                screenX += speed;
            }
            if(screenX == maxScreenX){
                groundX -= speed;
            }
        }
        if(cmd.getLeft()){
            rightDirection = false;
            if(screenX != 0){
                playerX -= speed;
            }
            if(screenX > minScreenX){
                screenX -= speed;
            }
            if(screenX == minScreenX && groundX < 0){
                groundX += speed;
            }
            minScreenX = groundX == 0 ? 0 : 180;
        }
    }

    @Override
    public void drawPlayer(Graphics2D gr2) {
    }

    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/stopRightN.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goRightN1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goRightN2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/stopLeftN.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goLeftN1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goLeftN2.png"));
            attackR = right0;
            attackL = left0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLocationAfterPowerup(int x, int y, int realx) {
    }

    @Override
    public int getPlX() {
        return this.playerX;
    }

    @Override
    public int getPlY() {
        return this.playerY;
    }

    @Override
    public int getScX() {
        return this.screenX;
    }

    @Override
    public int getScY() {
        return this.screenY;
    }
}
