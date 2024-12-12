package dgit.Commands;

import dgit.builder.HeadService;

import java.io.File;

public class CheckoutCommand implements Command {
    @Override
    public void execute(String[] args) {
        String branchName = args[0];
        File file = new File(".dgit/refs/heads/" + branchName);

        if (!file.exists()) {
            System.err.print("error: branch '" + branchName + "' not found.\n");
        }

        HeadService.changeHead(branchName);

    }
}
