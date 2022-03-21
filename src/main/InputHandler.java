import java.util.*;

public class InputHandler {
    static final String ADD_COMMAND = "ADD";
    static final String SET_DEVICE_STRENGTH_COMMAND = "SET_DEVICE_STRENGTH";
    static final String CONNECT_COMMAND = "CONNECT";
    static final String INFO_ROUTE_COMMAND = "INFO_ROUTE";
    private static final List<String> VALID_COMMANDS = Arrays.asList(ADD_COMMAND, SET_DEVICE_STRENGTH_COMMAND, CONNECT_COMMAND, INFO_ROUTE_COMMAND);
    private static final List<String> VALID_NODE_TYPES = Arrays.asList("COMPUTER", "REPEATER");

    private static final Scanner scanner = new Scanner(System.in);

    public String[] getInput() throws Exception{
        System.out.print("> ");
        String[] input = scanner.nextLine().split(" ");
        if(!isValidCommand(input)) {
            throw new Exception("Invalid Command Syntax");
        }
        return input;
    }



    private boolean isValidCommand(String[] input){
        String command = input[0];
        boolean isValidCommand = (input.length == 3) && VALID_COMMANDS.contains(command);
        if(isValidCommand){
            switch (command){
                case ADD_COMMAND:
                    return isValidNodeType(input[1]);
                case SET_DEVICE_STRENGTH_COMMAND:
                    return isValidSetStrength(input[2]);
            }
        }
        return isValidCommand;
    }

    private boolean isValidNodeType(String type){
        return VALID_NODE_TYPES.contains(type);
    }

    private boolean isValidSetStrength(String strengthString){
        try{
            Integer.parseInt(strengthString);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
