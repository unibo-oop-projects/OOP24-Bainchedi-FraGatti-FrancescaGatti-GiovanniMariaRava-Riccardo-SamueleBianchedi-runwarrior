package it.unibo.runwarrior.view.enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import it.unibo.runwarrior.model.enemy.EnemyImpl;
import it.unibo.runwarrior.view.GameLoopPanel;
import it.unibo.runwarrior.view.enemy.api.EnemyView;

public class SnakeView implements EnemyView{
    private BufferedImage rightSnake;
    private BufferedImage rightSnakeMoving;
    private BufferedImage leftSnake;
    private BufferedImage leftSnakeMoving;
    private BufferedImage poisonImage;
    private GameLoopPanel glp;
    public SnakeView(GameLoopPanel glp) {
        this.glp = glp;
        try {
            loadResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadResources() throws IOException {
        rightSnake = ImageIO.read(getClass().getResourceAsStream("/Snake/rightSnake.png"));
        rightSnakeMoving = ImageIO.read(getClass().getResourceAsStream("/Snake/rightSnakeMoving.png"));
        leftSnake = ImageIO.read(getClass().getResourceAsStream("/Snake/leftSnake.png"));
        leftSnakeMoving = ImageIO.read(getClass().getResourceAsStream("/Snake/leftSnakeMoving.png"));
        poisonImage = ImageIO.read(getClass().getResourceAsStream("/Snake/poison.png"));
    }

    @Override
    public void render(Graphics g, EnemyImpl enemy) {
        BufferedImage currentImage;

        if (enemy.velocityX > 0) {
            currentImage = enemy.step ? rightSnakeMoving : rightSnake;
        } else {
            currentImage = enemy.step ? leftSnakeMoving : leftSnake;
        }

        int shift = glp.getMapHandler().getShift(); 
        //System.out.println("SNAKE XS: " + (x+shift)+ "X:"+ x);
        g.drawImage(currentImage, enemy.x + shift , enemy.y, enemy.width, enemy.height, null);

    }
    
}
