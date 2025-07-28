package it.unibo.runwarrior.view;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOverPanel extends JPanel {
    public GameOverPanel(final int coins){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel("GAME OVER");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JLabel coinLabel = new JLabel("Coins collected: " + coins);
        coinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalStrut(30));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(20));
        this.add(coinLabel);
        this.add(Box.createVerticalStrut(30));
    }      
}
