package commands;

import network.Network;
import network.node.ComputerNode;
import network.node.Node;
import network.node.RepeaterNode;

import java.util.Arrays;
import java.util.List;

public class AddCommand implements Command {
    public static final String ADD_COMMAND = "ADD";
    private static final List<String> VALID_DEVICE_TYPE = Arrays.asList("COMPUTER", "REPEATER");

    private String[] inputs;
    private String nodeType;
    private String nodeName;

    public AddCommand(){}

    @Override
    public void setInputs(String[] inputs){
        this.inputs = inputs;
    }

    @Override
    public boolean parseInputs(){
        if(inputs.length != 3 || !VALID_DEVICE_TYPE.contains(inputs[1]))
            return false;
        nodeType = inputs[1];
        nodeName = inputs[2];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception{
        if(network.isDeviceAvailable(nodeName)){
            throw new Exception("That name already exists.");
        }

        Node node;
        if(nodeType.equals("COMPUTER")) node = new ComputerNode(nodeName);
        else node = new RepeaterNode(nodeName);

        network.putDevice(node);
        System.out.println("Successfully added " + nodeName);
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(ADD_COMMAND);
    }
}
