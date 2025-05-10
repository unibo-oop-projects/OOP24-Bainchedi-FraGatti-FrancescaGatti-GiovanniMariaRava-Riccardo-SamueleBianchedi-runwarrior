package it.unibo.runwarrior.view;



import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
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
        JLabel visualizzaSfondo = new JLabel(immagineSfondo); //il label può essere usato per mostrare semplici stringhe di testo o immagini (come icone) all'interno di un'interfaccia grafica
        visualizzaSfondo.setBounds(0,0,1280, 720); 
        pannelloSfondoMenu.add(visualizzaSfondo);  

        frameMenu.add(pannelloSfondoMenu);
        frameMenu.setVisible(true);
    }
}

