package it.unibo.runwarrior.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CollisionDetection;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;

public abstract class CharacterImpl implements Character{
    public static final int START_X = 96;
    private int startY;
    private int maxJump;
    private int midJump;
    protected int sizeCharacter;
    private int toTouchFloor = 2;
    
    private final int minScreenX = 0;//y IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    private int maxScreenX;//x IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    protected int playerX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLA MAPPA
    protected int playerY;// * VERTICALE
    private int screenX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLO SCHERMO
    private int screenY;// * VERITCALE (NON USATA PERCHè LA POSIZIONE VERTICALE è DATA SOLO DAL SALTO)
    protected Rectangle collisionArea;
    private boolean hitHead;

    protected boolean rightDirection = true;
    private int speed = 5;
    private int speedJumpUP = 12; 
    private int speedJumpDown = 6;
    private int groundX = 0;//variabile che permette lo scorrimento della mappa

    protected BufferedImage right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL;

    private GameLoopPanel glp;
    protected CharacterComand cmd;
    protected CharacterAnimation animation;
    private HandlerMapElement mapHandler;
    private CollisionDetection collisionDetection;

    public CharacterImpl(GameLoopPanel panel, CharacterComand commands, CollisionDetection collision, HandlerMapElement mapHandler){
        this.glp = panel;
        this.cmd = commands;
        playerImage();
        this.animation = new CharacterAnimation(commands, right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL);
        this.collisionDetection = collision;
        this.mapHandler = mapHandler;
        setStartY(mapHandler.getFirstY(), mapHandler.getTileSize());
    }

    private void setStartY(int y, int tileSize){
        startY = y + toTouchFloor;//542
        playerY = startY;
        screenY = startY;
        sizeCharacter = tileSize*2;
        maxJump = startY - (sizeCharacter*5/2);
        midJump = startY - (sizeCharacter*3/2);
        collisionArea = new Rectangle(playerX+(sizeCharacter/4), playerY+(sizeCharacter/4), sizeCharacter/2, sizeCharacter-(sizeCharacter/4)-toTouchFloor);
    }

    @Override
    public void update() {
        maxScreenX = glp.getWidth() / 2;
        String collisionDir = "";
        collisionDir = collisionDetection.checkCollision(this);
        if(cmd.getRight() && !cmd.getLeft()){
            rightDirection = true;
            if(!collisionDir.equals("right")){
                playerX += speed;
                if(screenX < maxScreenX){
                    screenX += speed;
                }
                else{
                    groundX -= speed;
                    mapHandler.setShift(groundX);
                }
            }
        }
        if(cmd.getLeft() && !cmd.getRight()){
            rightDirection = false;
            if(!collisionDir.equals("left")){
                if(screenX > 0){
                    playerX -= speed;
                }
                if(screenX > minScreenX){
                    screenX -= speed;
                }
            }
        }
        hitHead = collisionDir.equals("down") ? true : false;
        if(hitHead){
            cmd.setJump(false);
        }
        jump(cmd.isJumping(), maxJump);
        updatePlayerPosition();
        animation.frameChanger();
    }

    private void updatePlayerPosition() {
        collisionArea.setLocation(playerX + (sizeCharacter/4), playerY + (sizeCharacter/4));
        updateAttackCollision();
    }

    public void jump(boolean isJump, int jumpHeight){
        if(isJump){
            if(playerY > jumpHeight){
                playerY -= speedJumpUP;
            }
            else{
                playerY = jumpHeight;
                cmd.setJump(false);
            }
        }
        else{
            if(collisionDetection.isInAir(this)){
                playerY += speedJumpDown;
                updateJumpVariable();
            }
        }
    }

    public void updateJumpVariable(){
        maxJump = (startY - (sizeCharacter*5/2)) + (playerY - startY);
        midJump = (startY - (sizeCharacter*3/2)) + (playerY - startY);
    }

    public abstract void updateAttackCollision();

    @Override
    public void drawPlayer(Graphics2D gr2) {
        BufferedImage im = null;
        BufferedImage tip = null;
        im = animation.imagePlayer(rightDirection);
        tip = animation.getTip(rightDirection);
        gr2.drawImage(tip, screenX + sizeCharacter, playerY, sizeCharacter, sizeCharacter, null);
        gr2.drawImage(im, screenX, playerY, sizeCharacter, sizeCharacter, null);
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

    public Rectangle getArea(){
        return collisionArea;
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
