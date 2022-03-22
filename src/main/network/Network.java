package network;

import network.node.Node;

import java.util.HashMap;
import java.util.Map;

public class Network {
    private final Map<String, Node> devicesAvailable = new HashMap<>();

    public void putDevice(Node node){
        devicesAvailable.put(node.name, node);
    }

    public Node getDevice(String name){
        return devicesAvailable.get(name);
    }

    public boolean isDeviceAvailable(String name){
        return devicesAvailable.containsKey(name);
    }
}
