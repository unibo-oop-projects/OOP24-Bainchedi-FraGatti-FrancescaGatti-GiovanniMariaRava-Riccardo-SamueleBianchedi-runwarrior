package it.unibo.runwarrior.view;

import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;

public class menu {
    public static void main(String[] args){
        //creo il frame e imposto il titolo 
        final JFrame frameMenu = new JFrame(); 
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

        final JPanel pannelloSfondoMenu = new JPanel(); 
        pannelloSfondoMenu.setLayout(null);
        pannelloSfondoMenu.setOpaque(false);

        ImageIcon immagineSfondo = new ImageIcon("C:\\Users\\Utente\\Desktop\\OOP24-runwarrior\\src\\main\\resources\\Menu\\sfondoMenu.png"); 
        JLabel visualizzaSfondo = new JLabel(immagineSfondo); 
        visualizzaSfondo.setBounds(0,0,1280, 720); 
        pannelloSfondoMenu.add(visualizzaSfondo);  

        frameMenu.add(pannelloSfondoMenu);

        final JPanel pannelloTastoPlay = new JPanel(); 
        pannelloTastoPlay.setLayout(new FlowLayout(FlowLayout.CENTER));
        pannelloTastoPlay.setOpaque(false);

        final JButton playButton = new JButton("PLAY");
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){ 
                pannelloTastoPlay.remove(playButton);
                pannelloTastoPlay.add(new JButton("LIVELLO 1")); 
                pannelloTastoPlay.add(new JButton("LIVELLO 2")); 
                pannelloTastoPlay.add(new JButton("LIVELLO 3")); 

                final JButton pulsanteNegozio = new JButton("NEGOZIO"); 
                pulsanteNegozio.setBounds(1100, 20, 120, 40);  
                pannelloSfondoMenu.add(pulsanteNegozio); 
                pannelloTastoPlay.validate();
            }
            
        });
        pannelloTastoPlay.add(playButton);
        pannelloTastoPlay.setBounds(430, 300, 400, 100); 
        pannelloSfondoMenu.add(pannelloTastoPlay);
        
        frameMenu.setVisible(true);
    }
}

