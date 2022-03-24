package commands;
import network.Network;

public class ExitCommand implements Command {
    public static final String EXIT_COMMAND = "EXIT";

    @Override
    public boolean parseInputs() {
        return true;
    }

    @Override
    public void setInputs(String[] inputs){}

    @Override
    public void execute(Network network) throws Exception {
        System.exit(0);
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(EXIT_COMMAND);
    }
}
