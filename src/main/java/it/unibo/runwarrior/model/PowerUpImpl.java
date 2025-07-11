package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.view.GameLoopPanel;

public class PowerUpImpl {
    
    private BufferedImage image;
    private BufferedImage egg;
    private Rectangle touchArea;
    private GameLoopPanel glp;
    private boolean powerTaken = false;
    private boolean eggOpen = false;

    public PowerUpImpl(GameLoopPanel glp){
        this.glp = glp;
        touchArea = new Rectangle();
        try {
            egg = ImageIO.read(getClass().getResourceAsStream("/PowerUps/egg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void powerUpImage(){
        try{
            if(glp.getPowersHandler().getPowers() == 0){
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/armour.png"));
            }
            if(glp.getPowersHandler().getPowers() == 1){
                image = ImageIO.read(getClass().getResourceAsStream("/PowerUps/sword.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public BufferedImage getEgg(){
        return this.egg;
    }

    public void setTouchArea(Rectangle deathPosition){
        this.touchArea = deathPosition;
    }

    public Rectangle getTouchArea(){
        return touchArea;
    }

    public boolean getPower(){
        return powerTaken;
    }

    public boolean isEggOpen(){
        return eggOpen;
    }

    //sarà usato da PowerUpDetection, in cui andrò a gestire il player coi powerUP
    public void takePower(){
        powerTaken = true;
    }
    //mentre in KillDetection si gestirà il player con gli enemies, in cui verrà richiamato il metodo die() di EnemyImpl
    public void openTheEgg(){
        eggOpen = true;
    }
}
