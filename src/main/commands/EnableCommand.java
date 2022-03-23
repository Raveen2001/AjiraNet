package commands;

import network.Network;
import network.node.Node;

import java.util.Arrays;
import java.util.List;

public class EnableCommand implements Command {
    public static final String ENABLE_COMMAND = "ENABLE";
    private static final List<String> VALID_ENABLE_COMMANDS = Arrays.asList("SEND", "RECEIVE");

    private String[] inputs;
    private boolean isSend;
    private String nodeName;
    @Override
    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean validate() {
        String command = inputs[1];
        if(inputs.length != 3 || !VALID_ENABLE_COMMANDS.contains(command)){
            return false;
        }

        if(command.equals("SEND")) isSend = true;
        else isSend = false;
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
            node.setCanSend(true);
        }else{
            node.setCanReceive(true);
        }
//        System.out.println(node.canSend);
//        System.out.println(node.canReceive);
        System.out.println("Successfully enabled " + (isSend? "Send" : "Receive") + ".");
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(ENABLE_COMMAND);
    }
}
