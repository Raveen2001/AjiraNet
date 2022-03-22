import java.util.*;

public class InputHandler {
    static final String ADD_COMMAND = "ADD";
    static final String SET_DEVICE_STRENGTH_COMMAND = "SET_DEVICE_STRENGTH";
    static final String CONNECT_COMMAND = "CONNECT";
    static final String INFO_ROUTE_COMMAND = "INFO_ROUTE";
    static final String EXIT_COMMAND = "EXIT";
    private static final List<String> VALID_COMMANDS = Arrays.asList(AddCommand.ADD_COMMAND, SetDeviceStrengthCommand.SET_DEVICE_STRENGTH_COMMAND, ConnectCommand.CONNECT_COMMAND, InfoRouteCommand.INFO_ROUTE_COMMAND, ExitCommand.EXIT_COMMAND);

    private final Scanner scanner = new Scanner(System.in);

    public Command getCommand() throws Exception{
        System.out.print("> ");
        String[] input = scanner.nextLine().split(" ");
        if(!isValidCommand(input))
            throw new Exception("Invalid Command Syntax");

        List<Command> commands = new ArrayList<>();
        addAllCommands(commands);

        Command curCommand = null;
        for(Command command : commands){
            if (command.doesMatchCommand(input[0])){
                curCommand = command;
                curCommand.setInputs(input);
                break;
            }
        }
        return curCommand;
    }


    public Command getCommand(String[] input) throws Exception{
        if(!isValidCommand(input))
            throw new Exception("Invalid Command Syntax");

        Command command = null;
        switch (input[0]){
            case ADD_COMMAND -> command = new AddCommand(input);
            case SET_DEVICE_STRENGTH_COMMAND -> command = new SetDeviceStrengthCommand(input);
            case CONNECT_COMMAND -> command = new ConnectCommand(input);
            case INFO_ROUTE_COMMAND -> command = new InfoRouteCommand(input);
            case EXIT_COMMAND -> command = new ExitCommand();
        }
        return command;
    }

    private void addAllCommands(List<Command> commands){
        commands.add(new AddCommand());
        commands.add(new SetDeviceStrengthCommand());
        commands.add(new ConnectCommand());
        commands.add(new InfoRouteCommand());
        commands.add(new ExitCommand());
    }

    private boolean isValidCommand(String[] input){
        return (input.length > 0 && VALID_COMMANDS.contains(input[0]));
    }
}
