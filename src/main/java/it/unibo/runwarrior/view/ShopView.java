package it.unibo.runwarrior.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unibo.runwarrior.controller.shop.api.ShopController;
import it.unibo.runwarrior.controller.shop.impl.ShopControllerImpl;
import it.unibo.runwarrior.model.Score;

/**
 * Class that creates the Panel of the shop to be showed after the click on the 
 * shop button in the menu.
 */
public final class ShopView extends JPanel {
    private static final int TITLE_FONT_SIZE = 24;
    private static final int RIGID_AREA_HEIGHT_LARGE = 20;
    private static final int RIGID_AREA_HEIGHT_MEDIUM = 10;
    private static final int RIGID_AREA_HEIGHT_SMALL = 5;
    private static final Color COLOR_SHOP_BACKGROUND = new Color(255, 192, 203);
    private final ShopController shopController;
    private final JLabel coinLabel;
    private final JLabel skinStateLabel; 
    private final JButton buySkinButton;

    /**
     * ShopView constructor.
     *
     * @param score to take the coins collected
     */
    public ShopView(final Score score) {
        this.shopController = new ShopControllerImpl(score);
        setBackground(COLOR_SHOP_BACKGROUND);
        setOpaque(true);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel("SHOP");
        final Font font = new Font("Cooper Black", Font.BOLD, TITLE_FONT_SIZE);
        titleLabel.setFont(font);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        coinLabel = new JLabel();
        coinLabel.setFont(font);
        coinLabel.setAlignmentX(CENTER_ALIGNMENT);

        skinStateLabel = new JLabel();
        skinStateLabel.setFont(font);
        skinStateLabel.setAlignmentX(CENTER_ALIGNMENT);

        buySkinButton = new JButton("BUY SKIN");
        buySkinButton.setAlignmentX(CENTER_ALIGNMENT);
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
        selectDefaultSkinButton.setAlignmentX(CENTER_ALIGNMENT);
        selectDefaultSkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                shopController.selectSkin(shopController.getDefaultSkin());
                JOptionPane.showMessageDialog(null, "Default skin successfully selected!");
                updateShop();
            }
        });
        final JButton selectWizardSkinButton = new JButton("SELECT WIZARD SKIN");
        selectWizardSkinButton.setAlignmentX(CENTER_ALIGNMENT);
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
        add(Box.createRigidArea(new Dimension(0, RIGID_AREA_HEIGHT_LARGE)));
        add(coinLabel);
        add(Box.createRigidArea(new Dimension(0, RIGID_AREA_HEIGHT_MEDIUM)));
        add(skinStateLabel);
        add(Box.createRigidArea(new Dimension(0, RIGID_AREA_HEIGHT_LARGE)));
        add(buySkinButton);
        add(Box.createRigidArea(new Dimension(0, RIGID_AREA_HEIGHT_MEDIUM)));
        add(selectDefaultSkinButton);
        add(Box.createRigidArea(new Dimension(0, RIGID_AREA_HEIGHT_SMALL)));
        add(selectWizardSkinButton);
        updateShop();
    }

    /**
     * methot that update the shop based on the coins and the state of the skins.
     */
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
