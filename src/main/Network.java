import java.util.HashMap;
import java.util.Map;

public class Network {
    private final Map<String, Node> devicesAvailable = new HashMap<>();

    void putDevice(Node node){
        devicesAvailable.put(node.name, node);
    }

    Node getDevice(String name){
        return devicesAvailable.get(name);
    }

    boolean isDeviceAvailable(String name){
        return devicesAvailable.containsKey(name);
    }
}
