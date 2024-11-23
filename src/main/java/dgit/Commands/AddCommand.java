package dgit.Commands;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static dgit.builder.BlobRegister.registryBlob;
import static dgit.builder.IndexBuilder.registryOnIndex;


public class AddCommand implements Command {
    @Override
    public void execute(String[] args) {
        File dgit = new File(".dgit");
        if(!dgit.exists()){
            System.err.println("Not existing DGit project");
            System.exit(1);
        }
        File input = new File(args[1]);

        if(input.isFile()){
            try {
                processFile(input);
                return;
            } catch (NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        checkFilesOnPath(input);

    }
    public void checkFilesOnPath(final File folder) {
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                if(fileEntry.getName().equals(".dgit")){
                    continue;
                }
                checkFilesOnPath(fileEntry);
            } else {
                try {
                    processFile(fileEntry);
                } catch (NoSuchAlgorithmException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void processFile(File fileEntry) throws NoSuchAlgorithmException, IOException {
        File index = new File(".dgit/index");
        if(!index.exists()){
            index.createNewFile();
        }
        registryOnIndex(fileEntry);
        registryBlob(fileEntry);

        System.out.println("new File: " + fileEntry.getName());
    }

}
