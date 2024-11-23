package dgit.builder;

import dgit.FileCompressor;
import dgit.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class IndexBuilder {

    public static void registryOnIndex(File file) {
        File index = new File(".dgit/index");
        if (!index.exists()) {
            try {
                index.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            String sha1 = FileCompressor.sha1(file);
            File subIndex = new File(".dgit/index");
            FileWriter fileWriter = new FileWriter(subIndex, true);
            fileWriter.write(FileUtils.getType(file) + " " + sha1 + " " + file.getPath() + "\n");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
