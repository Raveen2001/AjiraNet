package network;

import commands.Command;
public class AjiraNetwork extends Network{
    private final CommandHandler commandHandler;

    public AjiraNetwork(){
        commandHandler = new CommandHandler();
        startConsole();
    }

    private void startConsole(){
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
