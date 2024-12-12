package dgit.Commands;

import java.io.File;

public class StatusCommand implements Command {
    @Override
    public void execute(String[] args) {

        File index = new File(".dgit/index");
        if (!index.exists()) {
            System.err.println("\u001B[31m" + "Not existing DGit project");
            System.exit(1);
        }
    }
}
