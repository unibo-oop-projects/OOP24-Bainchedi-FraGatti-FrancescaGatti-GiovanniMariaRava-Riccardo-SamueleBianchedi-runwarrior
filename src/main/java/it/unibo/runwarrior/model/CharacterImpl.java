package it.unibo.runwarrior.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import it.unibo.runwarrior.controller.CharacterAnimationHandler;
import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CharacterMovementHandler;
import it.unibo.runwarrior.controller.CollisionDetection;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

public abstract class CharacterImpl implements Character{
    protected int sizeCharacter;
    protected int toTouchFloor = 2;
    protected Rectangle collisionArea;
    protected Rectangle swordArea;
    protected boolean rightDirection;
    private int speed = 5;

    protected BufferedImage right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL;

    protected CharacterComand cmd;
    protected CharacterAnimationHandler animation;
    protected CharacterMovementHandler movement;

    public CharacterImpl(GameLoopPanel panel, CharacterComand commands, CollisionDetection collision, HandlerMapElement mapHandler, PowerUpFactoryImpl pFact) {
        this.cmd = commands;
        playerImage();
        setStartY(mapHandler.getFirstY(), mapHandler.getTileSize());
        this.movement = new CharacterMovementHandler(panel, this, commands, collision, mapHandler, pFact);
        this.animation = new CharacterAnimationHandler(commands, movement, right0, right1, right2, left0, left1, left2, attackR, attackL, tipR, tipL);
        collisionArea = new Rectangle(movement.getPlX()+(sizeCharacter/4), movement.getPlY()+(sizeCharacter/4),
                                        sizeCharacter/2, sizeCharacter-(sizeCharacter/4)-toTouchFloor);
        swordArea = new Rectangle();
    }

    private void setStartY(int y, int tileSize){
        sizeCharacter = tileSize*2;
    }

    @Override
    public void update() {
        this.rightDirection = movement.getRightDirection();
        movement.movePlayer();
        animation.frameChanger();
    }

    @Override
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
        gr2.drawImage(im, movement.getScX(), movement.getPlY(), sizeCharacter, sizeCharacter, null);
        if(animation.isAttacking()){
            tip = animation.getTip(rightDirection);
            int tipPos = rightDirection ? 1 : (-1);
            gr2.drawImage(tip, movement.getScX() + (tipPos*sizeCharacter), movement.getPlY(), sizeCharacter, sizeCharacter, null);
        }
    }

    public void drawRectangle(Graphics2D gr){
        gr.drawRect(movement.getScX()+(sizeCharacter/4), collisionArea.y, collisionArea.width, collisionArea.height);//si sposta in avanti perchè segue playerX non screenX
    }

    @Override
    public abstract void playerImage();

    @Override
    public CharacterMovementHandler getMovementHandler(){
        return this.movement;
    }

    public CharacterAnimationHandler getAnimationHandler() {
        return this.animation;
    }

    public Rectangle getSwordArea(){
        return this.swordArea;
    }

    @Override
    public Rectangle getArea(){
        return this.collisionArea;
    }

    @Override
    public int getSpeed(){
        return this.speed;
    }
}
