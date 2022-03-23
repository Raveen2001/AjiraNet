package commands;

import network.Network;
import network.node.Node;

import java.util.List;

public class InfoRouteCommand implements Command {
    public static final String INFO_ROUTE_COMMAND = "INFO_ROUTE";

    private String[] inputs;
    private String source;
    private String destination;
    public InfoRouteCommand(String[] input){
        this.inputs = input;
    }

    public InfoRouteCommand(){}

    @Override
    public void setInputs(String[] inputs){
        this.inputs = inputs;
    }

    @Override
    public boolean validate() {
        if(inputs.length != 3)
            return false;
        source = inputs[1];
        destination = inputs[2];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(source) || !network.isDeviceAvailable(destination)){
            throw new Exception("No source or destination node found.");
        }

        Node sourceNode = network.getDevice(source);
        Node destinationNode = network.getDevice(destination);
        sourceNode.printRoute(destinationNode);
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(INFO_ROUTE_COMMAND);
    }
}
