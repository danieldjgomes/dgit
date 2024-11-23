package dgit.builder;

import dgit.FileCompressor;
import dgit.FilePrinter;
import dgit.FileUtils;

import java.io.File;

public class BlobRegister {

    public static void registryBlob(File file) {
        String sha1 = FileCompressor.sha1(file);
        String deflatedContent = FileCompressor.deflateFile(file);

        FileUtils.createFolderIfNotExist(".dgit/objects/" + sha1.substring(0, 2));
        File deflatedFile = FileUtils.createFileIfNotExist(".dgit/objects/" + sha1.substring(0, 2) + "/" + sha1.substring(2));
        FilePrinter.writeFile(deflatedFile, deflatedContent);
    }

}
