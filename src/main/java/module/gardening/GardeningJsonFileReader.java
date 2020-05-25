package module.gardening;

import com.google.gson.reflect.TypeToken;
import common.json.JsonFileReader;
import common.json.JsonParseException;
import module.gardening.model.Crop;
import module.gardening.model.Fence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class GardeningJsonFileReader extends JsonFileReader {
    private static final Logger logger = LogManager.getLogger(GardeningJsonFileReader.class);

    public static List<Crop> readCropsFromFile(InputStream file) throws JsonParseException {
        try (InputStreamReader reader = new InputStreamReader(file)){
            Type listType = new TypeToken<List<Crop>>() {
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

    public static List<Fence> readFencesFromFile(InputStream file) throws JsonParseException {
        try (InputStreamReader reader = new InputStreamReader(file)) {
            Type listType = new TypeToken<List<Fence>>() {
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
