public class ConnectCommand implements Command{
    static final String CONNECT_COMMAND = "CONNECT";

    private String[] inputs;
    private String source;
    private String destination;
    ConnectCommand(String[] input){
        this.inputs = input;
    }

    public ConnectCommand(){}

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
            throw new Exception("No source or destination node.");
        }

        boolean isSameSourceAndDestination = source.equals(destination);
        if(isSameSourceAndDestination){
            throw new Exception("Cannot connect device to itself.");
        }

        Node sourceNode = network.getDevice(source);
        Node destinationNode = network.getDevice(destination);
        sourceNode.connect(destinationNode);
        System.out.println("Successfully connected.");
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(CONNECT_COMMAND);
    }
}
