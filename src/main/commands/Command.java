package commands;

import network.Network;
public interface Command {
    void setInputs(String[] inputs);
    boolean parseInputs();
    void execute(Network network) throws Exception;
    boolean doesMatchCommand(String command);
}
