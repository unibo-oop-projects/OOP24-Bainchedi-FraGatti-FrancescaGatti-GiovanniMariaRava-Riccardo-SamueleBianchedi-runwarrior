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

    protected boolean rightDirection = true;
    private int speed = 5;
    private int speedJumpUP = 12; 
    private int speedJumpDown = 6;
    private int groundX = 0;//variabile che permette lo scorrimento della mappa

    protected BufferedImage right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL;

    public GameLoopPanel glp;
    public CharacterComand cmd;
    protected CharacterAnimation animation;

    public CharacterImpl(GameLoopPanel panel, CharacterComand commands){
        this.glp = panel;
        this.cmd = commands;
        this.animation = new CharacterAnimation(commands);
        collisionArea = new Rectangle(playerX + 30, playerY + 20, 38,73);
        playerImage();
        animation.setImages(right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL);
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
        animation.frameChanger();
    }

    private void updatePlayerPosition() {
        collisionArea.setLocation(playerX + 30, playerY + 20);
        updateAttackCollision();
    }

    public abstract void updateAttackCollision();

    @Override
    public void drawPlayer(Graphics2D gr2) {
        BufferedImage im = null;
        BufferedImage tip = null;
        im = animation.imagePlayer(rightDirection);
        tip = animation.getTip(rightDirection);
        gr2.drawImage(tip, screenX + SIZE_CHARACTER, playerY, SIZE_CHARACTER, SIZE_CHARACTER, null);
        gr2.drawImage(im, screenX, playerY, SIZE_CHARACTER, SIZE_CHARACTER, null);
    }

    public void drawRectangle(Graphics2D gr){
        gr.drawRect(collisionArea.x, collisionArea.y, collisionArea.width, collisionArea.height);//si sposta in avanti perchè segue playerX non screenX
    }

    @Override
    public abstract void playerImage();

    @Override
    public void setLocationAfterPowerup(int x, int y, int realx) {
        this.playerY = y;
        this.playerX = realx;
        this.screenX = x;
    }

    public Rectangle getCollisionArea(){
        return collisionArea;
    }

    public int getGroundX(){
        return groundX;
    }

    public int getSpeed(){
        return speed;
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
