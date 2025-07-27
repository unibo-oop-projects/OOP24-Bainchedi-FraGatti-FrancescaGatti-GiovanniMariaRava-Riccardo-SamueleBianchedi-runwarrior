package it.unibo.runwarrior.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import it.unibo.runwarrior.controller.ShopControllerImpl;
import it.unibo.runwarrior.model.Score;

public class ShopView extends JPanel {
    private final ShopControllerImpl shopController;
    private final JLabel coinLabel;
    private final JLabel skinStateLabel; 
    private final JButton buySkinButton;

    public ShopView(final Score score) {
        this.shopController = new ShopControllerImpl(score);
        setBackground(new java.awt.Color(255, 192, 203));
        setOpaque(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel("SHOP");
        final Font font = new Font("Cooper Black", Font.BOLD, 24);
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        coinLabel = new JLabel();
        coinLabel.setFont(font);
        coinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        skinStateLabel = new JLabel();
        skinStateLabel.setFont(font);
        skinStateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buySkinButton = new JButton("BUY SKIN");
        buySkinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buySkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (shopController.buyPremiumSkin()) {
                    JOptionPane.showMessageDialog(null, "Skin purchased successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough coins or skin already purchased");
                }
                updateShop();
            }
        });
        final JButton selectDefaultSkinButton = new JButton("SELECT DEFAULT SKIN");
        selectDefaultSkinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectDefaultSkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                shopController.selectSkin(shopController.getDefaultSkin());
                JOptionPane.showMessageDialog(null, "Default skin successfully selected!");
                updateShop();
            }
        });
        final JButton selectWizardSkinButton = new JButton("SELECT WIZARD SKIN");
        selectWizardSkinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectWizardSkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!shopController.isPremiumSkinUnlocked()) {
                    JOptionPane.showMessageDialog(null,
                    "The 'Wizard' skin isn't available in the wardrobe yet. Get it first!");
                } else {
                    shopController.selectSkin(shopController.getPremiumSkin());
                    JOptionPane.showMessageDialog(null,
                    "'Wizard' skin successfully selected!");
                }
                updateShop();
            }
        });
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(coinLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(skinStateLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(buySkinButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(selectDefaultSkinButton);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(selectWizardSkinButton);
        updateShop();
    }

    private void updateShop() {
        final int coins = shopController.getCoinScore();
        coinLabel.setText("coins:" + coins);

        final boolean unlocked = shopController.isPremiumSkinUnlocked();
        if (unlocked) {
            skinStateLabel.setText("Skin 'Wizard' : BOUGHT");
            buySkinButton.setEnabled(false);
        } else {
            skinStateLabel.setText("Skin 'Wizard' : NOT BOUGHT");
            buySkinButton.setEnabled(true);
        }
    }
}
