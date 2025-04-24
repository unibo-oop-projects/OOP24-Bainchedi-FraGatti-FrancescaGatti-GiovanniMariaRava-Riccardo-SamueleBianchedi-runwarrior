package it.unibo.runwarrior.view;

import javax.swing.JFrame;

public class RunwarriorMain{

    private JFrame mainFrame;
    private GameLoopPanel glp;

    public RunwarriorMain(){
        mainFrame = new JFrame("runwarrior");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationByPlatform(false);
        mainFrame.setResizable(true);

        glp = new GameLoopPanel();
        glp.startGame();

        mainFrame.add(glp);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    public static void main(String[] args){
        new RunwarriorMain();
    }
}
