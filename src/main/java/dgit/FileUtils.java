package dgit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static String getType(File file) {
        return file.isDirectory() ? "tree" : "blob";
    }

    public static File createFileIfNotExist(String path) {
        try {
            File index = new File(path);
            if (!index.exists()) {
                index.createNewFile();
            }
            return index;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File createFolderIfNotExist(String path) {
        File index = new File(path);
        if (!index.exists()) {
            index.mkdir();
        }
        return index;
    }

    public static List<Node> buildNodeTreeFromIndexEntry(List<IndexEntry> list) {
        List<Node> topNodes = new ArrayList<>();

        for (IndexEntry entry : list) {
            Path path = entry.getPath();
            Node current = null;

            for (int p = 0; p < path.getNameCount(); p++) {
                String part = path.getName(p).toString();

                List<Node> currentLevelNodes = (p == 0) ? topNodes : current.nodes;

                Node existingNode = currentLevelNodes.stream()
                        .filter(node -> node.id.equals(part))
                        .findFirst()
                        .orElse(null);

                if (existingNode == null) {
                    Node newNode = new Node();
                    newNode.id = part;
                    newNode.nodeType = (p == path.getNameCount() - 1) ? "blob" : "tree";
                    newNode.nodes = new ArrayList<>();
                    if (newNode.nodeType.equals("blob")) {
                        newNode.sha = entry.getSha();
                    }
                    currentLevelNodes.add(newNode);
                    current = newNode;
                } else {
                    current = existingNode;
                }
            }
        }
        return topNodes;
    }

}
