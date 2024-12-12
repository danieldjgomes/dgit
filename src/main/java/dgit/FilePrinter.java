package dgit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilePrinter {

    public static void writeFile(File file, String content) {
        writeFile(file,content,false);
    }

    public static void writeFile(File file, String content, boolean append) {
        try {
            FileWriter treeWriter = new FileWriter(file, append);
            treeWriter.append(content);
            treeWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cleanFile(File file) {
        writeFile(file,"",false);
    }

}
