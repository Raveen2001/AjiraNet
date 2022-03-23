package network;

import commands.Command;

import java.util.Arrays;

public class AjiraNetwork extends Network{
    private final CommandHandler commandHandler;
    private final boolean isTestMode = true;

    public AjiraNetwork(){
        commandHandler = new CommandHandler();
        startConsole();
    }

    private void startConsole(){
        if (isTestMode) runTestCases();

        while(true){
            try{
               Command command = commandHandler.getCommand();
               if(command.validate()){
                   command.execute(this);
               }else{
                   throw new Exception("Invalid command Syntax.");
               }
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }




    private void runTestCases() {
        for(String inp: TestCases.inputs){
            System.out.println(inp);
            try{
                Command command = commandHandler.getCommand(inp.split(" "));
                if(command.validate()){
                    command.execute(this);
                }else{
                    throw new Exception("Invalid command syntax.");
                }
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
