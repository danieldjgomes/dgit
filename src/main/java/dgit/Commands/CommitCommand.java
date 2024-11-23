package dgit.Commands;

import dgit.FileUtils;
import dgit.IndexEntry;
import dgit.builder.CommitRegister;
import dgit.builder.IndexBuilder;
import dgit.builder.TreeRegister;

import java.io.File;
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

        String message = null;
        String author = "Who knows? ¯\\_(ツ)_/¯";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-m":
                case "--message":
                    if (i + 1 < args.length) {
                        message = args[++i];
                    } else {
                        System.err.println("Error: Missing value for --message");
                        return;
                    }
                    break;
                case "-a":
                case "--author":
                    if (i + 1 < args.length) {
                        author = args[++i];
                    } else {
                        return;
                    }
                    break;
                default:
                    break;
            }
        }

        if (message == null) {
            System.err.println("Error: --message is required");
            return;
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

            String parentCommit = CommitRegister.getParentCommit();
            String currentBranch = CommitRegister.getCurrentBranch();
            CommitRegister.register(sha, message, author, parentCommit, currentBranch);
            IndexBuilder.cleanIndex();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
