package network;

import network.commands.Command;
import network.commands.CommandHandler;

public class AjiraNetwork extends Network{
    private final CommandHandler commandHandler;

    public AjiraNetwork(){
        commandHandler = new CommandHandler();
    }

    public void startConsole(){
        while(true){
            try{
               Command command = commandHandler.getCommand();
               if(command.parseInputs()){
                   command.execute(this);
               }else{
                   throw new Exception("Invalid command Syntax.");
               }
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
