package dgit.builder;

import dgit.FileCompressor;
import dgit.FilePrinter;
import dgit.FileUtils;
import dgit.RefUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommitRegister {
    public static void register(String sha, String message, String author, String parentCommit, String currentBranch) {
        StringBuilder sb = new StringBuilder();
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        sb.append("tree ").append(sha).append("\n")
                .append(author).append(" ").append(timestamp).append("\n")
                .append("parent ").append(parentCommit).append("\n")
                .append(message);

        String fileSha = FileCompressor.sha1(sb.toString());

        FileUtils.createFolderIfNotExist(".dgit/objects/" + fileSha.substring(0, 2));

        File commitedFile = FileUtils.createFileIfNotExist(".dgit/objects/" + fileSha.substring(0, 2) + "/" + fileSha.substring(2));
        FilePrinter.writeFile(commitedFile, sb.toString());

        File refBranch = FileUtils.createFileIfNotExist(".dgit/refs/heads/" + currentBranch);
        FilePrinter.writeFile(refBranch, fileSha);
    }

    public static String getParentCommit() {
        return RefUtils.getHeadBranch();
    }

    public static String getCurrentBranch() {
        try{
            File head = new File(".dgit/HEAD");
            String firstLine = Files.readString(head.toPath());
            String prefix = "ref: refs/heads/";
            int index = firstLine.indexOf(prefix);
            return firstLine.substring(index + prefix.length()).trim();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
