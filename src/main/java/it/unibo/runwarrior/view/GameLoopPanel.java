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
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.player.NakedWarrior;
import it.unibo.runwarrior.view.enemy.EnemyViewFactory;
import it.unibo.runwarrior.view.enemy.GoblinView;
import it.unibo.runwarrior.view.enemy.GuardView;
import it.unibo.runwarrior.view.enemy.MonkeyView;
import it.unibo.runwarrior.view.enemy.SnakeView;
import it.unibo.runwarrior.view.enemy.WizardView;


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
private EnemyViewFactory enemyViewFactory;
    private EnemySpawner enemySpawner;
    private GameMap gameMap;

    private CoinController coinController;

   // private GameMusic music;

    public GameLoopPanel(){
        this.gameMap = GameMap.load("Map_1/map_1.txt", "Map_1/forest_theme.txt");
        this.commands = new CharacterComand();
        this.mapHandler = new HandlerMapElement(gameMap);
        this.collisionDetection = new CollisionDetection(gameMap.getMapData(), mapHandler.getBlocks(), mapHandler.getTileSize());
        this.powersFactory = new PowerUpFactoryImpl(this, mapHandler, gameMap.getMapData());
        this.powerUpsHandler = new PowersHandler(this, commands, collisionDetection, mapHandler, powersFactory);
        // String mapOneFileName = "src/main/resources/Map_1/map_1.txt";
        // String mapTwoFileName = "src/main/resources/Map_2/map_2.txt";
        // String imageConfigMapOne = "src/main/resources/Map_1/forest_theme.txt";
        // String imageConfigMapTwo = "src/main/resources/Map_2/desert_theme.txt";

        //GameMap levelOne = GameMap.load(mapOneFileName, imageConfigMapOne);
        //GameMap levelTwo = GameMap.load(mapTwoFileName, imageConfigMapTwo);
        this.enemyViewFactory = new EnemyViewFactory();
        initializeEnemyViewFactory();
        this.enemyHandler = new EnemyHandler(this, this.enemyViewFactory);
        this.enemySpawner = new EnemySpawner(enemyHandler, this);
        enemySpawner.loadEnemiesFromStream(getClass().getResourceAsStream("/Map_1/enemiesMap1.txt"));
        initializePlayer();

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
        enemySpawner.update();
        enemyHandler.updateWithMap(mapHandler.getCollisionRectangles());
    }

    @Override
    protected void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D gr2 = (Graphics2D) gr;
        
        mapHandler.printBlocks(gr2);
        powersFactory.printPowerUp(gr2);
        player.drawPlayer(gr2);
        player.drawRectangle(gr2);
        enemyHandler.render(gr2);
        coinController.drawAllCoins(gr2, mapHandler.getTileSize());
        gr2.dispose();
    }

    /**
     * Chooses one of the two player with whom the game starts.
     * To be connected with the shop
     */
    public void initializePlayer(){
        player = new NakedWarrior(this, commands, collisionDetection, mapHandler, powersFactory);
        powerUpsHandler.setIndex();
    }

    public Character getPlayer(){
        return this.player;
    }

    public void setPlayer(Character pl, int realX, int x, int y, int shift, long lastHit){
        this.player = pl;
        this.player.getMovementHandler().setLocationAfterPowerup(x, y, realX, shift, lastHit);
    }

    public PowersHandler getPowersHandler(){
        return this.powerUpsHandler;
    }

    public PowerUpFactoryImpl getPowersFactory(){
        return this.powersFactory;
    }

    // public int getCameraShift(){
    //     return player.getPlX();
    // }
    
    public HandlerMapElement getMapHandler() {
        return this.mapHandler;
    }

    public EnemyHandler getEnemyHandler() {
        return this.enemyHandler;
    }

    private final void initializeEnemyViewFactory(){
        enemyViewFactory.register(1, new GuardView(this));
        enemyViewFactory.register(2, new SnakeView(this));
        enemyViewFactory.register(3, new WizardView(this));
        enemyViewFactory.register(4, new GoblinView(this));
        enemyViewFactory.register(5, new MonkeyView(this));
    }
}
