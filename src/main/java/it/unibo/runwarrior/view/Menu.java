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

import it.unibo.runwarrior.controller.GameLoopController;
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
        private final static int FRAME_MENU_WIDTH = 1280;
        private final static int FRAME_MENU_HEIGHT = 720;
        private final static int PLAY_BUTTON_PANEL_WIDTH = 250;
        private final static int PLAY_BUTTON_PANEL_HEIGHT = 500;
        private final static int BUTTON_WIDTH = 150;
        private final static int BUTTON_HEIGHT = 40;
        private static final int SHOP_FRAME_WIDHT = 600;
        private static final int SHOP_FRAME_HEIGHT = 400;
        //creo il frame e imposto il titolo 
        private JFrame frameMenu;
        private BufferedImage immagineSfondo;
        private BufferedImage imgTitolo;

        public Menu() {
            this.frameMenu = new JFrame();
            initMenu();
        }

        public Menu(JFrame frame_esterno){
            this.frameMenu = frame_esterno;
            initMenu();  // stessa logica
        }

        public void initMenu(){
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
            frameMenu.setSize(FRAME_MENU_WIDTH, FRAME_MENU_HEIGHT);
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
            //pannelloSfondoMenu.setLayout(null);
            pannelloSfondoMenu.setLayout(new BorderLayout());
            pannelloSfondoMenu.setLayout(new BoxLayout(pannelloSfondoMenu, BoxLayout.Y_AXIS));
            pannelloSfondoMenu.setOpaque(true);

            final JLabel titoloLabel = new JLabel(new ImageIcon(imgTitolo));
            titoloLabel.setAlignmentX(CENTER_ALIGNMENT);
            //titoloLabel.setBounds(TITOLO_X, TITOLO_Y, TITOLO_WIDTH, TITOLO_HEIGHT);
            pannelloSfondoMenu.add(Box.createVerticalGlue());
            pannelloSfondoMenu.add(titoloLabel);
            final JPanel playButtonPanel = new JPanel();
            playButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            playButtonPanel.setOpaque(false);
            //playButtonPanel.setBounds(PLAY_PANEL_X, PLAY_PANEL_Y, PLAY_PANEL_WIDTH, PLAY_PANEL_HEIGHT);
            playButtonPanel.setAlignmentX(CENTER_ALIGNMENT);
            pannelloSfondoMenu.add(Box.createVerticalStrut(20));
            pannelloSfondoMenu.add(playButtonPanel);
            pannelloSfondoMenu.add(Box.createHorizontalGlue());
            playButtonPanel.setPreferredSize(new Dimension(PLAY_BUTTON_PANEL_WIDTH, PLAY_BUTTON_PANEL_HEIGHT));

            final JButton playButton = new JButton("PLAY");
            final Dimension buttonsDimension = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
            playButton.setMaximumSize(buttonsDimension);
            playButton.setPreferredSize(buttonsDimension);
            playButton.setMinimumSize(buttonsDimension);
            playButton.setBackground(new Color(120, 124, 126));
            final Font font = new Font("Cooper Black", Font.BOLD, 16);
            playButton.setFont(font);
            playButton.setForeground(Color.BLACK);
            playButton.setBorder(new LineBorder(new Color(85, 89, 91), 4));

            playButton.addActionListener(new ActionListener() {
                private GameLoopController glc;
                public void actionPerformed(final ActionEvent e) {
                    playButtonPanel.remove(playButton);
                    playButtonPanel.revalidate();
                    playButtonPanel.repaint();

                    final JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                    final JButton level1 = new JButton("LEVEL 1");
                    final JButton level2 = new JButton("LEVEL 2");
                    final JButton level3 = new JButton("LEVEL 3");
                    level1.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level1.setMaximumSize(buttonsDimension);
                    level1.setPreferredSize(buttonsDimension);
                    level1.setFont(font);
                    level1.setBackground(new Color(218, 165, 32));
                    level1.setBorder(new LineBorder(new Color(180, 130, 25), 4)); 
                    level1.setForeground(Color.BLACK);
                    level2.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level2.setMaximumSize(buttonsDimension);
                    level2.setPreferredSize(buttonsDimension);
                    level2.setFont(font);
                    level2.setBackground(new Color(60, 179, 60));
                    level2.setBorder(new LineBorder(new Color(40, 120, 40), 4));
                    level2.setForeground(Color.BLACK);
                    level3.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level3.setMaximumSize(buttonsDimension);
                    level3.setPreferredSize(buttonsDimension);
                    level3.setFont(font);
                    level3.setBackground(new Color(120, 124, 126));
                    level3.setBorder(new LineBorder(new Color(85, 89, 91), 4));
                    level3.setForeground(Color.BLACK);

                    level1.addActionListener(level1Event -> {
                        glc = new GameLoopController(frameMenu, "Map1/map_1.txt", "Map1/desert_theme.txt",
                        "/Map1/enemiesMap1.txt", "/Coins/CoinCoordinates_map1.txt");
                        glc.getGlp().startGame();
                        frameMenu.getContentPane().removeAll();
                        frameMenu.setContentPane(glc.getGlp());
                        frameMenu.revalidate();
                        frameMenu.repaint();
                        glc.getGlp().setFocusable(true);
                        glc.getGlp().requestFocusInWindow();
                        glc.getGlp().requestFocus();
                    });
                    level2.addActionListener(level2Event -> {
                        glc = new GameLoopController(frameMenu, "Map2/map2.txt", "Map2/forest_theme.txt",
                        "/Map2/enemiesMap2.txt", "/Coins/CoinCoordinates_map2.txt");
                        glc.getGlp().startGame();
                        frameMenu.getContentPane().removeAll();
                        frameMenu.setContentPane(glc.getGlp());
                        frameMenu.revalidate();
                        frameMenu.repaint();
                        glc.getGlp().setFocusable(true);
                        glc.getGlp().requestFocusInWindow();
                        glc.getGlp().requestFocus();
                    });
                    level3.addActionListener(level3Event -> {
                        glc = new GameLoopController(frameMenu, "Map_3/map_3.txt", "Map_3/map3Theme.txt",
                        "/Map_3/enemiesMap3.txt", "/Coins/CoinCoordinates_map3.txt");
                        glc.getGlp().startGame();
                        frameMenu.getContentPane().removeAll();
                        frameMenu.setContentPane(glc.getGlp());
                        frameMenu.revalidate();
                        frameMenu.repaint();
                        glc.getGlp().setFocusable(true);
                        glc.getGlp().requestFocusInWindow();
                        glc.getGlp().requestFocus();
                        
                    });
                    final JButton shopButton = new JButton("SHOP");
                    shopButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    shopButton.setMaximumSize(new Dimension(buttonsDimension));
                    shopButton.setPreferredSize(buttonsDimension);
                    shopButton.setFont(new Font("Cooper Black", Font.BOLD, 14));
                    shopButton.setBackground(new Color(70, 130, 180));
                    shopButton.setForeground(Color.WHITE);
                    shopButton.setBorder(new LineBorder(new Color(30, 90, 150), 4));

                    shopButton.addActionListener(shopEvent -> {
                        final JFrame shopFrame = new JFrame("SHOP");
                        shopFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        shopFrame.setSize(SHOP_FRAME_WIDHT, SHOP_FRAME_HEIGHT);
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
                    playButtonPanel.setPreferredSize(new Dimension(PLAY_BUTTON_PANEL_WIDTH, PLAY_BUTTON_PANEL_HEIGHT));
                    playButtonPanel.removeAll();
                    playButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
                    playButtonPanel.add(panel);
                    playButtonPanel.revalidate();
                    playButtonPanel.repaint();

                }
            });
            playButtonPanel.add(playButton, BorderLayout.CENTER); 
            pannelloSfondoMenu.add(playButtonPanel);
            frameMenu.setContentPane(pannelloSfondoMenu);
            frameMenu.setVisible(true);
        }

        public JPanel getPanel() {
            return new Menu();
        }

        public JFrame getFrameMenu() {
            return frameMenu;
        }
}
