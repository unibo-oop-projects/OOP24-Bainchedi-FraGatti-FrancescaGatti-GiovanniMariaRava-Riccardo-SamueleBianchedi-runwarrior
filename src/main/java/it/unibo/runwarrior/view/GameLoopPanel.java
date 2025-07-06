package it.unibo.runwarrior.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CollisionDetection;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowersHandler;
import it.unibo.runwarrior.model.Character;
import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.Guard;
import it.unibo.runwarrior.model.NakedWarrior;
//import it.unibo.runwarrior.model.Snake;
//import it.unibo.runwarrior.model.SwordWarrior;
//import it.unibo.runwarrior.model.Wizard;

public class GameLoopPanel extends JPanel implements Runnable{
    
    public static final int WIDTH = 1056;
    public static final int HEIGHT = 792;
    public static final int MLD = 1000000000;
    public static final int FPS = 60;

    private Thread gameThread;
    private Character player;
    private CharacterComand commands;
    private PowersHandler powerUpsHandler;
    private PowerUpFactoryImpl powersFactory;
    private CollisionDetection collisionDetection;
    
    private Handler handler;
    private HandlerMapElement mapHandler;
    private GameMap gameMap;

   // private GameMusic music;

    public GameLoopPanel(){
        this.gameMap = GameMap.load("Map_1/map_1.txt", "Map_1/forest_theme.txt");
        this.commands = new CharacterComand();
        this.mapHandler = new HandlerMapElement(gameMap);
        this.collisionDetection = new CollisionDetection(gameMap.getMapData(), mapHandler.getBlocks(), mapHandler.getTileSize());
        this.powersFactory = new PowerUpFactoryImpl(this);
        this.powerUpsHandler = new PowersHandler(this, commands, collisionDetection, mapHandler);
        initializePlayer();

        this.handler = new Handler();
        handler.addEnemy(new Guard(300, 512, 64, 64, true, handler, 100, 800, this));
        //handler.addEnemy(new Snake(300, 512, 64, 64, true, handler, 30, 400) );
        //handler.addEnemy(new Wizard(300, 512, 64,64, true, handler, 200, 800));

        //music = new GameMusic("gameMusic.wav", true);
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
        
        mapHandler.printBlocks(gr2);
        player.drawPlayer(gr2);
        player.drawRectangle(gr2);
        handler.render(gr2);
        gr2.dispose();
    }

    public void initializePlayer(){
        player = new NakedWarrior(this, commands, collisionDetection, mapHandler);
    }

    public void setPlayer(Character pl){
        this.player = pl;
    }

    public PowersHandler getPowersHandler(){
        return this.powerUpsHandler;
    }

    public PowerUpFactoryImpl getPowersFactory(){
        return this.powersFactory;
    }
}
