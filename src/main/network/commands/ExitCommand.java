package network.commands;
import network.Network;

public class ExitCommand implements Command {
    public static final String EXIT_COMMAND = "EXIT";
    public static final String CLASS_NAME = "network.commands.ExitCommand";


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

}
