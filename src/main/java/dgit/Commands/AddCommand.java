package dgit.Commands;

import dgit.FileUtils;

import java.io.File;
import java.util.Objects;

import static dgit.builder.BlobRegister.registryBlob;
import static dgit.builder.IndexBuilder.registryOnIndex;


public class AddCommand implements Command {
    @Override
    public void execute(String[] args) {
        File dgit = new File(".dgit");
        if (!dgit.exists()) {
            System.err.println("Not existing DGit project");
            System.exit(1);
        }
        File input = new File(args[0]);

        if (input.isFile()) {
            processFile(input);
            return;
        }
        checkFilesOnPath(input);
    }

    public void checkFilesOnPath(final File folder) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                if (fileEntry.getName().equals(".dgit")) {
                    continue;
                }
                checkFilesOnPath(fileEntry);
            } else {
                processFile(fileEntry);
            }
        }
    }

    private void processFile(File fileEntry) {
        FileUtils.createFileIfNotExist(".dgit/index");

        registryOnIndex(fileEntry);
        registryBlob(fileEntry);
        System.out.println("new File: " + fileEntry.getName());
    }

}
