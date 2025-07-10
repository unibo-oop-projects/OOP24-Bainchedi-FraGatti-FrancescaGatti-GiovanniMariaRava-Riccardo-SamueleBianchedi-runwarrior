package it.unibo.runwarrior.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import it.unibo.runwarrior.controller.CharacterAnimationHandler;
import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CharacterMovementHandler;
import it.unibo.runwarrior.controller.CollisionDetection;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;

public abstract class CharacterImpl implements Character{
    // public static final int START_X = 96;
    // private int startY;
    // private int maxJump;
    // private int midJump;
    protected int sizeCharacter;
    protected int toTouchFloor = 2;
    
    // private final int minScreenX = 0;//y IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    // private int maxScreenX;//x IN CUI SI FERMA IL PLAYER NELLO SCHERMO
    // protected int playerX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLA MAPPA
    // protected int playerY;// * VERTICALE
    // private int screenX = START_X;//POSIZIONE ORIZZONTALE DEL PLAYER NELLO SCHERMO
    // private int screenY;// * VERITCALE (NON USATA PERCHè LA POSIZIONE VERTICALE è DATA SOLO DAL SALTO)
    protected Rectangle collisionArea;
    // private boolean hitHead;
    // private boolean jumpKill;
    // private boolean descend;
    // private boolean handleDoubleCollision;

    protected boolean rightDirection;
    private int speed = 5;
    // private int speedJumpUP = 12; 
    // private int speedJumpDown = 6;
    // private int groundX = 0;//variabile che permette lo scorrimento della mappa

    protected BufferedImage right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL;

    private GameLoopPanel glp;
    protected CharacterComand cmd;
    protected CharacterAnimationHandler animation;
    protected CharacterMovementHandler movement;
    private HandlerMapElement mapHandler;
    private CollisionDetection collisionDetection;
    private List<Rectangle> enemies; // da mettere nel KillDetection

    public CharacterImpl(GameLoopPanel panel, CharacterComand commands, CollisionDetection collision, HandlerMapElement mapHandler){
        this.glp = panel;
        this.cmd = commands;
        playerImage();
        this.animation = new CharacterAnimationHandler(commands, right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL);
        this.collisionDetection = collision;
        this.mapHandler = mapHandler;
        setStartY(mapHandler.getFirstY(), mapHandler.getTileSize());
        this.movement = new CharacterMovementHandler(panel, this, commands, collision, mapHandler);
        collisionArea = new Rectangle(movement.getPlX()+(sizeCharacter/4), movement.getPlY()+(sizeCharacter/4),
                                        sizeCharacter/2, sizeCharacter-(sizeCharacter/4)-toTouchFloor);
    }

    private void setStartY(int y, int tileSize){
        sizeCharacter = tileSize*2;
    }

    @Override
    public void update() {
        this.rightDirection = movement.getRightDirection();
        movement.movePlayer();
        System.out.println((collisionArea.x + collisionArea.width));
        animation.frameChanger();
    }

    public void updatePlayerPosition() {
        collisionArea.setLocation(movement.getPlX() + (sizeCharacter/4), movement.getPlY() + (sizeCharacter/4));
        updateAttackCollision();
    }

    public abstract void updateAttackCollision();

    @Override
    public void drawPlayer(Graphics2D gr2) {
        BufferedImage im = null;
        BufferedImage tip = null;
        im = animation.imagePlayer(rightDirection);
        tip = animation.getTip(rightDirection);
        gr2.drawImage(tip, movement.getScX() + sizeCharacter, movement.getPlY(), sizeCharacter, sizeCharacter, null);
        gr2.drawImage(im, movement.getScX(), movement.getPlY(), sizeCharacter, sizeCharacter, null);
    }

    public void drawRectangle(Graphics2D gr){
        gr.drawRect(collisionArea.x, collisionArea.y, collisionArea.width, collisionArea.height);//si sposta in avanti perchè segue playerX non screenX
    }

    @Override
    public abstract void playerImage();

    @Override
    // public void setLocationAfterPowerup(int x, int y, int realx) {
    //     this.playerY = y;
    //     this.playerX = realx;
    //     this.screenX = x;
    // }

    public Rectangle getArea(){
        return collisionArea;
    }

    public int getSpeed(){
        return speed;
    }
}
