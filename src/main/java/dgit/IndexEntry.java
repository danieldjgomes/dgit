package dgit;

import java.io.File;
import java.nio.file.Path;

public class IndexEntry {

    public String getSha() {
        return sha;
    }

    public Path getPath() {
        return path;
    }

    private String type;
    private String sha;
    private Path path;
    private String creationDate;

    public IndexEntry(String fileLine) {
        String[] split = fileLine.split(" ");
        type = split[0];
        sha = split[1];
        creationDate = split[2];
        path = new File(split[3]).toPath();
    }
}
