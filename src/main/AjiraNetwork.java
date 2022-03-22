public class AjiraNetwork extends Network{
    InputHandler inputHandler;
    boolean isTestMode = true;

    public AjiraNetwork(){
        inputHandler = new InputHandler();
        startConsole();
    }

    private void startConsole(){
        if (isTestMode){
            for(String inp: TestCases.inputs){
                try{
                    Command command = inputHandler.getCommand(inp.split(" "));
                    if(command.validate()){
                        command.execute(this);
                    }else{
                        throw new Exception("Invalid Command Syntax");
                    }
                }catch (Exception e){
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        while(true){
            try{
               Command command = inputHandler.getCommand();
               if(command.validate()){
                   command.execute(this);
               }else{
                   throw new Exception("Invalid Command Syntax");
               }
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
