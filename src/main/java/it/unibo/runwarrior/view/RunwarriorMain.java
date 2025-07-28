package it.unibo.runwarrior.view;

import javax.swing.JFrame;

public class RunwarriorMain{
    private final static int FRAME_MENU_WIDTH = 1280;
    private final static int FRAME_MENU_HEIGHT = 720;

    public RunwarriorMain() {
        final JFrame gameFrame = new JFrame();
        gameFrame.setSize(FRAME_MENU_WIDTH, FRAME_MENU_HEIGHT);
        gameFrame.setLocationRelativeTo(null);
        final Menu menu = new Menu(gameFrame); 
        gameFrame.setContentPane(menu.getPanel());
        gameFrame.setVisible(true);
    }

    public static void main(final String[] args) {
        new RunwarriorMain();
    }
}
