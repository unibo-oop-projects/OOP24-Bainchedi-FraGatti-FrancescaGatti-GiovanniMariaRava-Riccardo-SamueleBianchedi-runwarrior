package it.unibo.runwarrior.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CharacterComand implements KeyListener{

    private boolean right;
    private boolean left;
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
    }

    public boolean getRight(){
        return right;
    }

    public boolean getLeft(){
        return left;
    }
    
}
