package network.commands;

import network.Network;
import network.node.Node;
import network.utils.Path;
import network.utils.PathFinder;

public class SendCommand implements Command{
    public static String SEND_COMMAND = "SEND";
    public static final String CLASS_NAME = "network.commands.SendCommand";


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

        if(fromNode.hasBlacklisted(toNode.name) || toNode.hasBlacklisted(fromNode.name)){
            throw new Exception("Message can't be sent since node is blocked.");
        }

        PathFinder pathFinder = new PathFinder(fromNode, toNode, true);
        Path path = pathFinder.getPath();

        System.out.println("Received Message : " + message);
        System.out.println("Hops: " + path.getNumberOfHops());
    }

}
