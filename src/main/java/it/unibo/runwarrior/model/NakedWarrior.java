package it.unibo.runwarrior.model;

import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CollisionDetection;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

public class NakedWarrior extends CharacterImpl{

    public NakedWarrior(GameLoopPanel panel, CharacterComand commands, CollisionDetection collision, HandlerMapElement mapHandler, PowerUpFactoryImpl pFact) {
        super(panel, commands, collision, mapHandler, pFact);
    }

    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/stopRightN.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goRightN1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goRightN2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/stopLeftN.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goLeftN1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goLeftN2.png"));
            attackR = right0;
            attackL = left0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAttackCollision(){
        
    }
    
}
