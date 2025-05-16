package it.unibo.runwarrior.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.JumpState;
import it.unibo.runwarrior.view.GameLoopPanel;

public abstract class CharacterImpl implements Character{

    public static final int SIZE_CHARACTER = 96;
    public static final int START_Y = 384;
    public static final int START_X = 240;
    private static int MAX_JUMP = START_Y - (SIZE_CHARACTER*5/2);
    private static int MID_JUMP = START_Y - (SIZE_CHARACTER*3/2);
    
    private final int minScreenX = 0;//y IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    private int maxScreenX;//x IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    protected int playerX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLA MAPPA
    protected int playerY = START_Y;// * VERTICALE
    private int screenX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLO SCHERMO
    private int screenY = START_Y;// * VERITCALE (NON USATA PERCHè LA POSIZIONE VERTICALE è DATA SOLO DAL SALTO)
    protected Rectangle collisionArea;

    protected boolean rightDirection;
    private int speed = 5;
    private int speedJumpUP = 12; 
    private int speedJumpDown = 6;
    protected PlayerFrame playerFrame = PlayerFrame.STOP_FRAME;
    private int changeFrame = 0;
    private int groundX = 0;//variabile che permette lo scorrimento della mappa
    private boolean crossWalk = false;
    private int useAttackMoving = 0;

    protected BufferedImage right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL;

    public GameLoopPanel glp;
    public CharacterComand cmd;

    public CharacterImpl(GameLoopPanel panel, CharacterComand commands){
        this.glp = panel;
        this.cmd = commands;
        collisionArea = new Rectangle(playerX + 30, playerY + 20, 38,73);
        playerImage();
    }

    @Override
    public void update() {
        maxScreenX = glp.getWidth() / 2;
        if(cmd.getRight() && !cmd.getLeft()){
            rightDirection = true;
            playerX += speed;
            if(screenX < maxScreenX){
                screenX += speed;
            }
            if(screenX == maxScreenX){
                groundX -= speed;
            }
        }
        if(cmd.getLeft() && !cmd.getRight()){
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

        updatePlayerPosition();
        frameChanger();
    }

    private void updatePlayerPosition() {
        collisionArea.setLocation(playerX + 30, playerY + 20);
        updateAttackCollision();
    }

    public abstract void updateAttackCollision();

    public void frameChanger(){
        if(!cmd.getStop() && !cmd.isJumping()){
            changeFrame++;
            if(changeFrame > 8){
                switch(playerFrame){
                    case STOP_FRAME, ATTACK_FRAME -> {
                        playerFrame = crossWalk ? PlayerFrame.GO_FRAME1 : PlayerFrame.GO_FRAME2;
                        crossWalk = !crossWalk;
                    }
                    case GO_FRAME1, GO_FRAME2 -> playerFrame = PlayerFrame.STOP_FRAME;
                }
                changeFrame = 0;
            }
            useAttackMoving++;
            if(cmd.getAttack() && useAttackMoving > 60){ //numero di attacchi limitato se in movimento
                playerFrame = PlayerFrame.ATTACK_FRAME;
                useAttackMoving = 0;
            }
        }
        else if(cmd.isJumping()){
            playerFrame = PlayerFrame.GO_FRAME2;
            if(cmd.getAttack()){
                playerFrame = PlayerFrame.ATTACK_FRAME;
            }
        }
        else if(cmd.getAttack()){
            playerFrame = PlayerFrame.ATTACK_FRAME;
        }
        else if(cmd.getStop() && !cmd.isJumping() && !cmd.getAttack()){
            playerFrame = PlayerFrame.STOP_FRAME;
        }
    }

    @Override
    public void drawPlayer(Graphics2D gr2) {
        BufferedImage im = null;
        if(rightDirection){
            switch(playerFrame){
                case STOP_FRAME -> im = right0;
                case GO_FRAME1 -> im = right1;
                case GO_FRAME2 -> im = right2;
                case ATTACK_FRAME -> {
                    im = attackR;
                    gr2.drawImage(tipR, screenX + SIZE_CHARACTER, playerY, SIZE_CHARACTER, SIZE_CHARACTER, null);
                }
            }
        }
        else{
            switch(playerFrame){
                case STOP_FRAME -> im = left0;
                case GO_FRAME1 -> im = left1;
                case GO_FRAME2 -> im = left2;
                case ATTACK_FRAME -> {
                    im = attackR;
                    gr2.drawImage(tipL, screenX - SIZE_CHARACTER, playerY, SIZE_CHARACTER, SIZE_CHARACTER, null);
                }
            }
        }
        gr2.drawImage(im, screenX, playerY, SIZE_CHARACTER, SIZE_CHARACTER, null);
    }

    public void drawRectangle(Graphics2D gr){
        gr.drawRect(collisionArea.x, collisionArea.y, collisionArea.width, collisionArea.height);//si sposta in avanti perchè segue playerX non screenX
    }

    @Override
    public abstract void playerImage();

    @Override
    public void setLocationAfterPowerup(int x, int y, int realx) {
    }

    public int getGroundX(){
        return groundX;
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
