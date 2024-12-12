package dgit;

import java.util.List;

public class Node {
    String id;
    String nodeType;

    public String getId() {
        return id;
    }

    public String getNodeType() {
        return nodeType;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public String getSha() {
        return sha;
    }

    List<Node> nodes;
    String sha;
}