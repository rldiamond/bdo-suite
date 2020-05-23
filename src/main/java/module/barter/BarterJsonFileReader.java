package module.barter;

import com.google.gson.reflect.TypeToken;
import common.json.JsonFileReader;
import common.json.JsonParseException;
import module.barter.model.BarterGood;
import module.barter.model.BarterRoute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
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
    public static List<BarterRoute> readBarterRoutesFromFile(File file) throws JsonParseException {
        try {
            Type listType = new TypeToken<List<BarterRoute>>() {
            }.getType();
            return getGson().fromJson(new FileReader(file), listType);
        } catch (Exception ex) {
            throw new JsonParseException();
        }
    }

    /**
     * Returns a list of BarterGood from the supplied JSON file.
     *
     * @param file The JSON file to read,
     * @return A list of BarterGood.
     * @throws JsonParseException If an error occurs parsing the JSON file.
     */
    public static final List<BarterGood> readBarterGoodsFromFile(File file) throws JsonParseException {
        try {
            Type listType = new TypeToken<List<BarterGood>>() {
            }.getType();
            return getGson().fromJson(new FileReader(file), listType);
        } catch (Exception ex) {
            throw new JsonParseException();
        }
    }

}