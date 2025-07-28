package it.unibo.runwarrior.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class thath handles keyboard input.
 */
public class CharacterComand implements KeyListener{

    private boolean right;
    private boolean left;
    private boolean isJump;
    private boolean attack;
    private boolean handleDoubleJump;

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        final int value = e.getKeyCode();
        if(value == KeyEvent.VK_RIGHT){
            right = true;
        }
        if(value == KeyEvent.VK_LEFT){
            left = true;
        }
        if(value == KeyEvent.VK_UP && !handleDoubleJump){
            isJump = true;
            handleDoubleJump = true;
        }
        if(value == KeyEvent.VK_SHIFT){
            attack = true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keyReleased(final KeyEvent e) {
        final int value = e.getKeyCode();
        if(value == KeyEvent.VK_RIGHT){
            right = false;
        }
        if(value == KeyEvent.VK_LEFT){
            left = false;
        }
        if(value == KeyEvent.VK_UP){
            isJump = false;
        }
        if(value == KeyEvent.VK_SHIFT){
            attack = false;
        }
    }

    /**
     * Set false by player movement handler when the player reaches maxJump.
     *
     * @param i false when the player is descending
     */
    public void setJump(final boolean i){
        this.isJump = i;
    }

    /**
     * Set this variable to deny double jump while in air
     * @param i false if the player can jump, true if the player touched ground and can jump
     */
    public void setDoubleJump(final boolean i){
        this.handleDoubleJump = i;
    }

    /**
     * @return true if the player goes to the right
     */
    public boolean isRight(){
        return right;
    }

    /**
     * @return true if the player goes to the left
     */
    public boolean isLeft(){
        return left;
    }

    /**
     * @return true if the player is stopped
     */
    public boolean isStop(){
        boolean standing;
        if((isRight() && isLeft()) || (!isRight() && !isLeft())){
            standing = true;
        }
        else{
            standing = false;
        }
        return standing;
    }

    /**
     * @return true if the player is jumping
     */
    public boolean isJumping(){
        return isJump;
    }

    /**
     * @return true if the player is attacking
     */
    public boolean isAttacking(){
        return attack;
    }
}
