public class SetDeviceStrengthCommand implements Command {
    static final String SET_DEVICE_STRENGTH_COMMAND = "SET_DEVICE_STRENGTH";

    private String[] inputs;
    private String deviceName;
    private int strength;

    SetDeviceStrengthCommand(String[] input){
        this.inputs = input;
    }

    public SetDeviceStrengthCommand(){}

    @Override
    public void setInputs(String[] inputs){
        this.inputs = inputs;
    }


    @Override
    public boolean validate(){
        try{
            if(inputs.length != 3) throw new Exception();
            deviceName = inputs[1];
            strength = Integer.parseInt(inputs[2]);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public void execute(Network network) throws Exception {
        if(!network.isDeviceAvailable(deviceName)){
            throw new Exception("Device not found.");
        }

        Node device = network.getDevice(deviceName);
        device.setStrength(strength);
        System.out.println("Successfully defined strength.");
    }

    @Override
    public boolean doesMatchCommand(String command) {
        return command.equals(SET_DEVICE_STRENGTH_COMMAND);
    }
}
