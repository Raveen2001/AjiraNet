import java.util.List;

public class InfoRouteCommand implements Command{
    static final String INFO_ROUTE_COMMAND = "INFO_ROUTE";

    private String[] inputs;
    private String source;
    private String destination;
    InfoRouteCommand(String[] input){
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

        List<String> route = sourceNode.routeInfo(destinationNode);
        if(route.isEmpty()){
            throw new Exception("No Route Found.");
        }
        System.out.println(String.join(" -> ", route));
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(INFO_ROUTE_COMMAND);
    }
}
