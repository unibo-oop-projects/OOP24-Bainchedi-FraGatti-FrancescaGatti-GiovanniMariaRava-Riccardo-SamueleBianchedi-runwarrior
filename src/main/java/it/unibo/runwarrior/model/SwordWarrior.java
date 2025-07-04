package it.unibo.runwarrior.model;

import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.view.GameLoopPanel;

public class SwordWarrior extends CharacterImpl{

    public SwordWarrior(GameLoopPanel panel, CharacterComand commands) {
        super(panel, commands);
    }

    @Override
    public void playerImage() {
        try {
            right0 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/stopRightS.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goRightS1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goRightS2.png"));
            left0 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/stopLeftS.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goLeftS1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/goLeftS2.png"));
            attackR = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/attackRight.png"));
            attackL = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/attackLeft.png"));
            tipR = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/tipRight.png"));
            tipL = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/tipLeft.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAttackCollision() {
        if(animation.getFrame() == PlayerFrame.ATTACK_FRAME && rightDirection){
            collisionArea.setSize(162, 73);
        }
        if(animation.getFrame() == PlayerFrame.ATTACK_FRAME && !rightDirection){
            collisionArea.setLocation(playerX - sizeCharacter, playerY + 20);
            collisionArea.setSize(164, 73);
        }
        if(!cmd.getAttack()){
            collisionArea.setSize(38, 73);
        }
    }
    
}
