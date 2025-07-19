package it.unibo.runwarrior.model.enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.controller.EnemyHandler;
import it.unibo.runwarrior.view.GameLoopPanel;

public class Monkey extends EnemyImpl {
    private BufferedImage rightMonkey;
    private BufferedImage leftMonkey;
    private BufferedImage rightMonkeyMoving;
    private BufferedImage leftMonkeyMoving;
    private BufferedImage rightMonkeyRunning;
    private BufferedImage leftMonkeyRunning;
    private BufferedImage banana;


    public Monkey(int x, int y, int width, int height, boolean solid, EnemyHandler handler, GameLoopPanel glp) {
        super(x, y, width, height, solid, handler, glp);
        setVelocityX(1);
        try{
            rightMonkey = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkey.png"));
            leftMonkey = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkey.png"));
            rightMonkeyMoving = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkeyMoving.png"));
            leftMonkeyMoving = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkeyMoving.png"));
            rightMonkeyRunning = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkeyRunning.png"));
            leftMonkeyRunning = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkeyRunning.png"));
            banana = ImageIO.read(getClass().getResourceAsStream("/Monkey/banana.png"));
            image = rightMonkey;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {

            BufferedImage currentImage;
            if (velocityX == 0){
                currentImage = image;
            }else if (velocityX > 0) {
                currentImage = step ? rightMonkeyMoving : rightMonkeyRunning;
                image = rightMonkey;
            } else {
                currentImage = step ? leftMonkeyMoving : leftMonkeyRunning;
                image = leftMonkey;
            }
            int shift = glp.getMapHandler().getShift();
            //System.out.println(" MONKEY XS: " + (x+shift)+ "X:"+ x);
            g.drawImage(currentImage, x+shift, y, width, height, null);
        
    }
}
