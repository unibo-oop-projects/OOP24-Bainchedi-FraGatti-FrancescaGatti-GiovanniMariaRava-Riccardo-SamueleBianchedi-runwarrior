package it.unibo.runwarrior.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CharacterComand implements KeyListener{

    private boolean right;
    private boolean left;
    private boolean standing;
    private JumpState jump = JumpState.STOP_JUMP;
    private boolean isJump;
    private boolean attack;

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int value = e.getKeyCode();
        
        if(value == KeyEvent.VK_RIGHT){
            right = true;
        }
        if(value == KeyEvent.VK_LEFT){
            left = true;
        }
        if(value == KeyEvent.VK_UP){
            isJump = true;
        }
        if(value == KeyEvent.VK_SHIFT){
            attack = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int value = e.getKeyCode();
        
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

    public void setJump(boolean i){
        this.isJump = i;
    }

    public boolean getRight(){
        return right;
    }

    public boolean getLeft(){
        return left;
    }

    public boolean getStop(){
        if((getRight() && getLeft()) || (!getRight() && !getLeft())){
            standing = true;
        }
        else{
            standing = false;
        }
        return standing;
    }

    public JumpState getJump(){
        return jump;
    }

    public boolean isJumping(){
        return isJump;
    }

    public boolean getAttack(){
        return attack;
    }
}
