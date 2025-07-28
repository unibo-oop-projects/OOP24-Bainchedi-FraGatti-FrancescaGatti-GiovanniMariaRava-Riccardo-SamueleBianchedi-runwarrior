package it.unibo.runwarrior.view;

import javax.swing.JFrame;

public class RunwarriorMain{

    public RunwarriorMain() {
        JFrame gameFrame = new JFrame();
        Menu menu = new Menu(gameFrame); 
        gameFrame.setContentPane(menu.getPanel());
        gameFrame.setVisible(true);
    }

    public static void main(final String[] args) {
        new RunwarriorMain();
    }
}
