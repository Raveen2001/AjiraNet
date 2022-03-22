package network;

import commands.Command;

public class AjiraNetwork extends Network{
    InputHandler inputHandler;
    boolean isTestMode = true;

    public AjiraNetwork(){
        inputHandler = new InputHandler();
        startConsole();
    }

    private void startConsole(){
        if (isTestMode) runTestCases();

        while(true){
            try{
               Command command = inputHandler.getCommand();
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
            try{
                Command command = inputHandler.getCommand(inp.split(" "));
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
