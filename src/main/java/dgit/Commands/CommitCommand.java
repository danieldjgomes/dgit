package dgit.Commands;

import dgit.FileUtils;
import dgit.IndexEntry;
import dgit.Node;
import dgit.builder.CommitRegister;
import dgit.builder.IndexBuilder;
import dgit.builder.TreeRegister;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static dgit.FileUtils.buildNodeTreeFromIndexEntry;
import static java.util.stream.Collectors.groupingBy;

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

            List<Node> topNodes = FileUtils.buildNodeTreeFromIndexEntry(list);

            String topNodesSHA = topNodes.stream().map(this::processNode).collect(Collectors.joining("\n"));
            String sha = TreeRegister.registry(topNodesSHA);

            String parentCommit = CommitRegister.getParentCommit();
            String currentBranch = CommitRegister.getCurrentBranch();
            CommitRegister.register(sha, message, author, parentCommit, currentBranch);
            IndexBuilder.cleanIndex();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private String processNode(Node node) {
        if (node.getNodes().isEmpty()) {
            return String.join(" ", node.getNodeType(), node.getSha(), node.getId());
        }
        String sha = node.getNodes().stream()
                .map(this::processNode)
                .collect(Collectors.joining("\n"));
        return String.join(" ", node.getNodeType(), TreeRegister.registry(sha), node.getId());
    }

}


