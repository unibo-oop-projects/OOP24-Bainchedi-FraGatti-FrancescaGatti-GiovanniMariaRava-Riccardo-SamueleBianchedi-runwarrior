package it.unibo.runwarrior.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.JumpState;
import it.unibo.runwarrior.view.GameLoopPanel;

public abstract class CharacterImpl implements Character{

    public static final int SIZE_CHARACTER = 96;
    public static final int START_Y = 400;
    public static final int START_X = 320;
    private static int MAX_JUMP = START_Y - (SIZE_CHARACTER*5/2);
    private static int MID_JUMP = START_Y - (SIZE_CHARACTER*3/2);
    
    private final int maxScreenX = 600;//x IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    private final int minScreenX = 0;//y IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    private int playerX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLA MAPPA
    private int playerY = START_Y;// * VERTICALE
    private int screenX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLO SCHERMO
    private int screenY = START_Y;// * VERITCALE (NON USATA PERCHè LA POSIZIONE VERTICALE è DATA SOLO DAL SALTO)

    private boolean rightDirection;
    private int speed = 5;
    private int speedJumpUP = 13; 
    private int speedJumpDown = 8;
    private PlayerFrame playerFrame = PlayerFrame.STOP_FRAME;
    private int changeFrame = 0;
    private int groundX = 0;//variabile che permette lo scorrimento della mappa
    private boolean crossWalk = false;

    protected BufferedImage right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL;

    public GameLoopPanel glp;
    public CharacterComand cmd;

    public CharacterImpl(GameLoopPanel panel, CharacterComand commands){
        this.glp = panel;
        this.cmd = commands;
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
            if(screenX > 0){
                playerX -= speed;
            }
            if(screenX > minScreenX){
                screenX -= speed;
            }
        }
        if(cmd.getJump() == JumpState.START_JUMP){
            if(playerY > MAX_JUMP){ 
                playerY -= speedJumpUP;
            }
            if(playerY <= MAX_JUMP){
                playerY = MAX_JUMP;
                cmd.setJump(JumpState.DOWN_JUMP);
            }
        }
        if(cmd.getJump() == JumpState.MIN_JUMP){
            if(playerY > MID_JUMP){
                playerY -= speedJumpUP;
            } 
            if(playerY <= MID_JUMP){
                playerY = MID_JUMP;
                cmd.setJump(JumpState.DOWN_JUMP);
            }
        }
        if(cmd.getJump() == JumpState.DOWN_JUMP){
            if(playerY < START_Y){
                playerY += speedJumpDown;
            }
            if(playerY >= START_Y){
                playerY = START_Y;
                cmd.setJump(JumpState.STOP_JUMP);
            }
        }

        frameChanger();
    }

    public void frameChanger(){
        if((cmd.getRight() || cmd.getLeft()) && !cmd.isJumping()){
            changeFrame++;
            if(changeFrame > 8){
                if(playerFrame == PlayerFrame.STOP_FRAME){
                    playerFrame = crossWalk ? PlayerFrame.GO_FRAME1 : PlayerFrame.GO_FRAME2;
                    crossWalk = !crossWalk;
                }
                else if(playerFrame == PlayerFrame.GO_FRAME1){
                    playerFrame = PlayerFrame.STOP_FRAME;
                }
                else if(playerFrame == PlayerFrame.GO_FRAME2){
                    playerFrame = PlayerFrame.STOP_FRAME;
                }
                changeFrame = 0;
            }
        }
        else if(cmd.isJumping()){
            playerFrame = PlayerFrame.GO_FRAME2;
        }
        else if(!cmd.getRight() && !cmd.getLeft() && !cmd.isJumping()){
            playerFrame = PlayerFrame.STOP_FRAME;
        }
    }

    @Override
    public void drawPlayer(Graphics2D gr2) {
        BufferedImage im = null;
        if(rightDirection){
            if(playerFrame == PlayerFrame.STOP_FRAME){
                im = right0;
            }
            if(playerFrame == PlayerFrame.GO_FRAME1){
                im = right1;
            }
            if(playerFrame == PlayerFrame.GO_FRAME2){
                im = right2;
            }
        }
        else{
            if(playerFrame == PlayerFrame.STOP_FRAME){
                im = left0;
            }
            if(playerFrame == PlayerFrame.GO_FRAME1){
                im = left1;
            }
            if(playerFrame == PlayerFrame.GO_FRAME2){
                im = left2;
            }
        }
        gr2.drawImage(im, screenX, playerY, SIZE_CHARACTER, SIZE_CHARACTER, null);
    }

    @Override
    public abstract void playerImage();

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
