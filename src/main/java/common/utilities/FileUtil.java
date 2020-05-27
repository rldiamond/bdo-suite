package common.utilities;

import com.google.gson.reflect.TypeToken;
import common.application.ModuleRegistration;
import common.json.JsonFileReader;
import common.json.ModuleData;
import common.logging.Log;
import common.settings.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class FileUtil {

    private static final Logger logger = LogManager.getLogger(FileUtil.class);

    public static void saveLogData(List<Log> logs) {
        final String path = getUserDirectory() + "/logs/log.json";

        File dataDir = new File(getUserDirectory() + "/logs/");
        if (!dataDir.exists()) {
            try {
                dataDir.mkdirs();
            } catch (Exception ex) {
                logger.error("Failed to create data directory.", ex);
            }
        }

        try (FileWriter fileWriter = new FileWriter(path)) {
            JsonFileReader.getGson().toJson(logs, fileWriter);
        } catch (Exception ex) {
            logger.error("Failed to save logs file!", ex);
        }
    }

    public static List<Log> loadLogs() {
        List<Log> loadedLogs = Collections.EMPTY_LIST;
        final String path = getUserDirectory() + "/logs/log.json";
        File settingsFile = new File(path);
        if (settingsFile.exists()) {
            try (FileReader fileReader = new FileReader(path)) {
                Type listType = new TypeToken<List<Log>>() {}.getType();
                loadedLogs = JsonFileReader.getGson().fromJson(fileReader, listType);
            } catch (Exception ex) {
                logger.error("Failed to load logs file!", ex);
            }
        }

        return loadedLogs;
    }

    public static void saveModuleData(ModuleData moduleData) {
        final String path = getUserDirectory() + "/data/" + moduleData.getModule().getTitle().toLowerCase() + "/" + moduleData.fileName() + ".json";

        File dataDir = new File(getUserDirectory() + "/data/" + moduleData.getModule().getTitle().toLowerCase() + "/");
        if (!dataDir.exists()) {
            try {
                dataDir.mkdirs();
            } catch (Exception ex) {
                logger.error("Failed to create data directory.", ex);
            }
        }

        try (FileWriter fileWriter = new FileWriter(path)) {
            JsonFileReader.getGson().toJson(moduleData, fileWriter);
        } catch (Exception ex) {
            logger.error("Failed to save settings file!", ex);
        }
    }

    public static <T extends ModuleData> T loadModuleData(Class<T> moduleData) {
        T temp = null;
        try {
            temp = moduleData.newInstance();
            final String path = getUserDirectory() + "/data/" + temp.getModule().getTitle().toLowerCase() + "/" + temp.fileName() + ".json";

            File dataFile = new File(path);
            if (dataFile.exists()) {
                try (FileReader fileReader = new FileReader(path)) {
                    temp = JsonFileReader.getGson().fromJson(fileReader, moduleData);
                } catch (Exception ex) {
                    logger.error("Failed to load settings file!", ex);
                }
            } else {
                saveModuleData(temp);
            }
        } catch (Exception ex) {
            logger.error("Failed to load module data!", ex);
        }

        return temp;

    }

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
