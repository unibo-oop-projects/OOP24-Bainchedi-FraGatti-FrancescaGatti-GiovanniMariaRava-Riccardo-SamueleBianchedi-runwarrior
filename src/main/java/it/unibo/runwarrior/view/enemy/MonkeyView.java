package it.unibo.runwarrior.view.enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;

public class MonkeyView implements EnemyView{
    private BufferedImage rightMonkey;
    private BufferedImage leftMonkey;
    private BufferedImage rightMonkeyMoving;
    private BufferedImage leftMonkeyMoving;
    private BufferedImage rightMonkeyRunning;
    private BufferedImage leftMonkeyRunning;
    private BufferedImage banana;
    private BufferedImage image;
    private GameLoopPanel glp;
    public MonkeyView(GameLoopPanel glp) {
        this.glp = glp;
        try{
            loadResources();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void loadResources() throws IOException {
        rightMonkey = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkey.png"));
        leftMonkey = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkey.png"));
        rightMonkeyMoving = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkeyMoving.png"));
        leftMonkeyMoving = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkeyMoving.png"));
        rightMonkeyRunning = ImageIO.read(getClass().getResourceAsStream("/Monkey/rightMonkeyRunning.png"));
        leftMonkeyRunning = ImageIO.read(getClass().getResourceAsStream("/Monkey/leftMonkeyRunning.png"));
        banana = ImageIO.read(getClass().getResourceAsStream("/Monkey/banana.png"));
        image = rightMonkey;
    }

    @Override
    public void render(Graphics g, EnemyImpl enemy) {
        BufferedImage currentImage;
        if (enemy.velocityX == 0){
            currentImage = image;
        }else if (enemy.velocityX > 0) {
            currentImage = enemy.step ? rightMonkeyMoving : rightMonkeyRunning;
            image = rightMonkey;
        } else {
            currentImage = enemy.step ? leftMonkeyMoving : leftMonkeyRunning;
            image = leftMonkey;
        }
        int shift = glp.getMapHandler().getShift();
        //System.out.println(" MONKEY XS: " + (x+shift)+ "X:"+ x);
        g.drawImage(currentImage, enemy.x+shift, enemy.y, enemy.width, enemy.height, null);
    }
    
}
