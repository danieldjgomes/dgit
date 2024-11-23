package dgit.builder;

import dgit.FileCompressor;
import dgit.FilePrinter;
import dgit.FileUtils;

import java.io.File;

public class IndexBuilder {

    public static void registryOnIndex(File file) {
        String sha1 = FileCompressor.sha1(file);
        File subIndex = new File(".dgit/index");
        String content = FileUtils.getType(file) + " " + sha1 + " " + file.getPath();
        FilePrinter.writeFile(subIndex, content + "\n", true);
    }

    public static void cleanIndex(){
        FilePrinter.cleanFile(new File(".dgit/index"));
    }

}
