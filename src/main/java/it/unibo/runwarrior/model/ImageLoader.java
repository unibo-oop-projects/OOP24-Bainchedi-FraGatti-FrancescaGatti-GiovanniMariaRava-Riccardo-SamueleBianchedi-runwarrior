package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {
    
    private Map<Integer, BufferedImage> blockImages;

    public ImageLoader() {
        blockImages = new HashMap<>();
    }

    public BufferedImage getBlockImage(int blockValue) {
        BufferedImage image = blockImages.get(blockValue);
        if (image == null) {
            System.err.println("Avviso: Nessuna immagine caricata per il valore di blocco: " + blockValue);
        }
        return image;
    }

    public boolean loadImage(int blockValue, String filePath) {
        
        try {

            //File imageFile = new File(filePath);

            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(filePath));

            if (image != null) {
                blockImages.put(blockValue, image);
                System.out.println("Immagine per blocco " + blockValue + " caricata da: " + filePath);
                return true;
            } else {
                System.err.println("Errore: Impossibile caricare l'immagine per il blocco " + blockValue +
                                   ". File letto come NULL. Controlla il formato o il contenuto del file: " + filePath);
                return false;
            }
        } catch (IOException e) {
            System.err.println("Errore I/O durante il caricamento dell'immagine per blocco " + blockValue +
                               " da " + filePath + ": " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Si è verificato un errore inatteso durante il caricamento dell'immagine per blocco " + blockValue +
                               " da " + filePath + ": " + e.getMessage());
            return false;
        }
    }

    public boolean loadImagesFromConfigFile(String configFilePath) {
        boolean allLoadedSuccessfully = true;
        InputStream inputStream = MapLoader.class.getClassLoader().getResourceAsStream(configFilePath);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim();
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue;
                }

                String[] parts = trimmedLine.split("=", 2);

                if (parts.length == 2) {
                    try {
                        int blockValue = Integer.parseInt(parts[0].trim());
                        String imagePath = parts[1].trim();

                        if (!loadImage(blockValue, imagePath)) {
                            System.err.println("Errore: Fallito il caricamento dell'immagine specificata nel config per il blocco " + blockValue + " da " + imagePath);
                            allLoadedSuccessfully = false; 
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Errore: Valore di blocco non valido (non numerico) nella riga di configurazione: '" + trimmedLine + "' nel file: " + configFilePath);
                        allLoadedSuccessfully = false;
                    }
                } else {
                    System.err.println("Avviso: Riga di configurazione immagine non valida, formato atteso 'numero=percorso': '" + trimmedLine + "' nel file: " + configFilePath);
                    allLoadedSuccessfully = false;
                }
            }
        } catch (IOException e) {
            System.err.println("Errore I/O durante la lettura del file di configurazione immagini '" + configFilePath + "': " + e.getMessage());
            return false;
        }

        return allLoadedSuccessfully;
    }

    public Map<Integer, BufferedImage> getLoadedImages() {
        return blockImages;
    }
}
