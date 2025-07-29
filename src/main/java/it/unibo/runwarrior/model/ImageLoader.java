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
 * The class reads key-value pairs where the key is an integer block ID
 * and the value is the path to the image resource.
 * This class is final as it is not designed for extension.
 */
public final class ImageLoader {
    private final Map<Integer, BufferedImage> blockImages;

    /**
     * Constructs a new ImageLoader, initializing the internal image map.
     */
    public ImageLoader() {
        this.blockImages = new HashMap<>();
    }

    /**
     * Loads a single image and associates it with a block value.
     *
     * @param blockValue the integer value to associate with the image.
     * @param filePath   the resource path to the image file.
     * @return true if loading was successful, false otherwise.
     */
    public boolean loadImage(final int blockValue, final String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                return false;
            }
            final BufferedImage image = ImageIO.read(is);
            if (image != null) {
                this.blockImages.put(blockValue, image);
                return true;
            }
            return false;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * Loads all images specified in a given configuration file.
     * The config file should contain lines in the format 'key=value'.
     *
     * @param configFilePath the resource path to the configuration file.
     * @return true if all images were loaded without critical errors, false otherwise.
     */
    public boolean loadImagesFromConfigFile(final String configFilePath) {
        boolean allLoadedSuccessfully = true;
        try (InputStream inputStream = MapLoader.class.getClassLoader().getResourceAsStream(configFilePath)) {
            if (inputStream == null) {
                return false;
            }
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
                            allLoadedSuccessfully = false;
                        }
                    } else {
                        allLoadedSuccessfully = false;
                    }
                }
            }
        } catch (final IOException e) {
            return false;
        }
        return allLoadedSuccessfully;
    }

    /**
     * Gets the loaded image for a specific block value.
     *
     * @param blockValue the integer value of the block.
     * @return the corresponding BufferedImage, or null if not found.
     */
    public BufferedImage getBlockImage(final int blockValue) {
        final BufferedImage image = this.blockImages.get(blockValue);
        if (image == null) {
            System.err.println("Warning: No image loaded for block value: " + blockValue);
        }
        return image;
    }

    /**
     * Returns an unmodifiable map of the loaded images.
     *
     * @return the map of block values to BufferedImages.
     */
    public Map<Integer, BufferedImage> getLoadedImages() {
        return Collections.unmodifiableMap(this.blockImages);
    }
}
