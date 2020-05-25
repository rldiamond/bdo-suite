package module.gardening;

import com.google.gson.reflect.TypeToken;
import common.json.JsonFileReader;
import common.json.JsonParseException;
import module.gardening.model.Crop;
import module.gardening.model.Fence;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class GardeningJsonFileReader extends JsonFileReader {

    public static List<Crop> readCropsFromFile(File file) throws JsonParseException {
        try {
            Type listType = new TypeToken<List<Crop>>() {
            }.getType();
            return getGson().fromJson(new FileReader(file), listType);
        } catch (Exception ex) {
            throw new JsonParseException();
        }
    }

    public static List<Fence> readFencesFromFile(File file) throws JsonParseException {
        try {
            Type listType = new TypeToken<List<Fence>>() {
            }.getType();
            return getGson().fromJson(new FileReader(file), listType);
        } catch (Exception ex) {
            throw new JsonParseException();
        }
    }

}
