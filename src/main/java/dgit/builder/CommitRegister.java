package dgit.builder;

import dgit.FileCompressor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class CommitRegister
{
    public static void register(String sha, String message){

        StringBuilder sb = new StringBuilder();
        sb.append("tree " + sha)
            .append("\n")
                .append("author me!" + new Date().getTime())
                    .append("\n")
                .append(message);

        String fileSha = FileCompressor.sha1(sb.toString());

        File folder = new File(".dgit/objects/" + fileSha.substring(0, 2));
        if(!folder.exists()){
            folder.mkdir();
        }

        File commitFile = new File(".dgit/objects/" + fileSha.substring(0, 2) + "/" + fileSha.substring(2));
        try {
            commitFile.createNewFile();
            FileWriter treeWriter = new FileWriter(commitFile);
            treeWriter.write(sb.toString());
            treeWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try{
        File refMaster = new File(".dgit/refs/heads/master");
        if(!refMaster.exists()){
            refMaster.createNewFile();
        }
        FileWriter treeWriter = new FileWriter(refMaster);
        treeWriter.write(fileSha);
        treeWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
