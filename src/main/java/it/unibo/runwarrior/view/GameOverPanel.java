package it.unibo.runwarrior.view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * class that creates the panel that appears after the gameover.
 * It says the number if coins the player has collected during the game.
 */
public class GameOverPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int VERTICAL_STRUT_HEIGHT_LARGE = 30;
    private static final int VERTICAL_STRUT_HEIGHT_MEDIUM = 20;
    private final JLabel titleLabel;
    private final JLabel coinLabel;

    /**
     * Game over panel constructor.
     *
     * @param coins it represents the number of coins collected during the game
     */
    public GameOverPanel(final int coins) {
        titleLabel = new JLabel("GAME OVER");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        coinLabel = new JLabel("Coins collected: " + coins);
        coinLabel.setAlignmentX(CENTER_ALIGNMENT);
        initialize();
    }

    /**
     * Initialize panel.
     */
    private void initialize() {
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        super.add(Box.createVerticalStrut(VERTICAL_STRUT_HEIGHT_LARGE));
        super.add(titleLabel);
        super.add(Box.createVerticalStrut(VERTICAL_STRUT_HEIGHT_MEDIUM));
        super.add(coinLabel);
        super.add(Box.createVerticalStrut(VERTICAL_STRUT_HEIGHT_LARGE));
    }
}
