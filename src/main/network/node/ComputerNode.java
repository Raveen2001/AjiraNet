package network.node;

public class ComputerNode extends Node{
    public ComputerNode(String name) {
        super(name);
        super.strength = 5;
        super.type = "COMPUTER";
    }

    @Override
    public int getUpdatedStrength(int strength) {
        return strength - 1;
    }

    @Override
    public boolean canBeDestination() {
        return true;
    }
}
