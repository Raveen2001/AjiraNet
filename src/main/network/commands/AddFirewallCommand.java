package network.commands;

import network.Network;
import network.node.Node;

public class AddFirewallCommand implements Command {

    public static final String ADD_FIREWALL_COMMAND = "ADD_TO_FIREWALL";
    public static final String CLASS_NAME = "network.commands.AddFirewallCommand";


    private String[] inputs;
    private String source;
    private String target;


    @Override
    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean parseInputs() {
        if(inputs.length != 3) return false;
        source = inputs[1];
        target = inputs[2];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(source) || !network.isDeviceAvailable(target)){
            throw new Exception("No node found");
        }

        Node sourceNode = network.getDevice(source);

        sourceNode.addToBlacklist(target);
        System.out.println(target + " added to " + sourceNode + "’s firewall.");
    }

}
