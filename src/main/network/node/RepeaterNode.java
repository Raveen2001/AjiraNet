package network.node;

public class RepeaterNode extends Node{
    public RepeaterNode(String name) {
        super(name);
        super.strength = 0;
    }

    @Override
    public int getUpdatedStrength(int strength) {
        return strength * 2;
    }

    @Override
    public boolean canBeDestination() {
        return false;
    }
}
