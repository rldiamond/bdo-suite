package module.barter;

import com.google.gson.reflect.TypeToken;
import common.json.JsonFileReader;
import common.json.JsonParseException;
import module.barter.model.Barter;
import module.barter.model.BarterGood;
import module.barter.model.BarterLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Extension of the JSON file reader to handle reading Barter-module specific files.
 */
public class BarterJsonFileReader extends JsonFileReader {

    private static final Logger logger = LogManager.getLogger(BarterJsonFileReader.class);

    /**
     * Returns a list of BarterRoute from the supplied JSON file.
     *
     * @param file The JSON file to read.
     * @return A list of BarterRoute.
     * @throws JsonParseException If an error occurs parsing the JSON file.
     */
    public static List<Barter> readBarterRoutesFromFile(InputStream file) throws JsonParseException {
        try (InputStreamReader reader = new InputStreamReader(file)){
            Type listType = new TypeToken<List<Barter>>() {
            }.getType();
            return getGson().fromJson(reader, listType);
        } catch (Exception ex) {
            logger.error(ex);
            throw new JsonParseException();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (Exception ex) {
                    logger.error("Failed to close input stream!");
                }
            }
        }
    }

    public static final List<BarterGood> readBarterGoodsFromFile(InputStream file) throws JsonParseException {
        try (InputStreamReader reader = new InputStreamReader(file)){
            Type listType = new TypeToken<List<BarterGood>>() {
            }.getType();
            return getGson().fromJson(reader, listType);
        } catch (Exception ex) {
            logger.error(ex);
            throw new JsonParseException();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (Exception ex) {
                    logger.error("Failed to close input stream!");
                }
            }
        }
    }

    /**
     * Returns a list of BarterLevel from the supplied JSON file.
     *
     * @param file The JSON file to read.
     * @return A list of BarterLevel.
     * @throws JsonParseException If an error occurs parsing the JSON file.
     */
    public static final List<BarterLevel> readBarterLevelsFromFile(InputStream file) throws JsonParseException {
        try (InputStreamReader reader = new InputStreamReader(file)){
            Type listType = new TypeToken<List<BarterLevel>>() {
            }.getType();
            return getGson().fromJson(reader, listType);
        } catch (Exception ex) {
            logger.error(ex);
            throw new JsonParseException();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (Exception ex) {
                    logger.error("Failed to close input stream!");
                }
            }
        }
    }

}
