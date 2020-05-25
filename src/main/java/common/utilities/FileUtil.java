package common.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileUtil {

    private static final Logger logger = LogManager.getLogger(FileUtil.class);




    public static String getUserDirectory() {
        return System.getProperty("user.home") + File.separator + ".bdo-suite";
    }

}
