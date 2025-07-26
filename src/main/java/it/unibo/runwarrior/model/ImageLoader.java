package it.unibo.runwarrior.model;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Utility class to load images based on a configuration file.
 * This class is fully encapsulated and protects its internal state from external modification.
 */
public final class ImageLoader {

    private static final String FROM_PATH_STRING = " from ";
    private final Map<Integer, BufferedImage> blockImages;

    /**
     * Constructs a new ImageLoader, initializing the internal image map.
     */
    public ImageLoader() {
        this.blockImages = new HashMap<>();
    }

    /**
     * Gets a defensive copy of the loaded image for a specific block value.
     *
     * @param blockValue the integer value of the block.
     * @return a safe copy of the corresponding BufferedImage, or null if not found.
     */
    public BufferedImage getBlockImage(final int blockValue) {
        final BufferedImage originalImage = this.blockImages.get(blockValue);
        if (originalImage == null) {
            System.err.println("Warning: No image loaded for block value: " + blockValue);
            return null;
        }
        
        final BufferedImage copy = new BufferedImage(
            originalImage.getWidth(),
            originalImage.getHeight(),
            originalImage.getType() != 0 ? originalImage.getType() : BufferedImage.TYPE_INT_ARGB
        );
        copy.getGraphics().drawImage(originalImage, 0, 0, null);
        return copy;
    }

    /**
     * Loads a single image, stores a defensive copy, and associates it with a block value.
     *
     * @param blockValue the integer value to associate with the image.
     * @param filePath   the resource path to the image file.
     * @return true if loading was successful, false otherwise.
     */
    public boolean loadImage(final int blockValue, final String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                System.err.println("Error: Cannot find resource file at path: " + filePath);
                return false;
            }
            final BufferedImage loadedImage = ImageIO.read(is);
            if (loadedImage != null) {
                final BufferedImage copy = new BufferedImage(
                    loadedImage.getWidth(),
                    loadedImage.getHeight(),
                    loadedImage.getType() != 0 ? loadedImage.getType() : BufferedImage.TYPE_INT_ARGB
                );
                copy.getGraphics().drawImage(loadedImage, 0, 0, null);
                
                this.blockImages.put(blockValue, copy);
                System.out.println("Image for block " + blockValue + " loaded from: " + filePath);
                return true;
            }
            System.err.println("Error: Failed to load image for block " + blockValue
                + ". File might be corrupted or in an unsupported format: " + filePath);
            return false;
        } catch (final IOException e) {
            System.err.println("I/O Error loading image for block " + blockValue
                + FROM_PATH_STRING + filePath + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads all images specified in a given configuration file using UTF-8 encoding.
     *
     * @param configFilePath the resource path to the configuration file.
     * @return true if all images were loaded without critical errors, false otherwise.
     */
    public boolean loadImagesFromConfigFile(final String configFilePath) {
        boolean allLoadedSuccessfully = true;
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFilePath)) {
            if (inputStream == null) {
                System.err.println("Error: Cannot find configuration file: " + configFilePath);
                return false;
            }
            // Specify UTF-8 encoding to prevent platform-dependent issues.
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String trimmedLine = line.trim();
                    if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                        continue;
                    }
                    final String[] parts = trimmedLine.split("=", 2);
                    if (parts.length == 2) {
                        try {
                            final int blockValue = Integer.parseInt(parts[0].trim());
                            final String imagePath = parts[1].trim();
                            if (!this.loadImage(blockValue, imagePath)) {
                                allLoadedSuccessfully = false;
                            }
                        } catch (final NumberFormatException e) {
                            System.err.println("Error: Invalid block value in config line: '"
                                + trimmedLine + "' in file: " + configFilePath);
                            allLoadedSuccessfully = false;
                        }
                    } else {
                        System.err.println("Warning: Invalid image config line format: '"
                            + trimmedLine + "' in file: " + configFilePath);
                        allLoadedSuccessfully = false;
                    }
                }
            }
        } catch (final IOException e) {
            System.err.println("I/O Error reading image config file '" + configFilePath + "': " + e.getMessage());
            return false;
        }
        return allLoadedSuccessfully;
    }

    /**
     * Returns an unmodifiable view of the map of the loaded images.
     *
     * @return a read-only map of block values to BufferedImages.
     */
    public Map<Integer, BufferedImage> getLoadedImages() {
        return Collections.unmodifiableMap(this.blockImages);
    }
}