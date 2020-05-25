package common.json;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Reads data from a provided JSON file into an object.
 */
public class JsonFileReader {

    private static final Gson GSON = new Gson();

    /**
     * Reads a JSON file at the provided location, and serializes it into the provided object.
     * @param file The JSON file.
     * @param clazz The object to serialize to.
     * @param <T> Serialized object read from JSON.
     * @return A serialized object read from the provided JSON file.
     * @throws JsonParseException if an error occurs parsing the JSON file.
     */
    public static <T extends JsonObject> T readFromFile(InputStream file, Class<T> clazz) throws JsonParseException {
        try (InputStreamReader reader = new InputStreamReader(file)){
            return getGson().fromJson(reader, clazz);
        } catch (Exception ex) {
            throw new JsonParseException();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    public static Gson getGson() {
        return GSON;
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

}
