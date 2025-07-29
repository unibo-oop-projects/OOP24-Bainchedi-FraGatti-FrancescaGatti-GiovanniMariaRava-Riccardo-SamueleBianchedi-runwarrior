package it.unibo.runwarrior.view;

import javax.swing.JFrame;

/**
 * Main class of the game.
 */
public final class RunwarriorMain {
    private static final int FRAME_MENU_WIDTH = 1280;
    private static final int FRAME_MENU_HEIGHT = 720;

    /**
     * Constructor of the main, that initialize the gameFrame and the menu.
     */
    private RunwarriorMain() {
        // void
    }

    /**
     * game initializer.
     */
    private static void initializeGame() {
        final JFrame gameFrame = new JFrame();
        gameFrame.setSize(FRAME_MENU_WIDTH, FRAME_MENU_HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        final Menu menu = new Menu(gameFrame); 
        gameFrame.setContentPane(menu.getPanel());
        gameFrame.setVisible(true);
    }

    /**
     * start the game.
     */
    public static void start(){
        initializeGame();
    }

    /**
     * Entry point of the application. Launches the game.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        start();
    }
}
