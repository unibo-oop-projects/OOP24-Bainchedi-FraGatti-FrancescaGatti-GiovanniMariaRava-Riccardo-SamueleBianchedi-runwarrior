package it.unibo.runwarrior.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CoinController;
import it.unibo.runwarrior.controller.CollisionDetection;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowersHandler;
import it.unibo.runwarrior.controller.EnemyHandler;
import it.unibo.runwarrior.controller.EnemySpawner;
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
    
    private HandlerMapElement mapHandler;
    private EnemyHandler enemyHandler;
    private EnemySpawner enemySpawner;
    private GameMap gameMap;

    private CoinController coinController;

   // private GameMusic music;

    public GameLoopPanel(){
        this.gameMap = GameMap.load("Map_1/map_1.txt", "Map_1/forest_theme.txt");
        this.commands = new CharacterComand();
        this.mapHandler = new HandlerMapElement(gameMap);
        this.collisionDetection = new CollisionDetection(gameMap.getMapData(), mapHandler.getBlocks(), mapHandler.getTileSize());
        this.powersFactory = new PowerUpFactoryImpl(this);
        this.powerUpsHandler = new PowersHandler(this, commands, collisionDetection, mapHandler);
        initializePlayer();
        // String mapOneFileName = "src/main/resources/Map_1/map_1.txt";
        // String mapTwoFileName = "src/main/resources/Map_2/map_2.txt";
        // String imageConfigMapOne = "src/main/resources/Map_1/forest_theme.txt";
        // String imageConfigMapTwo = "src/main/resources/Map_2/desert_theme.txt";

        //GameMap levelOne = GameMap.load(mapOneFileName, imageConfigMapOne);
        //GameMap levelTwo = GameMap.load(mapTwoFileName, imageConfigMapTwo);

        this.enemyHandler = new EnemyHandler();
        this.enemySpawner = new EnemySpawner(enemyHandler, this);
        enemyHandler.addEnemy(new Guard(300, 418, 64, 64, true, enemyHandler, this));
        //handler.addEnemy(new Snake(300, 512, 64, 64, true, handler, 30, 400) );
        //handler.addEnemy(new Wizard(300, 512, 64,64, true, handler, 200, 800));

        //music = new GameMusic("gameMusic.wav", true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(commands);
        this.setFocusable(true);

        this.coinController = new CoinController();
        List<int[]> coords = coinController.loadCoinFromFIle("CoinCoordinates_map1.txt");
        for(int[] coord : coords){
            coinController.addCoins(coord[0], coord[1]);
        }
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
        enemyHandler.update();
    }

    @Override
    protected void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D gr2 = (Graphics2D) gr;
        
        mapHandler.printBlocks(gr2);
        player.drawPlayer(gr2);
        player.drawRectangle(gr2);
        enemyHandler.render(gr2);
        gr2.dispose();
        coinController.drawAllCoins(gr2, mapHandler.getTileSize());
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

    public int getCameraShift(){
        return player.getPlX();
    }
    
    public HandlerMapElement getMapHandler() {
        return this.mapHandler;
    }
}
