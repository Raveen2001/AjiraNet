package network.commands;


import network.Network;
import network.node.Node;

public class SetDeviceStrengthCommand implements Command {
    public static final String SET_DEVICE_STRENGTH_COMMAND = "SET_DEVICE_STRENGTH";
    public static final String CLASS_NAME = "network.commands.SetDeviceStrengthCommand";


    private String[] inputs;
    private String nodeName;
    private int strength;


    @Override
    public void setInputs(String[] inputs){
        this.inputs = inputs;
    }


    @Override
    public boolean parseInputs(){
        try{
            if(inputs.length != 3) throw new Exception();
            nodeName = inputs[1];
            strength = Integer.parseInt(inputs[2]);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(nodeName)){
            throw new Exception("Device not found.");
        }

        Node node = network.getDevice(nodeName);
        node.strength = strength;
        System.out.println("Successfully defined strength.");
    }

}
