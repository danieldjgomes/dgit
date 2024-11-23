package dgit.Commands;

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
        File head = new File(".dgit/HEAD");
        try {
            Files.write(head.toPath(), "ref: refs/heads/master".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new File(".dgit/config").mkdirs();
        new File(".dgit/objects").mkdirs();
            new File(".dgit/objects/info").mkdirs();
            new File(".dgit/objects/pack").mkdirs();

        new File(".dgit/refs").mkdirs();
        new File(".dgit/refs/heads").mkdirs();
        new File(".dgit/refs/tags").mkdirs();

    }
}
