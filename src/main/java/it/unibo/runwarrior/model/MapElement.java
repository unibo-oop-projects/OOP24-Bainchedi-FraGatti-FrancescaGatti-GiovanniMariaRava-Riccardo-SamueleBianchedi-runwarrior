package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
     *
     * @param image the new image.
     */
    public void setImage(final BufferedImage image) {
        if (image != null) {
            this.image = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            this.image.getGraphics().drawImage(image, 0, 0, null);
        } else {
            this.image = null;
        }
    }

    /**
     * Sets the collision property for this map element.
     *
     * @param col true if the element should have collision, false otherwise.
     */
    public void setCollision(final boolean col) {
        this.collision = col;
    }

    /**
     * Sets the harmless property for this map element.
     *
     * @param harm true if the element is harmless, false otherwise.
     */
    public void setHarmless(final boolean harm) {
        this.harmless = harm;
    }

    /**
     * Sets the portal property for this map element.
     *
     * @param portal true if the element is a portal, false otherwise.
     */
    public void setPortal(final boolean portal) {
        this.portal = portal;
    }

    /**
     * Gets the image of the map element.
     * A SuppressFBWarnings annotation is used to ignore the EI_EXPOSE_REP error.
     * Creating a defensive copy of every get() call leads to significant lag.
     *
     * @return the element's original image for performance reasons.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "Performance-critical code: defensive copy in game loop causes lag."
    )
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * Gets the collision status of the map element.
     *
     * @return true if the element has collision.
     */
    public boolean isCollision() {
        return this.collision;
    }

    /**
     * Gets the harmless status of the map element.
     *
     * @return true if the element is harmless.
     */
    public boolean isHarmless() {
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
