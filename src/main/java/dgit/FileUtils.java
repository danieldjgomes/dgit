package dgit;

import java.io.File;
import java.nio.file.Path;

public class FileUtils {

    public static String getType(File file) {
        return file.isDirectory() ? "tree" : "blob";
    }
}
