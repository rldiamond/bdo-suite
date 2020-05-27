package data.image;

import common.utilities.FileUtil;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Manages a local cache of images.
 */
public class ItemImageCache {

    private static final Logger logger = LogManager.getLogger(ItemImageCache.class);
    private static final ItemImageCache SINGLETON = new ItemImageCache();

    public static ItemImageCache getInstance() {
        return SINGLETON;
    }

    private void cacheImage(URL url, long itemId) {
        try {
            FileUtils.copyURLToFile(url, createFileFromId(itemId));
        } catch (IOException ex) {
            logger.warn("Failed to download image from: " + url, ex);
        }
    }

    public Image getFromCache(long itemId) {
        //see if file exists in local cache
        if (!imageExists(itemId)) {
            URL imgUrl = ImageDAO.getInstance().getImageUrlForItem(itemId);
            cacheImage(imgUrl, itemId);
        }

        return getImageFromUserDir(itemId);
    }

    private boolean imageExists(long itemId) {
        return createFileFromId(itemId).exists();
    }

    private Image getImageFromUserDir(long itemId) {
        try {
            BufferedImage image = ImageIO.read(createFileFromId(itemId));
            return SwingFXUtils.toFXImage(image, null);
        } catch (IOException ex) {
            logger.warn("Failed to read image for item: " + itemId);
        }
        return null;
    }

    private File createFileFromId(long itemId) {
        return new File(getCacheDirectory() + itemId + ".png");
    }

    private String getCacheDirectory() {
        return FileUtil.getUserDirectory() + "/img/ ";
    }

}
