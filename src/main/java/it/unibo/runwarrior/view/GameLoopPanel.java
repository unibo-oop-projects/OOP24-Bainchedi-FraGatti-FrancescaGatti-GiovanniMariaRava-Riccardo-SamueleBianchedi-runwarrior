package it.unibo.runwarrior.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import it.unibo.runwarrior.controller.CoinController;
import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.model.Chronometer;

public class GameLoopPanel extends JPanel implements Runnable {
    public static final int WIDTH = 1056;
    public static final int HEIGHT = 792;
    public static final int MLD = 1000000000;
    public static final int FPS = 60;

    private Thread gameThread;
    private GameLoopController gameController;
    private CoinController coinController;
    private Chronometer chronometer;
    private boolean gameStarted = false;

    public GameLoopPanel(String mapPath, String themePath, String enemiesPath, String coinsPath, GameLoopController gameController) {
        this.gameController = gameController;

        //music = new GameMusic("gameMusic.wav", true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        System.out.println("--" + gameController.getCommands());
        this.addKeyListener(gameController.getCommands());
        this.setFocusable(true);
        this.requestFocusInWindow();
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
        gameController.update();
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D gr2 = (Graphics2D) gr;
        
        gameController.getMapHandler().printBlocks(gr2, gameController.getPlayer());
        gameController.getPowersManager().printPowerUp(gr2);
        gameController.getPlayer().drawPlayer(gr2);
        gameController.getPlayer().drawRectangle(gr2);
        gameController.getEnemyHandler().render(gr2);
        gameController.getCoinController().drawAllCoins(gr2, gameController.getMapHandler().getTileSize());
        gr2.setColor(Color.BLACK);
        gr2.setFont(new Font("Cooper Black", Font.BOLD, 20));
        gr2.drawString("TIME:" + chronometer.getTimeString(), 20, 40);
        gr2.drawString("COINS:" + gameController.getCoinController().getCoinsCollected(), 20, 70);
        gr2.dispose();
    }

    /**
     * Chooses one of the two player with whom the game starts.
     * To be connected with the shop
     */
    // public void initializePlayer() {
    //     final String selectedSkin = GameSaveManager.getInstance().getSelectedSkinName();
    //     final boolean wizardUnlocked = GameSaveManager.getInstance().isSkinPremiumSbloccata();
    //     if ("WIZARD".equalsIgnoreCase(selectedSkin) && wizardUnlocked) {
    //         player = new NakedWizard(this, commands, mapHandler, powersManager);
    //     } else {
    //         player = new NakedWarrior(this, commands, mapHandler, powersManager);
    //     }
    //     player.getMovementHandler().setStartY(mapHandler.getFirstY());
    //     powerUpsHandler.setIndex();
    // }

    // public Character getPlayer() {
    //     return this.player;
    // }

    // public void setPlayer(Character pl, int realX, int x, int y, int shift, long lastHit) {
    //     this.player = pl;
    //     this.player.getMovementHandler().setLocationAfterPowerup(x, y, realX, shift, lastHit);
    //     this.coinController.updatePlayer(pl);
    // }

    // public PowersHandler getPowersHandler() {
    //     return this.powerUpsHandler;
    // }

    // public PowerUpManager getPowersManager() {
    //     return this.powersManager;
    // }

    // // public int getCameraShift(){
    // //     return player.getPlX();
    // // }
    
    // public HandlerMapElement getMapHandler() {
    //     return this.mapHandler;
    // }

    // public EnemyHandlerImpl getEnemyHandler() {
    //     return this.enemyHandler;
    // }

    // /**
    //  * Map the int type with the correct EnemyView
    //  */
    // private final void initializeEnemyViewFactory() {
    //     enemyViewFactory.register(1, new GuardView(this));
    //     enemyViewFactory.register(2, new SnakeView(this));
    //     enemyViewFactory.register(3, new WizardView(this));
    //     enemyViewFactory.register(4, new GoblinView(this));
    //     enemyViewFactory.register(5, new MonkeyView(this));
    // }
}
