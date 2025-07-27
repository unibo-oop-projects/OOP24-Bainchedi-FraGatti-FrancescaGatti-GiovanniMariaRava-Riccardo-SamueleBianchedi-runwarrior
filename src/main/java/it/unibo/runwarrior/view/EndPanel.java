package it.unibo.runwarrior.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class EndPanel extends JPanel {
    
    private final String time;
    private final int coins;

    public EndPanel(String time, int coins) {
        this.time = time;
        this.coins = coins;
        this.setPreferredSize(new Dimension(GameLoopPanel.WIDTH, GameLoopPanel.HEIGHT));
        this.setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr2 = (Graphics2D) g;

        gr2.setColor(Color.WHITE);
        gr2.setFont(new Font("Cooper Black", Font.BOLD, 40));
        gr2.drawString("LEVEL COMPLETED!", 300, 200);

        gr2.setFont(new Font("Arial", Font.PLAIN, 30));
        gr2.drawString("time: " + time, 320, 300);
        gr2.drawString("coins collected: " + coins, 320, 360);
    }
}