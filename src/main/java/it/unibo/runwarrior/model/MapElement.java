package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;

/**
 * This class represents each tile of the map, with its characteristics.
 */
public final class MapElement {

    private BufferedImage image;
    private boolean collision;
    private boolean harmless;
    private boolean portal;

    /**
     * Sets the image for this map element.
     * @param im the new image.
     */
    public void setImage(final BufferedImage im) {
        this.image = im;
    }
/* modifica di spot bug
    public void setImage(BufferedImage image) {
    if (image != null) {
        // Crea una copia difensiva dell'immagine
        this.image = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        this.image.getGraphics().drawImage(image, 0, 0, null);
    } else {
        this.image = null;
    }
}
    */

    /**
     * Sets the collision property for this map element.
     * @param col true if the element should have collision, false otherwise.
     */
    public void setCollision(final boolean col) {
        this.collision = col;
    }

    /**
     * Sets the harmless property for this map element.
     * @param harm true if the element is harmless, false otherwise.
     */
    public void setHarmless(final boolean harm) {
        this.harmless = harm;
    }

    /**
     * Sets the portal property for this map element.
     * @param portal true if the element is a portal, false otherwise.
     */
    public void setPortal(final boolean portal) {
        this.portal = portal;
    }

    /**
     * Gets the image of the map element.
     *      
     * @return the element's image.
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Gets the collision status of the map element.
     * 
     * @return true if the element has collision.
     */
    public boolean getCollision() {
        return this.collision;
    }

    /**
     * Gets the harmless status of the map element.
     * 
     * @return true if the element is harmless.
     */
    public boolean getHarmless() {
        return this.harmless;
    }

    /**
     * Checks if the map element is a portal.
     * 
     * @return true if the element is a portal.
     */
    public boolean isPortal() {
        return this.portal;
    }
}
