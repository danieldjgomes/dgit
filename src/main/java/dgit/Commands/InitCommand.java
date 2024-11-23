package dgit.Commands;

import dgit.FilePrinter;
import dgit.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class InitCommand implements Command {
    @Override
    public void execute(String[] args) {
        File file = new File(".dgit");
        if(file.exists()){
            System.err.println("Existing DGit project");
            System.exit(1);
        }
        file.mkdirs();

        File head = FileUtils.createFileIfNotExist(".dgit/HEAD");
        FilePrinter.writeFile(head,"ref: refs/heads/master");

        FileUtils.createFolderIfNotExist(".dgit/config");
        FileUtils.createFolderIfNotExist(".dgit/objects");
        FileUtils.createFolderIfNotExist(".dgit/objects/info");
        FileUtils.createFolderIfNotExist(".dgit/objects/pack");

        FileUtils.createFolderIfNotExist(".dgit/refs");
        FileUtils.createFolderIfNotExist(".dgit/refs/heads");
        FileUtils.createFolderIfNotExist(".dgit/refs/tags");
    }
}
