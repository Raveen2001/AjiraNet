package network;

import commands.*;

import java.util.*;

import static commands.AddCommand.ADD_COMMAND;
import static commands.ConnectCommand.CONNECT_COMMAND;
import static commands.ExitCommand.EXIT_COMMAND;
import static commands.InfoRouteCommand.INFO_ROUTE_COMMAND;
import static commands.SetDeviceStrengthCommand.SET_DEVICE_STRENGTH_COMMAND;
import static commands.AddFirewallCommand.ADD_FIREWALL_COMMAND;
import static commands.SendCommand.SEND_COMMAND;
import static commands.DisableCommand.DISABLE_COMMAND;
import static commands.EnableCommand.ENABLE_COMMAND;

public class CommandHandler {
    private static final List<String> VALID_COMMANDS = Arrays.asList(ADD_COMMAND, SET_DEVICE_STRENGTH_COMMAND, CONNECT_COMMAND, INFO_ROUTE_COMMAND, ADD_FIREWALL_COMMAND, SEND_COMMAND, DISABLE_COMMAND,ENABLE_COMMAND, EXIT_COMMAND);

    private final Scanner scanner;
    private final List<Command> commands;

    public CommandHandler(){
        scanner = new Scanner(System.in);
        commands = new ArrayList<>();
        addAllCommands();
    }

    public Command getCommand() throws Exception{
        System.out.print("> ");
        String[] input = scanner.nextLine().split(" ");
        if(!isValidCommand(input))
            throw new Exception("Invalid command syntax.");

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

    private void addAllCommands(){
        commands.add(new AddCommand());
        commands.add(new SetDeviceStrengthCommand());
        commands.add(new ConnectCommand());
        commands.add(new InfoRouteCommand());
        commands.add(new AddFirewallCommand());
        commands.add(new SendCommand());
        commands.add(new DisableCommand());
        commands.add(new EnableCommand());
        commands.add(new ExitCommand());
    }

    private boolean isValidCommand(String[] input){
        return (input.length > 0 && VALID_COMMANDS.contains(input[0]));
    }
}
