package common.utilities;

import common.json.JsonFileReader;
import common.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;

public class FileUtil {

    private static final Logger logger = LogManager.getLogger(FileUtil.class);

    public static void saveSettings(Settings settings) {
        final String path = getUserDirectory() + "/settings/" + settings.getModule().getTitle().toLowerCase() + ".json";

        try (FileWriter fileWriter = new FileWriter(path)) {
            JsonFileReader.getGson().toJson(settings, fileWriter);
        } catch (Exception ex) {
            logger.error("Failed to save settings file!", ex);
        }

    }

    public static String getUserDirectory() {
        return System.getProperty("user.home") + File.separator + ".bdo-suite";
    }

}
