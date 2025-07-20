package it.unibo.runwarrior.view.enemy;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadResources'");
    }

    @Override
    public void render(Graphics g, EnemyImpl enemy) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
    
}
