package it.unibo.runwarrior.view;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu extends JPanel {
        //creo il frame e imposto il titolo 
        private JFrame frameMenu = new JFrame();
        private BufferedImage immagineSfondo;
        private BufferedImage imgTitolo;

        public Menu() {
            frameMenu.setTitle("RUN WARRIOR");
            frameMenu.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frameMenu.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent e) {
                    final int n = JOptionPane.showConfirmDialog(frameMenu, "Do you want to save your game data?", 
                        "Quitting...", JOptionPane.YES_NO_OPTION); 
                    if (n == JOptionPane.YES_OPTION) {
                        GameSaveManager.getInstance().saveGame();
                        System.exit(0);
                    } else if (n == JOptionPane.NO_OPTION) {
                        GameSaveManager.getInstance().resetGame();
                        System.exit(0);
                    }
                }
            });
            frameMenu.setSize(1280, 720);
            frameMenu.setLocationRelativeTo(null);
            try {
                immagineSfondo = ImageIO.read(getClass().getResourceAsStream("/Menu/sfondoMenu.png"));
                imgTitolo = ImageIO.read(getClass().getResourceAsStream("/Menu/titolo4.png"));
            } catch (final IOException e) {
                e.printStackTrace();
            }

            final JPanel pannelloSfondoMenu = new JPanel() {
                @Override
                protected void paintComponent(final Graphics g) {
                    super.paintComponent(g);
                    if (immagineSfondo != null) {
                        g.drawImage(immagineSfondo, 0, 0, getWidth(), getHeight(), this); 
                    }
                }
            };
            pannelloSfondoMenu.setLayout(null);
            pannelloSfondoMenu.setOpaque(true);

            final JLabel titoloLabel = new JLabel(new ImageIcon(imgTitolo));
            titoloLabel.setBounds(440, 100, 400, 300);
            pannelloSfondoMenu.add(titoloLabel);
            final JPanel pannelloTastoPlay = new JPanel();
            pannelloTastoPlay.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            pannelloTastoPlay.setOpaque(false);
            pannelloTastoPlay.setBounds(430, 300, 400, 500);
            pannelloTastoPlay.setPreferredSize(new Dimension(250, 500));

            final JButton playButton = new JButton("PLAY");
            final Dimension buttonPlayDimension = new Dimension(150, 40);
            playButton.setMaximumSize(buttonPlayDimension);
            playButton.setPreferredSize(buttonPlayDimension);
            playButton.setMinimumSize(buttonPlayDimension);
            playButton.setBackground(new Color(120, 124, 126));
            final Font font = new Font("Cooper Black", Font.BOLD, 16);
            playButton.setFont(font);
            playButton.setForeground(Color.BLACK);
            playButton.setBorder(new LineBorder(new Color(85, 89, 91), 4));

            playButton.addActionListener(new ActionListener() {
                private JFrame mainFrame;
                private GameLoopPanel glp;
                public void actionPerformed(final ActionEvent e) {
                    pannelloTastoPlay.remove(playButton);
                    pannelloTastoPlay.revalidate();
                    pannelloTastoPlay.repaint();

                    final JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    final Dimension buttonLevelDimension = new Dimension(150, 40);

                    final JButton level1 = new JButton("LEVEL 1");
                    final JButton level2 = new JButton("LEVEL 2");
                    final JButton level3 = new JButton("LEVEL 3");
                    level1.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level1.setMaximumSize(buttonLevelDimension);
                    level1.setPreferredSize(buttonLevelDimension);
                    level1.setFont(font);
                    level1.setBackground(new Color(218, 165, 32));
                    level1.setBorder(new LineBorder(new Color(180, 130, 25), 4)); 
                    level1.setForeground(Color.BLACK);
                    level2.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level2.setMaximumSize(buttonLevelDimension);
                    level2.setPreferredSize(buttonLevelDimension);
                    level2.setFont(font);
                    level2.setBackground(new Color(60, 179, 60));
                    level2.setBorder(new LineBorder(new Color(40, 120, 40), 4));
                    level2.setForeground(Color.BLACK);
                    level3.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level3.setMaximumSize(buttonLevelDimension);
                    level3.setPreferredSize(buttonLevelDimension);
                    level3.setFont(font);
                    level3.setBackground(new Color(120, 124, 126));
                    level3.setBorder(new LineBorder(new Color(85, 89, 91), 4));
                    level3.setForeground(Color.BLACK);

                    level1.addActionListener(level1Event -> {
                        mainFrame = new JFrame("runwarrior");
                        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        mainFrame.setLocationByPlatform(false);
                        mainFrame.setResizable(true);
                        glp = new GameLoopPanel("Map1/map_1.txt", "Map1/desert_theme.txt",
                        "/Map1/enemiesMap1.txt", "/Coins/CoinCoordinates_map1.txt");
                        glp.startGame();
                        mainFrame.add(glp);
                        mainFrame.pack();
                        mainFrame.setVisible(true);
                    });
                    level2.addActionListener(level2Event -> {
                        mainFrame = new JFrame("runwarrior");
                        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        mainFrame.setLocationByPlatform(false);
                        mainFrame.setResizable(true);
                        glp = new GameLoopPanel("Map2/map2.txt", "Map2/forest_theme.txt",
                        "/Map2/enemiesMap2.txt", "/Coins/CoinCoordinates_map2.txt");
                        glp.startGame();
                        mainFrame.add(glp);
                        mainFrame.pack();
                        mainFrame.setVisible(true);
                    });
                    level3.addActionListener(level3Event -> {
                        mainFrame = new JFrame("runwarrior");
                        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        mainFrame.setLocationByPlatform(false);
                        mainFrame.setResizable(true);
                        glp = new GameLoopPanel("Map_3/map_3.txt", "Map_3/map3Theme.txt",
                        "/Map_3/enemiesMap3.txt", "/Coins/CoinCoordinates_map3.txt");
                        glp.startGame();
                        mainFrame.add(glp);
                        mainFrame.pack();
                        mainFrame.setVisible(true);
                    });
                    final JButton shopButton = new JButton("SHOP");
                    shopButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    shopButton.setMaximumSize(new Dimension(150, 40));
                    shopButton.setPreferredSize(new Dimension(150, 40));
                    shopButton.setFont(new Font("Cooper Black", Font.BOLD, 14));
                    shopButton.setBackground(new Color(70, 130, 180));
                    shopButton.setForeground(Color.WHITE);
                    shopButton.setBorder(new LineBorder(new Color(30, 90, 150), 3));

                    shopButton.addActionListener(shopEvent -> {
                        final JFrame shopFrame = new JFrame("SHOP");
                        shopFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        shopFrame.setSize(600, 400);
                        shopFrame.setLocationRelativeTo(null);
                        shopFrame.setResizable(false);

                        // Inserisci la ShopView collegata a GameSaveManager e Score
                        final ShopView shopView = new ShopView(new Score(GameSaveManager.getInstance()));
                        shopFrame.add(shopView);
                        shopFrame.setVisible(true);
                    });
                    panel.setOpaque(false);
                    panel.add(Box.createVerticalStrut(10));
                    panel.add(level1);
                    panel.add(Box.createVerticalStrut(10));
                    panel.add(level2);
                    panel.add(Box.createVerticalStrut(10));
                    panel.add(level3);
                    panel.add(Box.createVerticalStrut(10));
                    panel.add(shopButton);
                    pannelloTastoPlay.setPreferredSize(new Dimension(250, 400));
                    pannelloTastoPlay.removeAll();
                    pannelloTastoPlay.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                    pannelloTastoPlay.add(panel);
                    pannelloTastoPlay.revalidate();
                    pannelloTastoPlay.repaint();

                }
            });
            pannelloTastoPlay.add(playButton, BorderLayout.CENTER); 
            pannelloSfondoMenu.add(pannelloTastoPlay);
            frameMenu.setContentPane(pannelloSfondoMenu);
            frameMenu.setVisible(true);
        }
}
