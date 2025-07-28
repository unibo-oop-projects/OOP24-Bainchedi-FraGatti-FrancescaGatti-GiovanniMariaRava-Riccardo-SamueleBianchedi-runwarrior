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
import it.unibo.runwarrior.model.GameSaveManager;

/**
 * Class that render the main panel of the game and the end frame.
 */
public class GameLoopPanel extends JPanel implements Runnable {
    public static final int WIDTH = 1056;
    public static final int HEIGHT = 792;
    public static final int MLD = 1000000000;
    public static final int FPS = 60;
    private static final int FONT_X = 20;
    private static final int FONT_TIME_Y = 40;
    private static final int FONT_COIN_Y = 70;

    private Thread gameThread;
    private GameLoopController gameController;
    private final GameMusic music;
    private Chronometer chronometer;
    private boolean gameStarted;
    private boolean gameEnded;
    private boolean levelCompleted;
    private boolean panelShawn;
    private JFrame frameMenu;

    /**
     * Constructor of the class. 
     *
     * @param frameMenu is the frame in which menu is shown
     * @param mapPath the path for loading of the map
     * @param themePath the path for loading the images of tile
     * @param enemiesPath the path for loadin the enemies position
     * @param coinsPath the path for loading the coin position
     * @param gameController the controller of the whole game
     */
    public GameLoopPanel(final JFrame frameMenu, final String mapPath, final String themePath, final String enemiesPath, 
                            final String coinsPath, final GameLoopController gameController) {
        this.gameController = gameController;
        this.frameMenu = frameMenu;
        music = new GameMusic("gameMusic.wav");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(gameController.getCommands());
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.chronometer = new Chronometer();
    }

    /**
     * Initialize a new thread and call start method.
     */
    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
        //music.play(true);
    }

    /**
     * Contains the loop for updating and repainting the game.
     */
    @Override
    public void run() {
        double timeFor1Frame = MLD/FPS;
        long lastTime = System.nanoTime();
        long currentTime;
        double waitingTime = 0;

        while (!Thread.currentThread().isInterrupted()) {
            currentTime = System.nanoTime();
            waitingTime += currentTime - lastTime;
            lastTime = currentTime;

            if (waitingTime >= timeFor1Frame) {
                update();
                repaint();
                waitingTime -= timeFor1Frame;
            }
        }
    }

    /**
     * Called when the game is ended to interrupt the thread.
     */
    public void endGame() {
        gameThread.interrupt();
    }

    /**
     * Check if the gamestarted is false starts the chronometer.
     * Handles the end of the level and the game.
     */
    public void update() {
        if (!gameStarted) {
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

    /**
     * Decides which panel show based on if the level is finished or the player is dead.
     */
    private void showEndPanel() {
        //aggiorno livelli:
        if (levelCompleted) {
            int currentLevel = gameController.getCurrentLevel();
            GameSaveManager save = GameSaveManager.getInstance();

            if (currentLevel >= save.getLevelsCompleted() + 1) {
                save.setLevelsCompleted(currentLevel);
            }
        }
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

    /**
     * Render all the part of the game.
     */
    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D gr2 = (Graphics2D) gr;
        
        gameController.getMapHandler().printBlocks(gr2, gameController.getPlayer());
        gameController.getPowersManager().printPowerUp(gr2);
        gameController.getPlayer().drawPlayer(gr2);
        gameController.getEnemyHandler().render(gr2);
        gameController.getCoinController().drawAllCoins(gr2, gameController.getMapHandler().getTileSize(), 
                                                        gameController.getPlayer());
        gr2.setColor(Color.BLACK);
        gr2.setFont(new Font("Cooper Black", Font.BOLD, FONT_X));
        gr2.drawString("TIME:" + chronometer.getTimeString(), FONT_X, FONT_TIME_Y);
        gr2.drawString("COINS:" + gameController.getCoinController().getCoinsCollected(), FONT_X, FONT_COIN_Y);

        gr2.dispose();
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
