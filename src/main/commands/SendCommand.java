package commands;

import network.Network;
import network.node.Node;

public class SendCommand implements Command{
    public static String SEND_COMMAND = "SEND";

    private String[] inputs;
    private String from;
    private String to;
    private String message;
    @Override
    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean parseInputs() {

        if(inputs.length != 4) return false;
        from = inputs[1];
        to = inputs[2];
        message = inputs[3];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(from) || !network.isDeviceAvailable(to)){
            throw new Exception("No node found.");
        }

        Node fromNode = network.getDevice(from);
        Node toNode = network.getDevice(to);

        fromNode.sendMessage(toNode, message);
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(SEND_COMMAND);
    }
}
