package it.unibo.runwarrior.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.runwarrior.controller.GameLoopController;
import it.unibo.runwarrior.model.Chronometer;
import it.unibo.runwarrior.model.ChronometerImpl;
import it.unibo.runwarrior.model.GameSaveManager;

public class GameLoopPanel extends JPanel implements Runnable {
    public static final int WIDTH = 1056;
    public static final int HEIGHT = 792;
    public static final int MLD = 1000000000;
    public static final int FPS = 60;

    private Thread gameThread;
    private GameLoopController gameController;
    private GameMusic music;
    private Chronometer chronometer;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private boolean levelCompleted = false;
    private boolean panelShawn = false;
    private JFrame frameMenu;
    // private volatile boolean running = true;

    public GameLoopPanel(JFrame frameMenu, String mapPath, String themePath, String enemiesPath, String coinsPath, GameLoopController gameController) {
        this.gameController = gameController;
        this.frameMenu = frameMenu;
        //music = new GameMusic("gameMusic.wav", true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(gameController.getCommands());
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.chronometer = new ChronometerImpl();
    }

    public void startGame() {
        //System.out.println("Start Game chiamato");
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timeFor1Frame = MLD/FPS; //16.666.666,67 in ns
        long lastTime = System.nanoTime();
        long currentTime;
        double waitingTime = 0;

        while (!Thread.currentThread().isInterrupted()) {
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

    public void endGame(){
        gameThread.interrupt();
    }

    public void update() {
        if(!gameStarted){
            chronometer.startTimer();
            gameStarted = true;
        }
        if (!gameEnded) {
            // controllo se ha vinto
            if (gameController.getPlayer().getMovementHandler().getCollisionDetection().gameOver()) {
                gameEnded = true;
                levelCompleted = true;
            } else if (gameController.getPowersHandler().gameOver()) {
                gameEnded = true; 
                levelCompleted = false;
            }
        }
        if (gameEnded && !panelShawn) {
            panelShawn = true;
            showEndPanel();
        }
        gameController.update();
    }

    private void showEndPanel() {
        //aggiorno livelli:
        if (levelCompleted) {
            int currentLevel = gameController.getCurrentLevel();
            GameSaveManager save = GameSaveManager.getInstance();

            if (currentLevel >= save.getLevelsCompleted() + 1) {
                save.setLevelsCompleted(currentLevel);
            }
        }
        
        GameSaveManager.getInstance().addCoin(gameController.getCoinController().getCoinsCollected());

        //quale pannello mostrare?
        JPanel content = levelCompleted
        ? new LevelCompletedPanel(chronometer.getTimeString(), gameController.getCoinController().getCoinsCollected())
        : new GameOverPanel(gameController.getCoinController().getCoinsCollected());

        JDialog dialog = new JDialog(frameMenu, "Game Result", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(content);
        dialog.pack();
        dialog.setLocationRelativeTo(frameMenu);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.out.println("Dialog closed, resetting frame");
                endGame();
                SwingUtilities.invokeLater(() -> {
                    frameMenu.getContentPane().removeAll();
                    Menu menuPanel = new Menu(frameMenu);
                    frameMenu.setContentPane(menuPanel);
                    frameMenu.revalidate();
                    frameMenu.repaint();
                    frameMenu.setVisible(true);
                    menuPanel.setFocusable(true);
                    menuPanel.requestFocusInWindow();
                });
            }
        });
        dialog.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        try{
            Graphics2D gr2 = (Graphics2D) gr;
            
            gameController.getMapHandler().printBlocks(gr2, gameController.getPlayer());
            gameController.getPowersManager().printPowerUp(gr2);
            gameController.getPlayer().drawPlayer(gr2);
            gameController.getEnemyHandler().render(gr2);
            gameController.getCoinController().drawAllCoins(gr2, gameController.getMapHandler().getTileSize(), gameController.getPlayer());
            gr2.setColor(Color.BLACK);
            gr2.setFont(new Font("Cooper Black", Font.BOLD, 20));
            gr2.drawString("TIME:" + chronometer.getTimeString(), 20, 40);
            gr2.drawString("COINS:" + gameController.getCoinController().getCoinsCollected(), 20, 70);

            gr2.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        // resultFrame.setVisible(true);
        // resultFrame.addWindowListener(new java.awt.event.WindowAdapter() {
        // @Override
        // public void windowClosed(java.awt.event.WindowEvent e) {
        //     // Quando l'utente chiude il pannello, torna al menu
        //     javax.swing.SwingUtilities.invokeLater(() -> {
        //         Menu menu = new Menu();
        //         JFrame frame = menu.getFrameMenu();
        //         frame.setVisible(true);
        //     });
        // @Override
        // public void windowClosing(java.awt.event.WindowEvent e) {
        //     resultFrame.dispose();
        // }
        // });
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

