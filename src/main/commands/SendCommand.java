package commands;

import network.Network;
import network.node.Node;

public class SendCommand implements Command{
    public static String SEND_COMMAND = "SEND";

    private String[] inputs;
    private String source;
    private String destination;
    private String message;
    @Override
    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean validate() {

        if(inputs.length != 4) return false;
        source = inputs[1];
        destination = inputs[2];
        message = inputs[3];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(source) || !network.isDeviceAvailable(destination)){
            throw new Exception("No node found.");
        }

        Node sourceNode = network.getDevice(source);
        Node destinationNode = network.getDevice(destination);

        if(sourceNode.hasBlacklisted(destinationNode) || destinationNode.hasBlacklisted(sourceNode)){
            throw new Exception("Message can't be sent since node is blocked.");
        }

        sourceNode.sendMessage(destinationNode, message);
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(SEND_COMMAND);
    }
}
