package it.unibo.runwarrior.view;

// import java.awt.BorderLayout;
// import java.awt.Color;
// import java.awt.GridLayout;
// import java.util.List;

// import javax.swing.BorderFactory;
// import javax.swing.BoxLayout;
// import javax.swing.JButton;
import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.SwingConstants;

// import it.unibo.runwarrior.controller.ShopController;
// import it.unibo.runwarrior.model.Skin;

public class ShopView extends JFrame{
    // private ShopController shopController; 
    // private JLabel coinLabel; 

    // public ShopView(ShopController shopController){
    //     this.shopController = shopController;
    // }

    // private void shopFrame(){
    //     setTitle("Shop");
    //     setSize(600,400);
    //     setLocationRelativeTo(null);
    //     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    //     setLayout(new BorderLayout());

    //     //parte con quante monete ho nel portafoglio
    //     coinLabel = new JLabel("WALLET: "+ shopController.getCoinScore(), SwingConstants.CENTER);
    //     add(coinLabel, BorderLayout.NORTH);

    //     //pannello con le skin da comprare e non 
    //     JPanel skinsPanel = new JPanel(new GridLayout(0,2,10,10));
    //     skinsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    //     List<Skin> skins = shopController.getAllSkins();

    //     for(Skin skin : skins){
    //         JPanel oneSkinPanel = new JPanel();
    //         oneSkinPanel.setLayout(new BoxLayout(oneSkinPanel, BoxLayout.Y_AXIS)); //me li mette in verticale
    //         oneSkinPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    //         JLabel nameSkinLabel = new JLabel(skin.getPrice() + "$", SwingConstants.CENTER);
    //         nameSkinLabel.setAlignmentX(CENTER_ALIGNMENT);

    //         JButton buyButton = new JButton(skin.saleState() ? "USE" : "BUY");
    //         buyButton.setAlignmentX(CENTER_ALIGNMENT);
    //         buyButton.setEnabled(!skin.saleState());

    //         buyButton.addActionListener(e -> {
    //             //bottone del compra o usa 
    //         });

    //     }
    // }

}
