package common.utilities;

import common.application.ModuleRegistration;
import common.json.JsonFileReader;
import common.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtil {

    private static final Logger logger = LogManager.getLogger(FileUtil.class);

    public static void saveSettings(Settings settings) {
        final String path = getUserDirectory() + "/settings/" + settings.getModule().getTitle().toLowerCase() + ".json";

        // check if the settings directory exists
        File settingsDir = new File(getUserDirectory() + "/settings/");
        if (!settingsDir.exists()) {
            try {
                settingsDir.mkdirs();
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        try (FileWriter fileWriter = new FileWriter(path)) {
            JsonFileReader.getGson().toJson(settings, fileWriter);
        } catch (Exception ex) {
            logger.error("Failed to save settings file!", ex);
        }
    }

    public static <T extends Settings> T loadSettings(Class<T> settings, ModuleRegistration registration) {
        final String path = getUserDirectory() + "/settings/" + registration.getTitle().toLowerCase() + ".json";
        T loadedSettings = null;
        File settingsFile = new File(path);
        if (settingsFile.exists()) {
            try (FileReader fileReader = new FileReader(path)) {
                loadedSettings = JsonFileReader.getGson().fromJson(fileReader, settings);
            } catch (Exception ex) {
                logger.error("Failed to load settings file!", ex);
            }
        } else {
            try {
                loadedSettings = settings.newInstance();
                saveSettings(loadedSettings);
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        return loadedSettings;
    }

    public static String getUserDirectory() {
        return System.getProperty("user.home") + File.separator + ".bdo-suite";
    }

}
