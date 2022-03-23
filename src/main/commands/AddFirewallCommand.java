package commands;

import network.Network;
import network.node.Node;

public class AddFirewallCommand implements Command {

    public static final String ADD_FIREWALL_COMMAND = "ADD_TO_FIREWALL";

    private String[] inputs;
    private String source;
    private String blacklist;
    @Override
    public void setInputs(String[] inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean validate() {
        if(inputs.length != 3) return false;
        source = inputs[1];
        blacklist = inputs[2];
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(source) || !network.isDeviceAvailable(blacklist)){
            throw new Exception("No node found");
        }

        Node sourceNode = network.getDevice(source);
        Node blacklistNode = network.getDevice(blacklist);

        if(sourceNode.blacklistedNodes.containsKey(blacklistNode.name)){
            throw new Exception("Node already blocked");
        }

        sourceNode.blacklistedNodes.put(blacklistNode.name, blacklistNode);
        System.out.println(blacklistNode.name + " added to " + sourceNode.name + "’s firewall.");
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(ADD_FIREWALL_COMMAND);
    }
}
