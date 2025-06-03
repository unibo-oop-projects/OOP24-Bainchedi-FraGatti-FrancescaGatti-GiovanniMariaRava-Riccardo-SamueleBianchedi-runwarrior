package it.unibo.runwarrior.view;

import javax.imageio.ImageIO;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu extends JPanel{
        //creo il frame e imposto il titolo 
        final JFrame frameMenu = new JFrame();
        BufferedImage immagineSfondo = null;
        public Menu(){
            frameMenu.setTitle("RUN WARRIOR");
            frameMenu.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frameMenu.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e){
                    int n = JOptionPane.showConfirmDialog(frameMenu, "do you really want to quit?", "Quitting...", JOptionPane.YES_NO_OPTION); 
                    if(n==JOptionPane.YES_OPTION){
                        System.exit(0);
                    }
                }
            });
            frameMenu.setSize(1280, 720); 
            frameMenu.setLocationRelativeTo(null); //per centrare la finestra
 
            //ImageIcon immagineSfondo = new ImageIcon("C:\Users\Utente\Desktop\OOP24-runwarrior\src\main\resources\Menu\sfondoMenu.png"); 
            try{
                immagineSfondo = ImageIO.read(getClass().getResourceAsStream("/Menu/sfondoMenu.png"));
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

            final JPanel pannelloTastoPlay = new JPanel(); 
            pannelloTastoPlay.setLayout(new FlowLayout(FlowLayout.CENTER));
            pannelloTastoPlay.setOpaque(false);
            pannelloTastoPlay.setBounds(430,300,400,100);

            final JButton playButton = new JButton("PLAY");
            playButton.addActionListener(new ActionListener() {
                private JFrame mainFrame;
                private GameLoopPanel glp;
                public void actionPerformed(ActionEvent e){ 
                    pannelloTastoPlay.remove(playButton);
                    mainFrame = new JFrame("runwarrior");
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainFrame.setLocationByPlatform(false);
                    mainFrame.setResizable(true);

                    glp = new GameLoopPanel();
                    glp.startGame();
                    
                    mainFrame.add(glp);
                    mainFrame.pack();
                    mainFrame.setVisible(true);
                    //pannelloTastoPlay.add(new JButton("LIVELLO 1")); 
                    //pannelloTastoPlay.add(new JButton("LIVELLO 2")); 
                    //pannelloTastoPlay.add(new JButton("LIVELLO 3")); 

                    //final JButton pulsanteNegozio = new JButton("NEGOZIO"); 
                    //pulsanteNegozio.setBounds(1100, 20, 120, 40);  
                    //pannelloSfondoMenu.add(pulsanteNegozio); 
                    pannelloTastoPlay.validate();
                }
            });
            pannelloTastoPlay.add(playButton); 
            pannelloSfondoMenu.add(pannelloTastoPlay);
            
            frameMenu.setContentPane(pannelloSfondoMenu);
            frameMenu.setVisible(true);
        }
}

