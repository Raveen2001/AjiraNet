package network.commands;

import java.util.*;

public class CommandHandler {
    private final Map<String, String> COMMANDS_WITH_CLASS_NAMES = new HashMap<>();
    private final Scanner scanner;

    public CommandHandler(){
        scanner = new Scanner(System.in);
        addAllCommandsWithClassName();
    }

    public Command getCommand() throws Exception{
        System.out.print("> ");
        String[] inputs = scanner.nextLine().split(" ");
        if(!isValidCommand(inputs))
            throw new Exception("Invalid command syntax.");

        Class command = Class.forName(COMMANDS_WITH_CLASS_NAMES.get(inputs[0]));
        Command curCommand = (Command) command.getConstructor().newInstance();
        curCommand.setInputs(inputs);
        return curCommand;
    }

    private boolean isValidCommand(String[] input){
        return (input.length > 0 && COMMANDS_WITH_CLASS_NAMES.containsKey(input[0]));
    }

    private void addAllCommandsWithClassName(){
        COMMANDS_WITH_CLASS_NAMES.put(AddCommand.ADD_COMMAND, AddCommand.CLASS_NAME);
        COMMANDS_WITH_CLASS_NAMES.put(SetDeviceStrengthCommand.SET_DEVICE_STRENGTH_COMMAND, SetDeviceStrengthCommand.CLASS_NAME);
        COMMANDS_WITH_CLASS_NAMES.put(ConnectCommand.CONNECT_COMMAND, ConnectCommand.CLASS_NAME);
        COMMANDS_WITH_CLASS_NAMES.put(InfoRouteCommand.INFO_ROUTE_COMMAND, InfoRouteCommand.CLASS_NAME);
        COMMANDS_WITH_CLASS_NAMES.put(AddFirewallCommand.ADD_FIREWALL_COMMAND, AddFirewallCommand.CLASS_NAME);
        COMMANDS_WITH_CLASS_NAMES.put(SendCommand.SEND_COMMAND, SendCommand.CLASS_NAME);
        COMMANDS_WITH_CLASS_NAMES.put(EnableDisableCommand.ENABLE_COMMAND, EnableDisableCommand.CLASS_NAME);
        COMMANDS_WITH_CLASS_NAMES.put(EnableDisableCommand.DISABLE_COMMAND, EnableDisableCommand.CLASS_NAME);
        COMMANDS_WITH_CLASS_NAMES.put(ExitCommand.EXIT_COMMAND, ExitCommand.CLASS_NAME);
    }
}
