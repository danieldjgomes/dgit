package dgit;

import dgit.Commands.AddCommand;
import dgit.Commands.Command;
import dgit.Commands.CommitCommand;
import dgit.Commands.InitCommand;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        Map<String, Command> commands = new HashMap<>();

        commands.put("init", new InitCommand());
        commands.put("add", new AddCommand());
        commands.put("commit", new CommitCommand());

        if (args.length == 0) {
            System.err.println("Usage: dgit <command> [<args>]");
            System.exit(1);
        }

        Command command = commands.get(args[0]);

        if (command == null) {
            System.err.println("Command not found, try: " + String.join(",\n ", commands.keySet()));
            System.exit(1);
        }
        command.execute(args);

    }
}
