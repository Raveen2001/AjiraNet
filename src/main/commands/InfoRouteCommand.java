package commands;

import network.Network;
import network.node.Node;

public class InfoRouteCommand implements Command {
    public static final String INFO_ROUTE_COMMAND = "INFO_ROUTE";

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
            throw new Exception("No source or destination node found.");
        }

        Node fromNode = network.getDevice(from);
        Node toNode = network.getDevice(to);

        fromNode.getRoute(toNode);
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(INFO_ROUTE_COMMAND);
    }
}
