package it.unibo.runwarrior.view;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * class that creates the panel that appears after the gameover.
 * It says the number if coins the player has collected during the game.
 */
public class GameOverPanel extends JPanel {
    private static final int VERTICAL_STRUT_HEIGHT_LARGE = 30;
    private static final int VERTICAL_STRUT_HEIGHT_MEDIUM = 20;

    /**
     * Game over panel constructor.
     *
     * @param coins it represents the number of coins collected during the game
     */
    public GameOverPanel(final int coins) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel("GAME OVER");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JLabel coinLabel = new JLabel("Coins collected: " + coins);
        coinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_HEIGHT_LARGE));
        this.add(titleLabel);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_HEIGHT_MEDIUM));
        this.add(coinLabel);
        this.add(Box.createVerticalStrut(VERTICAL_STRUT_HEIGHT_LARGE));
    }
}
