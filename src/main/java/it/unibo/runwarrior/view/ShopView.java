package it.unibo.runwarrior.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.unibo.runwarrior.controller.ShopController;
//import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;

public class ShopView extends JPanel{
    private final ShopController shopController;
    private final JLabel coinLabel;
    private final JLabel skinStateLabel; 
    private final JButton buySkinButton;

    public ShopView(Score score){
        this.shopController = new ShopController(score);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel titleLabel = new JLabel("SHOP");
        titleLabel.setFont(new Font("Cooper Black", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        coinLabel = new JLabel();
        coinLabel.setFont(new Font("Cooper Black", Font.BOLD, 14));
        coinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        skinStateLabel = new JLabel();
        skinStateLabel.setFont(new Font("Cooper Black", Font.BOLD, 14));
        skinStateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buySkinButton = new JButton("BUY SKIN");
        buySkinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buySkinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(shopController.buyPremiumSkin()){
                    JOptionPane.showMessageDialog(null, "Skin purchased successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough coins or skin already purchased");
                }
            }
        });
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(coinLabel);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(skinStateLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(buySkinButton);
        updateShop();
    }
    
    private void updateShop(){
        int coins = shopController.getCoinScore();
        coinLabel.setText("coins:" + coins);

        boolean unlocked = shopController.isPremiumSkinUnlocked();
        if(unlocked){
            skinStateLabel.setText("Skin 'Wizard' : BOUGHT");
            buySkinButton.setEnabled(false);
        } else{
            skinStateLabel.setText("Skin 'Wizard' : NOT BOUGHT");
            buySkinButton.setEnabled(false);
        }
    }
}