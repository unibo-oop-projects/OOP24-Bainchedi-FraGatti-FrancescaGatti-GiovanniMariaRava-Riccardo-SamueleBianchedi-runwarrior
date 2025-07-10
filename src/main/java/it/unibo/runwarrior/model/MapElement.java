package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;

public class MapElement {

    private int id; // Identificatore del blocco
    private String name;
    private boolean solid; // Indica se il blocco è solido (non attraversabile)+
    private boolean harmfull; // Indica se il blocco è dannoso (causa danni al giocatore)
    private BufferedImage image; // Immagine associata al blocco

    public MapElement(int id, String name, boolean solid, boolean harmfull, BufferedImage image) {
        this.id = id;
        this.solid = solid;
        this.harmfull = harmfull;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSolid() {
        return solid;
    }

    public boolean isHarmfull() {
        return harmfull;
    }

    public BufferedImage getImage() {
        return image;
    }
}

