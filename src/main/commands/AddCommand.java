package commands;

import network.Network;
import network.node.Node;

import java.util.Arrays;
import java.util.List;

public class AddCommand implements Command {
    public static final String ADD_COMMAND = "ADD";
    private static final List<String> VALID_DEVICE_TYPE = Arrays.asList("COMPUTER", "REPEATER");

    private String[] inputs;
    private String deviceType;
    private String deviceName;

    public AddCommand(String[] input){
        this.inputs = input;
    }

    public AddCommand(){}

    @Override
    public void setInputs(String[] inputs){
        this.inputs = inputs;
    }

    @Override
    public boolean validate(){
        if(inputs.length != 3 || !VALID_DEVICE_TYPE.contains(inputs[1]))
            return false;
        deviceType = inputs[1];
        deviceName = inputs[2];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception{
        if(network.isDeviceAvailable(deviceName)){
            throw new Exception("That name already exists.");
        }

        Node node = new Node(deviceName, deviceType);
        network.putDevice(node);
        System.out.println("Successfully added " + deviceName);
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(ADD_COMMAND);
    }
}
