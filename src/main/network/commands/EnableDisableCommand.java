package network.commands;

import network.Network;
import network.node.Node;

import java.util.Arrays;
import java.util.List;

public class EnableDisableCommand implements Command {
    public static final String ENABLE_COMMAND = "ENABLE";
    public static final String DISABLE_COMMAND = "DISABLE";
    public static final String CLASS_NAME = "network.commands.EnableDisableCommand";

    private static final List<String> VALID_SUB_COMMANDS = Arrays.asList("SEND", "RECEIVE");


    private String[] inputs;
    private boolean isEnable;
    private boolean isSend;
    private String nodeName;


    @Override
    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean parseInputs() {
        String command = inputs[0];
        String subCommand = inputs[1];
        if(inputs.length != 3 || !VALID_SUB_COMMANDS.contains(subCommand)){
            return false;
        }

        isEnable = command.equals(ENABLE_COMMAND);
        isSend = subCommand.equals("SEND");
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
            node.setCanSend(isEnable);
        }else{
            node.setCanReceive(isEnable);
        }
        System.out.println("Successfully " + (isEnable ? "enabled" : "disabled") + " " +  (isSend? "Send" : "Receive") + ".");
    }

}
