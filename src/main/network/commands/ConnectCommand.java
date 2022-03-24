package network.commands;

import network.Network;
import network.node.Node;

public class ConnectCommand implements Command {
    public static final String CONNECT_COMMAND = "CONNECT";
    public static final String CLASS_NAME = "network.commands.ConnectCommand";


    private String[] inputs;
    private String from;
    private String to;

    @Override
    public void setInputs(String[] inputs){
        this.inputs = inputs;
    }

    @Override
    public boolean parseInputs() {
        if(inputs.length != 3)
            return false;
        from = inputs[1];
        to = inputs[2];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(from) || !network.isDeviceAvailable(to)){
            throw new Exception("No source or destination node.");
        }

        Node fromNode = network.getDevice(from);
        Node toNode = network.getDevice(to);
        fromNode.makeConnectionTo(toNode);
        System.out.println("Successfully connected.");
    }

}
