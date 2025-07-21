package it.unibo.runwarrior.model.player;

import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.PowerUpFactoryImpl;

public class SwordWarrior extends CharacterImpl{

    public SwordWarrior(GameLoopPanel panel, CharacterComand commands, HandlerMapElement mapHandler, PowerUpFactoryImpl pFact) {
        super(panel, commands, mapHandler, pFact);
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
            jumpR = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/jumpRightS.png"));
            jumpL = ImageIO.read(getClass().getResourceAsStream("/WarriorImages/jumpLeftS.png"));
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
            //collisionArea.setSize((sizeCharacter*7)/4, sizeCharacter-(sizeCharacter/4)-toTouchFloor);
            swordArea.setBounds(movement.getPlX() + sizeCharacter, movement.getPlY() + sizeCharacter/4,
                                    sizeCharacter, sizeCharacter-(sizeCharacter/4)-(TO_TOUCH_FLOOR*2));
        }
        if(animation.getFrame() == PlayerFrame.ATTACK_FRAME && !rightDirection){
            swordArea.setBounds(movement.getPlX() - sizeCharacter, movement.getPlY() + sizeCharacter/4,
                                            sizeCharacter, sizeCharacter-(sizeCharacter/4)-(TO_TOUCH_FLOOR*2));
            //collisionArea.union(tip);
        }
        if(!cmd.getAttack()){
            collisionArea.setSize(sizeCharacter/2, sizeCharacter-(sizeCharacter/4)-TO_TOUCH_FLOOR);
        }
    }
}
