package it.unibo.runwarrior.model;
import javax.swing.*;
//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {
    public GameTimer(){
        final JFrame frameCronometro = new JFrame(); 
        frameCronometro.setTitle("cronometro di gioco");
        frameCronometro.setBounds(100, 100, 450, 300);
        frameCronometro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCronometro.getContentPane().setLayout(null);

        JLabel labelNumeriCronometro = new JLabel("0.0");
        final Chronometer c = new Chronometer(labelNumeriCronometro); 

        labelNumeriCronometro.setHorizontalAlignment(SwingConstants.CENTER);
        labelNumeriCronometro.setBounds(99, 66, 246, 14);
        frameCronometro.getContentPane().add(labelNumeriCronometro);

        final JButton avvia = new JButton("AVVIA"); 
        avvia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
                c.Avvia();
            }
        });
        avvia.setBounds(256, 115, 89, 76); 
        frameCronometro.getContentPane().add(avvia);

        final JButton ferma = new JButton("FERMA"); 
        ferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e2) {
                c.Ferma();
            }
        });
        ferma.setBounds(99, 115, 89, 76); 
        frameCronometro.getContentPane().add(ferma);  
        frameCronometro.setVisible(true);
    }
}

