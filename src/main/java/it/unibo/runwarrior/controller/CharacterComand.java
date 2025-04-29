package it.unibo.runwarrior.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CharacterComand implements KeyListener{

    private boolean right;
    private boolean left;
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
        if(value == KeyEvent.VK_UP && jump == JumpState.STOP_JUMP){
            jump = JumpState.START_JUMP;
            isJump = true;
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
        if(value == KeyEvent.VK_UP && jump == JumpState.START_JUMP){
            jump = JumpState.MIN_JUMP;
            isJump = true;
        }
    }

    public void setJump(JumpState stateJump){
        this.jump = stateJump;
        if(stateJump == JumpState.STOP_JUMP){
            isJump = false;
        }
    }

    public boolean getRight(){
        return right;
    }

    public boolean getLeft(){
        return left;
    }

    public JumpState getJump(){
        return jump;
    }

    public boolean isJumping(){
        return isJump;
    }
    
}
