import java.util.*;

public class AjiraNet {
    Map<String, Node> devicesAvailable = new HashMap<>();
    InputHandler inputHandler = new InputHandler();

    public AjiraNet(){
        startConsole();
    }

    private void startConsole(){
        while(true){
            try{
               String[] input = inputHandler.getInput();
               parseInput(input);
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void parseInput(String[] input) throws Exception{
        String command = input[0];
        String arg1 = input[1];
        String arg2 = input[2];

        switch (command) {
            case InputHandler.ADD_COMMAND -> addNode(arg1, arg2);
            case InputHandler.SET_DEVICE_STRENGTH_COMMAND -> setStrength(arg1, arg2);
            case InputHandler.CONNECT_COMMAND -> connect(arg1, arg2);
            case InputHandler.INFO_ROUTE_COMMAND -> routeInfo(arg1, arg2);
        }
    }

    private void addNode(String type, String name) throws Exception{
        if(isDeviceAvailable(name)){
            throw new Exception("That name already exists.");
        }

        Node node = new Node(name, type);
        devicesAvailable.put(name, node);
        System.out.println("Successfully added " + name);
    }

    private void setStrength(String name, String strengthString) throws Exception{
        if(!isDeviceAvailable(name)){
            throw new Exception("Device not found.");
        }

        int strength = Integer.parseInt(strengthString);
        Node device = devicesAvailable.get(name);
        device.setStrength(strength);
        System.out.println("Successfully defined strength.");
    }

    private void connect(String source, String destination) throws Exception{

        if(!isDeviceAvailable(source) || !isDeviceAvailable(destination)){
            throw new Exception("No source or destination node.");
        }

        boolean isSameSourceAndDestination = source.equals(destination);
        if(isSameSourceAndDestination){
            throw new Exception("Cannot connect device to itself.");
        }

        Node sourceNode = devicesAvailable.get(source);
        Node destinationNode = devicesAvailable.get(destination);
        sourceNode.connect(destinationNode);
        System.out.println("Successfully connected.");
    }

    private void routeInfo(String source, String destination) throws Exception{
        if(!isDeviceAvailable(source) || !isDeviceAvailable(destination)){
            throw new Exception("No source or destination node found.");
        }

        Node sourceNode = devicesAvailable.get(source);
        Node destinationNode = devicesAvailable.get(destination);

        if(isRepeater(destinationNode)){
            throw new Exception("Route cannot be calculated with a repeater.");
        }

        List<String> route = sourceNode.routeInfo(destinationNode);
        if(route.isEmpty()){
            throw new Exception("No Route Found.");
        }
        printRoute(route);
    }

    private boolean isDeviceAvailable(String name){
        return devicesAvailable.containsKey(name);
    }

    private void printRoute(List<String> route){
        System.out.println(String.join(" -> ", route));
    }

    private boolean isRepeater(Node node){
        return !node.isComputer;
    }

}
