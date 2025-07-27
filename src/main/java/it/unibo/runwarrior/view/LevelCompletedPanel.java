package it.unibo.runwarrior.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LevelCompletedPanel extends JPanel {

    public LevelCompletedPanel(final String time, final int coins){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel("LEVEL COMPLETED!");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        final JLabel coinLabel = new JLabel();
        coinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        coinLabel.setText("coins:" + coins);
        final JLabel timeLabel = new JLabel();
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setText("time:" + time);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(coinLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(timeLabel);
    }
}
