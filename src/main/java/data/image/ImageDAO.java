package data.image;

import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ImageDAO {

    private static final ImageDAO SINGLETON = new ImageDAO();
    private static final Logger logger = LogManager.getLogger(ImageDAO.class);

    public static ImageDAO getInstance() {
        return SINGLETON;
    }

    private static final String BDD_DATABASE_URL = "https://bddatabase.net";

    private ImageDAO() {

    }

    public Image getImage(long itemId) {
        return ItemImageCache.getInstance().getFromCache(itemId);
    }

    /**
     * Parse the item image URL from the BDDatabase page.
     *
     * @param itemId The ID of the item.
     * @return URL for the image, null if an error occurs.
     */
    public URL getImageUrlForItem(long itemId) {
        String url = BDD_DATABASE_URL + "/us/item/" + String.valueOf(itemId) + "/";
        String content = null;
        URLConnection connection = null;
        Scanner scanner = null;

        try {
            connection = new URL(url).openConnection();
            scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        } catch (Exception ex) {
            logger.warn("Could not connect to the BDDatabase to parse an image URL.");
            return null;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        final String searchTerm = "class=\"item_icon\"";
        if (content.contains(searchTerm)) {

            int index = content.indexOf(searchTerm);
            String cut = content.substring(index - 500, index);
            final String imgSrc = "<img src=";
            index = cut.indexOf(imgSrc);
            String img = cut.substring(index + imgSrc.length());
            img = img.trim().replace("\"", "");
            try {
                return new URL(BDD_DATABASE_URL + img);
            } catch (Exception ex) {
                logger.warn("Could not build an appropriate URL for an image for an item with the ID: " + itemId);
                return null;
            }


        } else {
            logger.warn("Image tag not found in downloaded content!");
            return null;
        }
    }

}
