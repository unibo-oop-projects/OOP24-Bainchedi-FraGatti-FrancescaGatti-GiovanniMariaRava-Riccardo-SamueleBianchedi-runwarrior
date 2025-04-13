package it.unibo.runwarrior.view;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GameLoopPanel extends JPanel implements Runnable{
    
    public static final int WIDTH = 960;
    public static final int HEIGHT = 672;

    private Thread gameThread;

    public GameLoopPanel(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void startGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        
    }
}
