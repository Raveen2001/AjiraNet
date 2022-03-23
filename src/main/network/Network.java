package network;

import network.node.Node;

import java.util.HashMap;
import java.util.Map;

public class Network {
    private final Map<String, Node> devices = new HashMap<>();

    public void putDevice(Node node){
        devices.put(node.name, node);
    }

    public Node getDevice(String name){
        return devices.get(name);
    }

    public boolean isDeviceAvailable(String name){
        return devices.containsKey(name);
    }
}
