package dgit.builder;

import dgit.FileCompressor;
import dgit.IndexEntry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TreeRegister
{

    public static String registry(String type, String name, String sha) {
        StringBuilder sb = new StringBuilder();
        sb.append(type)
                .append(" ")
                .append(name)
                .append(" ")
                .append(sha);
        String fileSha = FileCompressor.sha1(sb.toString());
        File folder = new File(".dgit/objects/" + fileSha.substring(0, 2));
        if(!folder.exists()){
            folder.mkdir();
        }
        try {
            File tree = new File(".dgit/objects/" + fileSha.substring(0, 2) + "/" + fileSha.substring(2));
            tree.createNewFile();
            FileWriter treeWriter = new FileWriter(tree);
            treeWriter.write(sb.toString());
            treeWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileSha;
    }
}
