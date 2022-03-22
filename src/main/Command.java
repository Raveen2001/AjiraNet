public interface Command {
    void setInputs(String[] inputs);
    boolean validate();
    void execute(Network network) throws Exception;
    boolean doesMatchCommand(String command);
}
