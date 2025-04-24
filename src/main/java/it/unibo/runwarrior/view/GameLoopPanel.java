package it.unibo.runwarrior.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.PowersHandler;
import it.unibo.runwarrior.model.Character;
import it.unibo.runwarrior.model.Guard;
import it.unibo.runwarrior.model.NakedWarrior;
import it.unibo.runwarrior.model.Snake;
import it.unibo.runwarrior.model.Wizard;

public class GameLoopPanel extends JPanel implements Runnable{
    
    public static final int WIDTH = 1056;
    public static final int HEIGHT = 672;
    public static final int MLD = 1000000000;
    public static final int FPS = 60;

    private Thread gameThread;
    private Character player;
    private CharacterComand commands;
    private PowersHandler powerUpsHandler;
    
    private Handler handler;

    public GameLoopPanel(){
        this.commands = new CharacterComand();
        this.powerUpsHandler = new PowersHandler(this, commands);
        initializePlayer();

        this.handler = new Handler();
        //handler.addEnemy(new Guard(300, 512, 64, 64, true, handler, 100, 800));
        //handler.addEnemy(new Snake(300, 512, 64, 64, true, handler, 30, 400) );
        handler.addEnemy(new Wizard(300, 512, 64,64, true, handler, 200, 800));

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(commands);
        this.setFocusable(true);
    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        double timeFor1Frame = MLD/FPS; //16.666.666,67 in ns
        long lastTime = System.nanoTime();
        long currentTime;
        double waitingTime = 0;

        while(true){
            currentTime = System.nanoTime();
            waitingTime += (currentTime-lastTime);
            lastTime = currentTime;
            
            if(waitingTime >= timeFor1Frame){
                update();
                repaint();
                waitingTime  -= timeFor1Frame;
            }
        }
    }

    public void update(){
        player.update();
        handler.update();
    }

    @Override
    protected void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D gr2 = (Graphics2D) gr;
        
        player.drawPlayer(gr2);
        handler.render(gr2);
        gr2.dispose();
    }

    public void initializePlayer(){
        player = new NakedWarrior(this, commands);
    }

    public void setPlayer(Character pl){
        player = pl;
    }
}
