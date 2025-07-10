package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BlockTypeLoader {

    private final Map<Integer, BlockType> blockTypes;

    public BlockTypeLoader() {
        this.blockTypes = new HashMap<>();
    }

    public boolean loadBlockTypesFromConfigFile(String propertiesFilePath, Map<Integer, BufferedImage> imagesMap) {

        if (imagesMap == null) {
            throw new IllegalArgumentException("La mappa delle immagini non può essere null.");
        }

        boolean allLoadedSuccessfully = true;

        try (BufferedReader br = new BufferedReader(new FileReader(propertiesFilePath))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    continue;
                }

                String[] parts = trimmedLine.split("=", 2);

                if (parts.length == 2) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String[] properties = parts[1].trim().split(",");

                        if (properties.length == 3) {
                            String name = properties[0].trim();

                            boolean isSolid = Boolean.parseBoolean(properties[1].trim());
                            boolean dealsDamage = Boolean.parseBoolean(properties[2].trim());

                            BufferedImage image = imagesMap.get(id);
                            if (image == null) {
                                System.err.println("AVVISO (Riga " + lineNumber + "): Immagine non trovata per il BlockType ID " + id + ". Il blocco sarà caricato senza immagine visiva.");
                            }

                            BlockType type = new BlockType(id, name, isSolid, dealsDamage, image);
                            blockTypes.put(id, type);
                            //System.out.println("Caricato BlockType: " + type.getName() + " (ID: " + type.getId() + ")");

                        } else {
                            System.err.println("ERRORE (Riga " + lineNumber + "): Formato proprietà non valido. Atteso 'ID=Nome,isSolid,dealsDamage'. Riga: '" + trimmedLine + "'");
                            allLoadedSuccessfully = false;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("ERRORE (Riga " + lineNumber + "): ID blocco non valido (non numerico). Riga: '" + trimmedLine + "'. Errore: " + e.getMessage());
                        allLoadedSuccessfully = false;
                    }
                } else {
                    System.err.println("ERRORE (Riga " + lineNumber + "): Formato riga non valido. Atteso 'ID=Proprietà'. Riga: '" + trimmedLine + "'");
                    allLoadedSuccessfully = false;
                }
            }
        } catch (IOException e) {
            System.err.println("ERRORE CRITICO: Errore I/O durante la lettura del file di configurazione delle proprietà dei blocchi '" + propertiesFilePath + "': " + e.getMessage());
            return false;
        }

        return allLoadedSuccessfully;
    }

    public Map<Integer, BlockType> getBlockTypes() {
        return new HashMap<>(blockTypes);
    }
}
