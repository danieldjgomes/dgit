package dgit.Commands;

import dgit.FileUtils;
import dgit.IndexEntry;
import dgit.builder.CommitRegister;
import dgit.builder.TreeRegister;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CommitCommand implements Command{
    @Override
    public void execute(String[] args) {
        File index = new File(".dgit/index");
        if(!index.exists()){
            System.err.println("Not existing DGit project");
            System.exit(1);
        }

        try {
            List<IndexEntry> list = Files.readAllLines(index.toPath()).stream().map(IndexEntry::new).toList();
            String sha = null;

            for (IndexEntry indexEntry : list) {
                for (int i = indexEntry.getPath().getNameCount(); i > 0; i--) {
                    File file = new File(indexEntry.getPath().subpath(0, i).toString());
                    sha = TreeRegister.registry(FileUtils.getType(file), file.getName(),
                            sha == null ? indexEntry.getSha() : sha);
                }
            }


            CommitRegister.register(sha, args[1]);





        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
