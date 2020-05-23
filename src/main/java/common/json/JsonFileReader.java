package common.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import module.barter.model.BarterRoute;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

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
    public static <T extends JsonObject> T readFromFile(File file, Class<T> clazz) throws JsonParseException {
        try {
            return GSON.fromJson(new FileReader(file), clazz);
        } catch (Exception ex) {
            throw new JsonParseException();
        }
    }

    public static Gson getGson() {
        return GSON;
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

}
