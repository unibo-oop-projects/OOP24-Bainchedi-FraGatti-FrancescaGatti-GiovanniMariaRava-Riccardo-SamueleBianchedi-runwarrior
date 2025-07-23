package it.unibo.runwarrior.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

import javax.swing.JPanel;

import it.unibo.runwarrior.controller.CharacterComand;
import it.unibo.runwarrior.controller.CoinController;
import it.unibo.runwarrior.controller.HandlerMapElement;
import it.unibo.runwarrior.controller.PowersHandler;
import it.unibo.runwarrior.controller.enemy.EnemySpawner;
import it.unibo.runwarrior.controller.enemy.impl.EnemyHandlerImpl;
import it.unibo.runwarrior.model.player.Character;
import it.unibo.runwarrior.model.Chronometer;
import it.unibo.runwarrior.model.GameMap;
import it.unibo.runwarrior.model.player.NakedWarrior;
import it.unibo.runwarrior.model.player.NakedWizard;
import it.unibo.runwarrior.view.enemy.impl.EnemyViewFactoryImpl;
import it.unibo.runwarrior.view.enemy.impl.GoblinView;
import it.unibo.runwarrior.view.enemy.impl.GuardView;
import it.unibo.runwarrior.view.enemy.impl.MonkeyView;
import it.unibo.runwarrior.view.enemy.impl.SnakeView;
import it.unibo.runwarrior.view.enemy.impl.WizardView;

public class GameLoopPanel extends JPanel implements Runnable {
    public static final int WIDTH = 1056;
    public static final int HEIGHT = 792;
    public static final int MLD = 1000000000;
    public static final int FPS = 60;

    private Thread gameThread;
    private Character player;
    private CharacterComand commands;
    private PowersHandler powerUpsHandler;
    private PowerUpFactoryImpl powersFactory;

    private HandlerMapElement mapHandler;
    private EnemyHandlerImpl enemyHandler;
    private EnemyViewFactoryImpl enemyViewFactory;
    private EnemySpawner enemySpawner;
    private GameMap gameMap;
    private CoinController coinController;
    private Chronometer chronometer;
    private boolean gameStarted = false;
   // private GameMusic music;

    public GameLoopPanel(String mapPath, String themePath, String enemiesPath, String coinsPath) {
        this.gameMap = GameMap.load(mapPath, themePath);
        this.commands = new CharacterComand();
        this.mapHandler = new HandlerMapElement(gameMap);
        this.powersFactory = new PowerUpFactoryImpl(this, mapHandler, gameMap.getMapData());
        this.powerUpsHandler = new PowersHandler(this, commands, mapHandler, powersFactory);
        // String mapOneFileName = "src/main/resources/Map_1/map_1.txt";
        // String mapTwoFileName = "src/main/resources/Map_2/map_2.txt";
        // String imageConfigMapOne = "src/main/resources/Map_1/forest_theme.txt";
        // String imageConfigMapTwo = "src/main/resources/Map_2/desert_theme.txt";

        //GameMap levelOne = GameMap.load(mapOneFileName, imageConfigMapOne);
        //GameMap levelTwo = GameMap.load(mapTwoFileName, imageConfigMapTwo);
        this.enemyViewFactory = new EnemyViewFactoryImpl();
        initializeEnemyViewFactory();
        this.enemyHandler = new EnemyHandlerImpl(this, this.enemyViewFactory);
        this.enemySpawner = new EnemySpawner(enemyHandler, this);
        enemySpawner.loadEnemiesFromStream(getClass().getResourceAsStream(enemiesPath));
        initializePlayer();

        //music = new GameMusic("gameMusic.wav", true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(commands);
        this.setFocusable(true);

        this.coinController = new CoinController(player);
        List<int[]> coords = coinController.loadCoinFromFile(coinsPath);
        for(int[] coord : coords){
            coinController.addCoins(coord[0], coord[1]);
        }
        this.chronometer = new Chronometer();
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timeFor1Frame = MLD/FPS; //16.666.666,67 in ns
        long lastTime = System.nanoTime();
        long currentTime;
        double waitingTime = 0;

        while (true) {
            currentTime = System.nanoTime();
            waitingTime += (currentTime-lastTime);
            lastTime = currentTime;
            
            if (waitingTime >= timeFor1Frame) {
                update();
                repaint();
                waitingTime  -= timeFor1Frame;
            }
        }
    }

    public void update() {
        if(!gameStarted){
            chronometer.StartTimer();
            gameStarted = true;
        }
        player.update();
        enemySpawner.update();
        enemyHandler.updateWithMap(mapHandler.getCollisionRectangles());
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D gr2 = (Graphics2D) gr;
        
        mapHandler.printBlocks(gr2, player);
        powersFactory.printPowerUp(gr2);
        player.drawPlayer(gr2);
        player.drawRectangle(gr2);
        enemyHandler.render(gr2);
        coinController.drawAllCoins(gr2, mapHandler.getTileSize());
        gr2.setColor(Color.BLACK);
        gr2.setFont(new Font("Cooper Black", Font.BOLD, 20));
        gr2.drawString("TIME:" + chronometer.getTimeString(), 20, 40);
        gr2.drawString("COINS:", 20, 70);
        gr2.dispose();
    }

    /**
     * Chooses one of the two player with whom the game starts.
     * To be connected with the shop
     */
    public void initializePlayer() {
        player = new NakedWizard(this, commands, mapHandler, powersFactory);
        powerUpsHandler.setIndex();
    }

    public Character getPlayer() {
        return this.player;
    }

    public void setPlayer(Character pl, int realX, int x, int y, int shift, long lastHit) {
        this.player = pl;
        this.player.getMovementHandler().setLocationAfterPowerup(x, y, realX, shift, lastHit);
        this.coinController.updatePlayer(pl);
    }

    public PowersHandler getPowersHandler() {
        return this.powerUpsHandler;
    }

    public PowerUpFactoryImpl getPowersFactory() {
        return this.powersFactory;
    }

    // public int getCameraShift(){
    //     return player.getPlX();
    // }
    
    public HandlerMapElement getMapHandler() {
        return this.mapHandler;
    }

    public EnemyHandlerImpl getEnemyHandler() {
        return this.enemyHandler;
    }

    /**
     * Map the int type with the correct EnemyView
     */
    private final void initializeEnemyViewFactory() {
        enemyViewFactory.register(1, new GuardView(this));
        enemyViewFactory.register(2, new SnakeView(this));
        enemyViewFactory.register(3, new WizardView(this));
        enemyViewFactory.register(4, new GoblinView(this));
        enemyViewFactory.register(5, new MonkeyView(this));
    }
}
