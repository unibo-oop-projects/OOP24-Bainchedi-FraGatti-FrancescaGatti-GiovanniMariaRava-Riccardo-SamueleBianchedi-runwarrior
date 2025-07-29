package it.unibo.runwarrior.view;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class that creates the panel to be showed after the completion of the level.
 */
public class LevelCompletedPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int RIGID_AREA_HEIGHT_LARGE = 20;
    private static final int RIGID_AREA_HEIGHT_MEDIUM = 10;
    private final JLabel titleLabel;
    private final JLabel coinLabel;
    private final JLabel timeLabel;

    /**
     * Constructor of the level completed panel.
     *
     * @param time time elapsed to complete the level
     * @param coins coins collected during the game
     */
    public LevelCompletedPanel(final String time, final int coins) {
        titleLabel = new JLabel("LEVEL COMPLETED!");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        coinLabel = new JLabel();
        coinLabel.setAlignmentX(CENTER_ALIGNMENT);
        coinLabel.setText("coins:" + coins);
        timeLabel = new JLabel();
        timeLabel.setAlignmentX(CENTER_ALIGNMENT);
        timeLabel.setText("time:" + time);
        initialize();
    }

    /**
     * Initialize panel.
     */
    private void initialize() {
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        super.add(titleLabel);
        super.add(Box.createRigidArea(new Dimension(0, RIGID_AREA_HEIGHT_LARGE)));
        super.add(coinLabel);
        super.add(Box.createRigidArea(new Dimension(0, RIGID_AREA_HEIGHT_MEDIUM)));
        super.add(timeLabel);
    }
}
