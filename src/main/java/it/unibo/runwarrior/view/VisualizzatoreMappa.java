package it.unibo.runwarrior.view;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizzatoreMappa {

    // --- Costanti Configurabili ---
    private static final int LARGHEZZA_BLOCCO = 48; // Larghezza di un blocco in pixel
    private static final int ALTEZZA_BLOCCO = 48;   // Altezza di un blocco in pixel
    private static final int RIGHE_MAPPA = 22;      // Numero di righe nella mappa
    private static final int COLONNE_MAPPA = 375;   // Numero di colonne nella mappa

    private static final String PERCORSO_FILE_MAPPA = "/Map_1/mappa.txt"; // Relativo a src
    private static final String PERCORSO_CARTELLA_IMMAGINI = "/Map_1/"; // Relativo a src

    // Mappatura tra caratteri della mappa e nomi dei file immagine
    private static final Map<Character, String> NOMI_IMMAGINI = new HashMap<>();
    static {
        NOMI_IMMAGINI.put('0', "sky.png");
        NOMI_IMMAGINI.put('1', "grass.png");
        NOMI_IMMAGINI.put('2', "terrain.png");
        //NOMI_IMMAGINI.put('5', "fungo.png");
        // Aggiungi altri blocchi se necessario
    }
    // --- Fine Costanti ---

    private Map<Character, BufferedImage> immaginiBlocchi;
    private List<String> datiMappa;

    public VisualizzatoreMappa() {
        immaginiBlocchi = new HashMap<>();
        datiMappa = new ArrayList<>();

        caricaImmagini();
        caricaDatiMappa();

        // Creazione del pannello della mappa
        PannelloMappa pannelloMappa = new PannelloMappa();

        // Creazione del frame (la finestra principale)
        JFrame frame = new JFrame("Visualizzatore Mappa Gioco");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Chiude l'applicazione quando la finestra viene chiusa

        // Aggiungiamo il pannello a uno JScrollPane per permettere lo scrolling
        JScrollPane scrollPane = new JScrollPane(pannelloMappa);
        scrollPane.setPreferredSize(new Dimension(1024, 768)); // Dimensione iniziale della finestra scrollabile
        
        frame.add(scrollPane); // Aggiunge lo scroll pane al frame
        frame.pack(); // Adatta le dimensioni del frame al contenuto (o alla preferredSize dello scrollPane)
        frame.setLocationRelativeTo(null); // Centra la finestra sullo schermo
        frame.setVisible(true); // Rende la finestra visibile
    }

    private void caricaImmagini() {
        System.out.println("Caricamento immagini...");
        for (Map.Entry<Character, String> entry : NOMI_IMMAGINI.entrySet()) {
            char tipoBlocco = entry.getKey();
            String nomeFile = entry.getValue();
            String percorsoCompleto = PERCORSO_CARTELLA_IMMAGINI + nomeFile;
            try {
                URL urlImmagine = VisualizzatoreMappa.class.getResource(percorsoCompleto);
                if (urlImmagine == null) {
                    System.err.println("Impossibile trovare l'immagine: " + percorsoCompleto);
                    // Potresti voler caricare un'immagine di default o terminare
                    continue;
                }
                BufferedImage img = ImageIO.read(urlImmagine);
                immaginiBlocchi.put(tipoBlocco, img);
                System.out.println("Caricata: " + nomeFile);
            } catch (IOException e) {
                System.err.println("Errore durante il caricamento dell'immagine " + percorsoCompleto + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Immagini caricate: " + immaginiBlocchi.size());
    }

    private void caricaDatiMappa() {
        System.out.println("Caricamento dati mappa da: " + PERCORSO_FILE_MAPPA);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(VisualizzatoreMappa.class.getResourceAsStream(PERCORSO_FILE_MAPPA)))) {
            
            if (VisualizzatoreMappa.class.getResourceAsStream(PERCORSO_FILE_MAPPA) == null) {
                System.err.println("File mappa non trovato: " + PERCORSO_FILE_MAPPA);
                // Popola con una mappa vuota o di default per evitare NullPointerException
                for(int i=0; i < RIGHE_MAPPA; i++) {
                    StringBuilder rigaVuota = new StringBuilder();
                    for(int j=0; j < COLONNE_MAPPA; j++) {
                        rigaVuota.append('0'); // Riempi con cielo o un blocco di default
                    }
                    datiMappa.add(rigaVuota.toString());
                }
                return;
            }

            String riga;
            int numeroRiga = 0;
            while ((riga = reader.readLine()) != null && numeroRiga < RIGHE_MAPPA) {
                if (riga.length() < COLONNE_MAPPA) {
                    System.err.println("Attenzione: la riga " + (numeroRiga + 1) + " della mappa è più corta del previsto (" + riga.length() + " invece di " + COLONNE_MAPPA + "). Verrà riempita con '0'.");
                    riga = String.format("%-" + COLONNE_MAPPA + "s", riga).replace(' ', '0');
                } else if (riga.length() > COLONNE_MAPPA) {
                    System.err.println("Attenzione: la riga " + (numeroRiga + 1) + " della mappa è più lunga del previsto (" + riga.length() + " invece di " + COLONNE_MAPPA + "). Verrà troncata.");
                    riga = riga.substring(0, COLONNE_MAPPA);
                }
                datiMappa.add(riga);
                numeroRiga++;
            }
            System.out.println("Dati mappa caricati: " + datiMappa.size() + " righe.");

            if (datiMappa.isEmpty()) {
                 System.err.println("Il file mappa è vuoto o non è stato letto correttamente.");
                 // Popola con una mappa di default se vuota
                for(int i=0; i < RIGHE_MAPPA; i++) {
                    StringBuilder rigaVuota = new StringBuilder();
                    for(int j=0; j < COLONNE_MAPPA; j++) {
                        rigaVuota.append('0');
                    }
                    datiMappa.add(rigaVuota.toString());
                }
            }

        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file mappa " + PERCORSO_FILE_MAPPA + ": " + e.getMessage());
            e.printStackTrace();
            // Se c'è un errore, potresti voler creare una mappa di default
             if (datiMappa.isEmpty()) {
                for(int i=0; i < RIGHE_MAPPA; i++) {
                    StringBuilder rigaVuota = new StringBuilder();
                    for(int j=0; j < COLONNE_MAPPA; j++) {
                        rigaVuota.append('0'); // Riempi con cielo o un blocco di default
                    }
                    datiMappa.add(rigaVuota.toString());
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Impossibile trovare il file mappa: " + PERCORSO_FILE_MAPPA + ". Assicurati che sia in 'src' e che il percorso sia corretto.");
            e.printStackTrace();
            // Popola con una mappa di default
            if (datiMappa.isEmpty()) {
                for(int i=0; i < RIGHE_MAPPA; i++) {
                    StringBuilder rigaVuota = new StringBuilder();
                    for(int j=0; j < COLONNE_MAPPA; j++) {
                        rigaVuota.append('0');
                    }
                    datiMappa.add(rigaVuota.toString());
                }
            }
        }
    }

    // Classe interna per il pannello di disegno
    class PannelloMappa extends JPanel {
        
        public PannelloMappa() {
            // Imposta la dimensione preferita del pannello in modo che JScrollPane sappia quanto spazio serve
            setPreferredSize(new Dimension(COLONNE_MAPPA * LARGHEZZA_BLOCCO, RIGHE_MAPPA * ALTEZZA_BLOCCO));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Pulisce il pannello

            if (datiMappa.isEmpty() || immaginiBlocchi.isEmpty()) {
                g.setColor(Color.RED);
                g.drawString("Dati mappa o immagini non caricati correttamente!", 20, 20);
                return;
            }

            // Disegna la mappa
            for (int riga = 0; riga < datiMappa.size(); riga++) {
                String lineaMappa = datiMappa.get(riga);
                for (int colonna = 0; colonna < lineaMappa.length(); colonna++) {
                    char tipoBlocco = lineaMappa.charAt(colonna);
                    BufferedImage img = immaginiBlocchi.get(tipoBlocco);

                    int x = colonna * LARGHEZZA_BLOCCO;
                    int y = riga * ALTEZZA_BLOCCO;

                    if (img != null) {
                        g.drawImage(img, x, y, LARGHEZZA_BLOCCO, ALTEZZA_BLOCCO, null);
                    } else {
                        // Se un'immagine non è definita per un tipo di blocco, disegna un quadrato colorato
                        g.setColor(Color.MAGENTA); // Un colore per indicare un blocco mancante/sconosciuto
                        g.fillRect(x, y, LARGHEZZA_BLOCCO, ALTEZZA_BLOCCO);
                        g.setColor(Color.BLACK);
                        g.drawString(String.valueOf(tipoBlocco), x + LARGHEZZA_BLOCCO / 2 - 5, y + ALTEZZA_BLOCCO / 2 + 5);
                    }
                }
            }
        }
    }

    // Metodo main per avviare l'applicazione
    public static void main(String[] args) {
        // Esegue la creazione della GUI nel thread di dispatch degli eventi di Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VisualizzatoreMappa();
            }
        });
    }
}