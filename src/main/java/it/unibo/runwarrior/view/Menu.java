package it.unibo.runwarrior.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import it.unibo.runwarrior.model.GameSaveManager;
import it.unibo.runwarrior.model.Score;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu extends JPanel{
        //creo il frame e imposto il titolo 
        final JFrame frameMenu = new JFrame();
        BufferedImage immagineSfondo = null;
        BufferedImage imgTastoPlay = null; 
        BufferedImage imgTitolo = null; 

        public Menu(){
            frameMenu.setTitle("RUN WARRIOR");
            frameMenu.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frameMenu.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e){
                    int n = JOptionPane.showConfirmDialog(frameMenu, "Do you want to save your game data?", "Quitting...", JOptionPane.YES_NO_OPTION); 
                    if(n==JOptionPane.YES_OPTION){
                        GameSaveManager.getInstance().saveGame();
                        System.exit(0);
                    } else if (n == JOptionPane.NO_OPTION){
                        GameSaveManager.getInstance().resetGame();
                        System.exit(0);
                    } else if (n == JOptionPane.CANCEL_OPTION || n == JOptionPane.CLOSED_OPTION){}
                }
            });
            frameMenu.setSize(1280, 720); 
            frameMenu.setLocationRelativeTo(null); //per centrare la finestra
 
            //ImageIcon immagineSfondo = new ImageIcon("C:\Users\Utente\Desktop\OOP24-runwarrior\src\main\resources\Menu\sfondoMenu.png"); 
            try{
                immagineSfondo = ImageIO.read(getClass().getResourceAsStream("/Menu/sfondoMenu.png"));
                imgTitolo = ImageIO.read(getClass().getResourceAsStream("/Menu/titolo4.png"));
                imgTastoPlay = ImageIO.read(getClass().getResourceAsStream("/Menu/tastoPlay.png"));
            }catch(IOException e){
                e.printStackTrace();
            }

            final JPanel pannelloSfondoMenu = new JPanel(){
                @Override
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    if(immagineSfondo != null){
                        g.drawImage(immagineSfondo, 0, 0, getWidth(), getHeight(), this); 
                    }
                }
            };
            pannelloSfondoMenu.setLayout(null);
            pannelloSfondoMenu.setOpaque(true);

            JLabel titoloLabel = new JLabel(new ImageIcon(imgTitolo)); 
            titoloLabel.setBounds(440, 100, 400, 300);
            pannelloSfondoMenu.add(titoloLabel);
            final JPanel pannelloTastoPlay = new JPanel(); 
            pannelloTastoPlay.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            pannelloTastoPlay.setOpaque(false);
            pannelloTastoPlay.setBounds(430, 300, 400, 500);
            pannelloTastoPlay.setPreferredSize(new Dimension(250, 500));

            final JButton playButton = new JButton("PLAY");
            Dimension buttonPlayDimension = new Dimension(150, 40);
            playButton.setMaximumSize(buttonPlayDimension);
            playButton.setPreferredSize(buttonPlayDimension);
            playButton.setMinimumSize(buttonPlayDimension);
            playButton.setBackground(new Color(120, 124, 126));
            playButton.setFont(new Font("Cooper Black", Font.BOLD, 16));
            playButton.setForeground(Color.BLACK);
            playButton.setBorder(new LineBorder(new Color(85, 89, 91), 4));

            playButton.addActionListener(new ActionListener() {
                private JFrame mainFrame;
                private GameLoopPanel glp;
                public void actionPerformed(ActionEvent e){ 
                    pannelloTastoPlay.remove(playButton);
                    pannelloTastoPlay.revalidate();
                    pannelloTastoPlay.repaint();

                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    Dimension buttonLevelDimension = new Dimension(150,40);
                    Font fontButton = new Font("Cooper Black", Font.BOLD, 16);

                    JButton level1 = new JButton("LEVEL 1");
                    JButton level2 = new JButton("LEVEL 2");
                    JButton level3 = new JButton("LEVEL 3");
                    level1.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level1.setMaximumSize(buttonLevelDimension);
                    level1.setPreferredSize(buttonLevelDimension);
                    level1.setFont(fontButton);
                    level1.setBackground(new Color(218, 165, 32));
                    level1.setBorder(new LineBorder(new Color(180,130,25), 4));  
                    level1.setForeground(Color.BLACK);                  
                    level2.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level2.setMaximumSize(buttonLevelDimension);
                    level2.setPreferredSize(buttonLevelDimension);
                    level2.setFont(fontButton);
                    level2.setBackground(new Color(60, 179, 60));
                    level2.setBorder(new LineBorder(new Color(40, 120, 40), 4));     
                    level2.setForeground(Color.BLACK);               
                    level3.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    level3.setMaximumSize(buttonLevelDimension);
                    level3.setPreferredSize(buttonLevelDimension);
                    level3.setFont(fontButton);
                    level3.setBackground(new Color(120, 124, 126));
                    level3.setBorder(new LineBorder(new Color(85, 89, 91), 4));
                    level3.setForeground(Color.BLACK);

                    level1.addActionListener(level1Event ->{
                        mainFrame = new JFrame("runwarrior");
                        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        mainFrame.setLocationByPlatform(false);
                        mainFrame.setResizable(true);
                        
                        glp = new GameLoopPanel("Map1/map_1.txt", "Map1/desert_theme.txt", "/Map1/enemiesMap1.txt", "/Coins/CoinCoordinates_map1.txt");
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
                        
                        glp = new GameLoopPanel("Map2/map2.txt", "Map2/forest_theme.txt", "/Map2/enemiesMap2.txt", "/Coins/CoinCoordinates_map2.txt");
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
                        
                        glp = new GameLoopPanel("Map_3/map_3.txt", "Map_3/map3Theme.txt", "/Map_3/enemiesMap3.txt", "/Coins/CoinCoordinates_map3.txt");
                        glp.startGame();
                        mainFrame.add(glp);
                        mainFrame.pack();
                        mainFrame.setVisible(true);
                    });
                    JButton shopButton = new JButton("SHOP");
                    shopButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    shopButton.setMaximumSize(new Dimension(150, 40));
                    shopButton.setPreferredSize(new Dimension(150, 40));
                    shopButton.setFont(new Font("Cooper Black", Font.BOLD, 14));
                    shopButton.setBackground(new Color(70, 130, 180));
                    shopButton.setForeground(Color.WHITE);
                    shopButton.setBorder(new LineBorder(new Color(30, 90, 150), 3));

                    shopButton.addActionListener(shopEvent -> {
                        JFrame shopFrame = new JFrame("SHOP");
                        shopFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        shopFrame.setSize(600, 400);
                        shopFrame.setLocationRelativeTo(null);
                        shopFrame.setResizable(false);

                        // Inserisci la ShopView collegata a GameSaveManager e Score
                        ShopView shopView = new ShopView(new Score(GameSaveManager.getInstance()));
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

