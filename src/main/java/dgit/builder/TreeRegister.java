package dgit.builder;

import dgit.FileCompressor;
import dgit.FilePrinter;
import dgit.FileUtils;

import java.io.File;

public class TreeRegister {

    public static String registry(String type, String name, String sha) {
        String fileContent = String.join(" ", type, name, sha);
        String fileSha = FileCompressor.sha1(fileContent);

        FileUtils.createFolderIfNotExist(".dgit/objects/" + fileSha.substring(0, 2));
        File tree = FileUtils.createFileIfNotExist(".dgit/objects/" + fileSha.substring(0, 2) + "/" + fileSha.substring(2));
        FilePrinter.writeFile(tree, fileContent);
        return fileSha;
    }
}
