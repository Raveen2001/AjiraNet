package commands;

import network.Network;
import network.node.Node;

import java.util.Arrays;
import java.util.List;

public class DisableCommand implements Command {
    public static final String DISABLE_COMMAND = "DISABLE";
    private static final List<String> VALID_DISABLE_COMMANDS = Arrays.asList("SEND", "RECEIVE");

    private String[] inputs;
    private boolean isSend;
    private String nodeName;

    @Override
    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean parseInputs() {
        String command = inputs[1];
        if(inputs.length != 3 || !VALID_DISABLE_COMMANDS.contains(command)){
            return false;
        }

        isSend = command.equals("SEND");
        nodeName = inputs[2];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(nodeName)){
            throw new Exception("No node found");
        }

        Node node = network.getDevice(nodeName);
        if(isSend) {
            node.disableSend();
        }else{
            node.disableReceive();
        }

        System.out.println("Successfully disabled " + (isSend? "Send" : "Receive") + ".");
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(DISABLE_COMMAND);
    }
}
