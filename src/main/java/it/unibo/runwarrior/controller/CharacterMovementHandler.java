package it.unibo.runwarrior.controller;

import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;
import it.unibo.runwarrior.model.player.Character;

public class CharacterMovementHandler {
    private GameLoopPanel glp;
    private CharacterComand cmd;
    private CollisionDetection collisionDetection;
    private Character player;
    private HandlerMapElement mapHandler;
    private PowerUpDetection pUpDetection;
    private KillDetection killDetection;

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
    private boolean hitHead;
    private boolean jumpKill;
    private boolean descend;
    private boolean handleDoubleCollision;
    private boolean canAttack;

    private boolean rightDirection = true;
    private int speed = 5;
    private int speedJumpUP = 12; 
    private int speedJumpDown = 6;
    private int groundX = 0;//variabile che permette lo scorrimento della mappa

    public CharacterMovementHandler(GameLoopPanel panel, Character player, CharacterComand cmd, CollisionDetection collDet, HandlerMapElement hM, PowerUpFactoryImpl pFact){
        this.glp = panel;
        this.cmd = cmd;
        this.collisionDetection = collDet; 
        this.player = player;
        this.mapHandler = hM;
        this.pUpDetection = new PowerUpDetection(panel, pFact);
        this.killDetection = new KillDetection(panel, hM);
        setStartY(mapHandler.getFirstY(), mapHandler.getTileSize());
    }

    private void setStartY(int y, int tileSize){
        startY = y + toTouchFloor;
        playerY = startY;
        sizeCharacter = tileSize*2;
        maxJump = startY - (sizeCharacter*5/2);
        midJump = startY - (sizeCharacter*4/2);
    }

    public void setLocationAfterPowerup(int x, int y, int realx, int groundX, long lastHit) {
        this.screenX = x;
        this.playerY = y;
        this.playerX = realx;
        this.groundX = groundX;
        this.killDetection.setHitWaitTime(lastHit);
    }

    public void movePlayer(){
        maxScreenX = glp.getWidth() / 2;
        String collisionDir = "";
        String tempDir = "";
        collisionDir = collisionDetection.checkCollision(player, false);
        tempDir = pUpDetection.checkCollisionWithPowers(player, this);
        if(!tempDir.isEmpty()){
            collisionDir = tempDir;
        }
        killDetection.checkCollisionWithEnemeies(player);

        hitHead = collisionDir.equals("down") ? true : false;
        if(hitHead){
            cmd.setJump(false);
        }
        jump(cmd.isJumping(), maxJump);
        handleDoubleCollision = (collisionDir.equals("up") || collisionDir.equals("down")) && descend ? true : false;
        if(jumpKill){
            jumpAfterKill();
        }
        if(cmd.getRight() && !cmd.getLeft()){
            rightDirection = true;
            if(!collisionDir.equals("right") && !handleDoubleCollision){
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
            if(!collisionDir.equals("left") && !handleDoubleCollision){
                if(screenX > 0){
                    playerX -= speed;
                }
                if(screenX > minScreenX){
                    screenX -= speed;
                }
            }
        }
        player.updatePlayerPosition();
        canAttack = (collisionDir.equals("right") || collisionDir.equals("left")) ? false : true;
    }

    public void jump(boolean isJump, int jumpHeight){
        if(isJump && !descend){
            if(playerY > jumpHeight){
                playerY -= speedJumpUP;
            }
            else{
                playerY = jumpHeight;
                cmd.setJump(false);
            }
        }
        else{
            if(collisionDetection.isInAir(player) && !jumpKill){
                descend = true;
                playerY += speedJumpDown;
            }
            else{
                descend = false;
                cmd.setDoubleJump(false);
            }
            updateJumpVariable();
        }
    }

    public void setJumpKill(){
        this.jumpKill = true;
        cmd.setJump(true);
    }

    public void jumpAfterKill(){
        if(playerY > midJump){
            playerY -= speedJumpUP;
        }
        else{
            playerY = midJump;
            jumpKill = false;
            cmd.setJump(false);
        }
    }

    public void updateJumpVariable(){
        maxJump = (startY - (sizeCharacter*5/2)) + (playerY - startY);
        midJump = (startY - (sizeCharacter*4/2)) + (playerY - startY);
    }

    public boolean canAttack(){
        return this.canAttack;
    }

    public KillDetection getKillDetection() {
        return this.killDetection;
    }

    public boolean getRightDirection(){
        return this.rightDirection;
    }

     public int getSpeedJumpDown() {
        return this.speedJumpDown;
    }

    public int getGroundX(){
        return this.groundX;
    }

    public int getPlX() {
        return this.playerX;
    }

    public int getPlY() {
        return this.playerY;
    }

    public int getScX() {
        return this.screenX;
    }
}
