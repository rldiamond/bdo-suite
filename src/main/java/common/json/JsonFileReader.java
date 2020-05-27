package common.json;

import com.google.gson.Gson;
import common.logging.AppLogger;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reads data from a provided JSON file into an object.
 */
public class JsonFileReader {

    private static final AppLogger logger = AppLogger.getLogger();
    private static final Gson GSON = new Gson();

    /**
     * Reads a JSON file at the provided location, and serializes it into the provided object.
     *
     * @param file  The JSON file.
     * @param clazz The object to serialize to.
     * @param <T>   Serialized object read from JSON.
     * @return A serialized object read from the provided JSON file.
     * @throws JsonParseException if an error occurs parsing the JSON file.
     */
    public static <T extends ModuleData> T readFromFile(InputStream file, Class<T> clazz) throws JsonParseException {
        try (InputStreamReader reader = new InputStreamReader(file)) {
            return getGson().fromJson(reader, clazz);
        } catch (Exception ex) {
            throw new JsonParseException("Failed to parse JSON for object " + clazz.getSimpleName());
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (Exception ex) {
                    logger.error("Failed to close file InputStream!", ex);
                }
            }
        }
    }

    /**
     * @return the instance of GSON used for parsing JSON.
     */
    public static Gson getGson() {
        return GSON;
    }

    /**
     * Convert the provided object to JSON.
     *
     * @param object the object to convert to JSON.
     * @return JSON string version of the provided object.
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

}
